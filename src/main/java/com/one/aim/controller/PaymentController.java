package com.one.aim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.one.aim.rq.PaymentRq;
import com.one.aim.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@PostMapping("/payment")
	public ResponseEntity<?> payment(@RequestBody PaymentRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /payment]");
		}
		return new ResponseEntity<>(paymentService.processPayment(rq), HttpStatus.OK);
	}

}
