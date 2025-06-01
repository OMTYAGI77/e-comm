package com.one.aim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	
	@PostMapping("/carts")
	public ResponseEntity<?> retrieveCart() throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(cartService.retrieveCart(), HttpStatus.OK);
	}
}
