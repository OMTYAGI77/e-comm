package com.one.aim.service;

import org.springframework.security.core.Authentication;

import com.one.aim.rq.UserRq;
import com.one.vm.core.BaseRs;

public interface UserService {

	public BaseRs saveUser(UserRq rq) throws Exception;

	public BaseRs signIn(Authentication rq) throws Exception;

	public BaseRs retrieveUser() throws Exception;

	public BaseRs logout() throws Exception;

}
