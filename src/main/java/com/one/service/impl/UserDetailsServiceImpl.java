package com.one.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.one.aim.bo.AdminBO;
import com.one.aim.bo.SellerBO;
import com.one.aim.bo.UserBO;
import com.one.aim.bo.VendorBO;
import com.one.aim.repo.AdminRepo;
import com.one.aim.repo.SellerRepo;
import com.one.aim.repo.UserRepo;
import com.one.aim.repo.VendorRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	AdminRepo adminRepo;

	@Autowired
	SellerRepo sellerRepo;
	
	@Autowired
	VendorRepo vendorRepo;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmpNumber) throws UsernameNotFoundException {

		UserBO user = userRepo.findByEmailOrUsername(usernameOrEmpNumber, usernameOrEmpNumber);
		AdminBO admin = adminRepo.findByEmailOrUsername(usernameOrEmpNumber, usernameOrEmpNumber);
		SellerBO seller = sellerRepo.findByEmailOrUsername(usernameOrEmpNumber, usernameOrEmpNumber);
		VendorBO vendor = vendorRepo.findByEmailOrUsername(usernameOrEmpNumber, usernameOrEmpNumber);
//		if (user != null) {
//			if (user.isLogin()) {
//				throw new UsernameNotFoundException("User Already login : " + usernameOrEmpNumber);
//			} else {
//				user.setLogin(true);
//				userRepo.save(user);
//			}
//		}
//		if (admin != null) {
//			if (user.isLogin()) {
//				throw new UsernameNotFoundException("User Already login : " + usernameOrEmpNumber);
//			} else {
//				admin.setLogin(true);
//				adminRepo.save(admin);
//			}
//		}
//		if (seller != null) {
//			if (seller.isLogin()) {
//				throw new UsernameNotFoundException("User Already login : " + usernameOrEmpNumber);
//			} else {
//				seller.setLogin(true);
//				Repo.save(seller);
//			}
//		}
		if (user == null && admin == null && seller == null && vendor==null) {
			throw new UsernameNotFoundException("User Not Found : " + usernameOrEmpNumber);
		}
		if (admin != null) {
			user = new UserBO();
			user.setUsername(admin.getUsername());
			user.setPassword(admin.getPassword());
			user.setId(admin.getId());
			// adminRepo.save(null);
		}
		if (seller != null) {
			user = new UserBO();
			user.setUsername(seller.getUsername());
			user.setPassword(seller.getPassword());
			user.setId(seller.getId());
		}
		if (vendor != null) {
			user = new UserBO();
			user.setUsername(vendor.getUsername());
			user.setPassword(vendor.getPassword());
			user.setId(vendor.getId());
		}
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