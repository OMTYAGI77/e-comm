package com.one.aim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.one.aim.rq.LoginRq;
import com.one.aim.rq.UserRq;
import com.one.aim.service.SellerService;
import com.one.aim.service.UserService;
import com.one.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/auth/signup")
	public ResponseEntity<?> saveCompany(@RequestBody UserRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(userService.saveUser(rq), HttpStatus.OK);
	}

	@PostMapping("/auth/signin")
	public ResponseEntity<?> signIn(@RequestBody LoginRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /signin]");
		}
		try {
			String key = Utils.getValidString(rq.getUsername()).intern();
			synchronized (key) {
				System.out.println("hello--1");
				Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(rq.getUsername(), rq.getPassword()));
				System.out.println("hello--2");
				SecurityContextHolder.getContext().setAuthentication(authentication);
				System.out.println("hello--3");
				return new ResponseEntity<>(userService.signIn(authentication), HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("Excetion in  loginUser(LoginRq)  -->" + e.getMessage());
			throw e;
		}
	}

//	@GetMapping("/find/{id}")
//    public UserBO getUserBOById(@PathVariable Long id) {
//        return userService.getUserBOById(id);
//    }

	@PostMapping("/find")
	public ResponseEntity<?> retrieveUserBO() throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(userService.retrieveUserBO(), HttpStatus.OK);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing RESTfulService [POST /user]");
		}
		return new ResponseEntity<>(userService.logout(), HttpStatus.OK);
	}
}
