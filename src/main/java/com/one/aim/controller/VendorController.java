package com.one.aim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.one.aim.rq.VendorRq;
import com.one.aim.service.VendorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class VendorController {

	@Autowired
	private VendorService adminService;

	@PostMapping("/auth/signup/vendor")
	public ResponseEntity<?> saveAdmin(@RequestBody VendorRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(adminService.saveVendor(rq), HttpStatus.OK);
	}

//	@GetMapping("/find/id/{id}")
//    public AdminBO getUserBOById(@PathVariable Long id) {
//        return adminService.getAdminBOById(id);
//    }

	@PostMapping("/find/vendor")
	public ResponseEntity<?> retrieveAdminBO() throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(adminService.retrieveVendor(), HttpStatus.OK);
	}
}