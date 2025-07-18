package com.one.aim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.one.aim.rq.AddressRq;
import com.one.aim.service.AddressService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class AddressController {

	@Autowired
	private AddressService addressService;

	@PostMapping("/adddress/save")
	public ResponseEntity<?> saveCart(@RequestBody AddressRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(addressService.saveAddress(rq), HttpStatus.OK);
	}
}
