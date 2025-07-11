package com.one.aim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.one.aim.rq.OrderRq;
import com.one.aim.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/order/save")
	public ResponseEntity<?> saveOrder(@RequestBody OrderRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /order/save]");
		}
		return new ResponseEntity<>(orderService.placeOrder(rq), HttpStatus.OK);
	}

	@GetMapping("/order/{id}")
	public ResponseEntity<?> retrieveCart(@PathVariable("id") long id) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [GET /order/id]");
		}
		return new ResponseEntity<>(orderService.retrieveOrder(id), HttpStatus.OK);
	}

	@GetMapping("/orders")
	public ResponseEntity<?> retrieveCartList() throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /orders]");
		}
		return new ResponseEntity<>(orderService.retrieveOrders(), HttpStatus.OK);
	}

}
