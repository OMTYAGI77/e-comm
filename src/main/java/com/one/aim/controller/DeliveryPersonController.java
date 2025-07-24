package com.one.aim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.one.aim.rq.DeliveryPersonRq;
import com.one.aim.service.DeliveryPersonService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/contacts")
@Slf4j
@CrossOrigin(origins = "*")
public class DeliveryPersonController {
	
	@Autowired
	DeliveryPersonService deliveryPersonService;
	
	
	@PostMapping("/auth/signup/delivery")
	public ResponseEntity<?> saveCompany(@RequestBody DeliveryPersonRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(deliveryPersonService.saveDeliveryPerson(rq), HttpStatus.OK);
	}
	
//	@PostMapping("/deliveryPerson/all")
//	public ResponseEntity<?> retrievesDeliveryPerson() throws Exception {
//
//		if (log.isDebugEnabled()) {
//			log.debug("Executing RESTfulService [POST /user]");
//		}
//		return new ResponseEntity<>(deliveryPersonService.retrieveAdmin(), HttpStatus.OK);
//	}

	
	

}
