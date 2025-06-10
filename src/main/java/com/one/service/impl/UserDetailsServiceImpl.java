package com.one.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.one.aim.bo.AdminBO;
import com.one.aim.bo.UserBO;
import com.one.aim.repo.AdminRepo;
import com.one.aim.repo.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	AdminRepo adminRepo;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmpNumber) throws UsernameNotFoundException {

		System.out.println(usernameOrEmpNumber);
		UserBO user = userRepo.findByUsername(usernameOrEmpNumber);
		AdminBO admin = adminRepo.findByUsername(usernameOrEmpNumber);
		AdminBO seller = adminRepo.findByUsername(usernameOrEmpNumber);
		if (user == null && admin == null && seller==null) {
			new UsernameNotFoundException("User Not Found : " + usernameOrEmpNumber);
		}
		if (admin != null) {
			user = new UserBO();
			user.setUsername(admin.getUsername());
			user.setPassword(admin.getPassword());
			user.setId(admin.getId());
		}
		System.out.println("hiiii------2");
		// UserBO user = optUser.get();
		System.out.println(user.toString());
		Long userid = user.getId();
		String username = user.getUsername();
		String password = user.getPassword();
		Long employeeid = null;
		Integer loginstatus = 1;
		System.out.println("userid " + userid + " username " + username + " password " + password + " employeeid "
				+ employeeid + " loginstatus " + loginstatus);
		System.out.println("hiiii------3");
		return UserDetailsImpl.build(user);
	}
}