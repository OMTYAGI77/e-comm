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

import com.one.aim.rq.CartRq;
import com.one.aim.service.CartService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/cart/save")
	public ResponseEntity<?> saveCart(@RequestBody CartRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(cartService.saveCart(rq), HttpStatus.OK);
	}

	@GetMapping("/carts")
	public ResponseEntity<?> retrieveCartList(
			@RequestParam(value = "limit", required = false, defaultValue = "20") int limit,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int offset) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(cartService.retrieveCarts(limit, offset), HttpStatus.OK);
	}

	@GetMapping("/carts/category/{category}")
	public ResponseEntity<?> retrieveCartsByCategory(@PathVariable("category") String category) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(cartService.retrieveCartsByCategory(category), HttpStatus.OK);
	}
	
	@GetMapping("/carts/{id}")
	public ResponseEntity<?> retrieveCart(@PathVariable("id") String id) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(cartService.retrieveCart(id), HttpStatus.OK);
	}
}
