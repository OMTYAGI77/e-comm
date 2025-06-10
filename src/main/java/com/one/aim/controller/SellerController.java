package com.one.aim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.one.aim.rq.SellerRq;
import com.one.aim.service.SellerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class SellerController {

	@Autowired
	private SellerService sellerService;

	@PostMapping("/auth/signup/seller")
	public ResponseEntity<?> saveAdmin(@RequestBody SellerRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(sellerService.saveSeller(rq), HttpStatus.OK);
	}

//	@GetMapping("/find/id/{id}")
//    public AdminBO getUserBOById(@PathVariable Long id) {
//        return adminService.getAdminBOById(id);
//    }

	@GetMapping("/find/seller")
	public ResponseEntity<?> retrieveSeller() throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(sellerService.retrieveSeller(), HttpStatus.OK);
	}
	
	@GetMapping("/find/seller/carts")
	public ResponseEntity<?> retrieveSellerCarts() throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(sellerService.retrieveSellerCarts(), HttpStatus.OK);
	}
}
