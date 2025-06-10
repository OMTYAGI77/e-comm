package com.one.aim.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.one.aim.bo.CartBO;
import com.one.aim.bo.SellerBO;
import com.one.aim.constants.ErrorCodes;
import com.one.aim.constants.MessageCodes;
import com.one.aim.mapper.CartMapper;
import com.one.aim.mapper.SellerMapper;
import com.one.aim.repo.CartRepo;
import com.one.aim.repo.SellerRepo;
import com.one.aim.rq.SellerRq;
import com.one.aim.rs.CartRs;
import com.one.aim.rs.SellerRs;
import com.one.aim.rs.data.CartDataRsList;
import com.one.aim.rs.data.SellerDataRs;
import com.one.aim.service.SellerService;
import com.one.constants.StringConstants;
import com.one.utils.AuthUtils;
import com.one.utils.Utils;
import com.one.vm.core.BaseRs;
import com.one.vm.utils.ResponseUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	SellerRepo sellerRepo;

	@Autowired
	CartRepo cartRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Sign Up
	@Override
	public BaseRs saveSeller(SellerRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing saveCompany(CompanyRq) ->");
		}

//		List<String> errors = AdminHelper.validateAdmin(rq);
//
//		if (Utils.isNotEmpty(errors)) {
//			log.error(ErrorCodes.EC_INVALID_INPUT);
//			return ResponseUtils.failure(ErrorCodes.EC_INVALID_INPUT, errors);
//		}
		String docId = Utils.getValidString(rq.getDocId());
		String message = StringConstants.EMPTY;
		SellerBO sellerBO = null;
		if (Utils.isNotEmpty(docId)) { // UPDATE
			long id = Long.parseLong(docId);
			Optional<SellerBO> optAdminBO = sellerRepo.findById(id);
			sellerBO = optAdminBO.get();
			if (sellerBO == null) {
				log.error(ErrorCodes.EC_USER_NOT_FOUND);
				return ResponseUtils.failure(ErrorCodes.EC_USER_NOT_FOUND);
			}
		} else {
			sellerBO = new SellerBO(); // SAVE
			message = MessageCodes.MC_SAVED_SUCCESSFUL;
		}
		String email = Utils.getValidString(rq.getEmail());
		if (!email.equals(Utils.getValidString(sellerBO.getEmail()))) {
			sellerBO.setEmail(email);
		}
		String userName = Utils.getValidString(rq.getUserName());
		if (!userName.equals(sellerBO.getUsername())) {
			sellerBO.setUsername(userName);
		}
		String phoneNo = Utils.getValidString(rq.getPhoneNo());
		if (!phoneNo.equals(sellerBO.getPhoneno())) {
			sellerBO.setPhoneno(phoneNo);
		}
		String rawPassword = Utils.getValidString(rq.getPassword());
		String existingEncodedPassword = sellerBO.getPassword();

		if (!passwordEncoder.matches(rawPassword, existingEncodedPassword)) {
			String hashedPassword = passwordEncoder.encode(rawPassword);
			sellerBO.setPassword(hashedPassword);
		}
		sellerRepo.save(sellerBO);
		SellerRs sellerRs = SellerMapper.mapToSellerRs(sellerBO);
		return ResponseUtils.success(new SellerDataRs(message, sellerRs));
	}

	@Override
	public Object retrieveSeller() {
		Optional<SellerBO> seller = sellerRepo.findById(AuthUtils.findLoggedInUser().getDocId());
		return seller.orElse(null);
	}

	@Override
	public BaseRs retrieveSellerCarts() throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing retrieveSellerCarts() ->");
		}
		List<CartBO> bos = cartRepo.findAllBySellerid(AuthUtils.findLoggedInUser().getDocId());
		String message;
		if (Utils.isEmpty(bos)) {
			message = MessageCodes.MC_NO_RECORDS_FOUND;
			log.error(message);
			return ResponseUtils.success(new SellerDataRs(message));
		}
		List<CartRs> CartRsList = CartMapper.mapToCartRsList(bos);
		message = MessageCodes.MC_RETRIEVED_SUCCESSFUL;
		return ResponseUtils.success(new CartDataRsList(message, CartRsList));
	}

}
