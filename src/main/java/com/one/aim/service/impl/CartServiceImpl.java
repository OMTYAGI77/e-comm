package com.one.aim.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.one.aim.bo.AdminBO;
import com.one.aim.bo.CartBO;
import com.one.aim.constants.ErrorCodes;
import com.one.aim.constants.MessageCodes;
import com.one.aim.mapper.CartMapper;
import com.one.aim.repo.AdminRepo;
import com.one.aim.repo.CartRepo;
import com.one.aim.rq.CartRq;
import com.one.aim.rs.CartRs;
import com.one.aim.rs.data.CartDataRs;
import com.one.aim.rs.data.CartDataRsList;
import com.one.aim.service.CartService;
import com.one.constants.StringConstants;
import com.one.utils.AuthUtils;
import com.one.utils.Utils;
import com.one.vm.core.BaseRs;
import com.one.vm.utils.ResponseUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartRepo cartRepo;

	@Autowired
	AdminRepo adminRepo;

	@Override
	public BaseRs saveCart(CartRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing saveCompany(CompanyRq) ->");
		}
		
		

		AdminBO adminBO = adminRepo.findByIdAndUsername(AuthUtils.findLoggedInUser().getDocId(),
                AuthUtils.findLoggedInUser().getUserName());

if (null == adminBO) {
    log.error(ErrorCodes.EC_UNAUTHORIZED_ACCESS);
    return ResponseUtils.failure(ErrorCodes.EC_UNAUTHORIZED_ACCESS);
}
		// List<String> errors = UserHelper.validateUser(rq);

//		if (Utils.isNotEmpty(errors)) {
//			log.error(ErrorCodes.EC_INVALID_INPUT);
//			return ResponseUtils.failure(ErrorCodes.EC_INVALID_INPUT, errors);
//		}
		String docId = Utils.getValidString(rq.getDocId());
		String message = StringConstants.EMPTY;
		CartBO cartBO = null;
		if (Utils.isNotEmpty(docId)) { // UPDATE
			long id = Long.parseLong(docId);
			Optional<CartBO> optCartBO = cartRepo.findById(id);
			cartBO = optCartBO.get();
			if (cartBO == null) {
				log.error(ErrorCodes.EC_USER_NOT_FOUND);
				return ResponseUtils.failure(ErrorCodes.EC_USER_NOT_FOUND);
			}
		} else {
			cartBO = new CartBO(); // SAVE
			message = MessageCodes.MC_SAVED_SUCCESSFUL;
		}
		String pName = Utils.getValidString(rq.getPName());
		if (!pName.equals(Utils.getValidString(cartBO.getPname()))) {
			cartBO.setPname(pName);
		}
		String description = Utils.getValidString(rq.getDescription());
		if (!description.equals(cartBO.getDescription())) {
			cartBO.setDescription(description);
		}
		String category = Utils.getValidString(rq.getCategory());
		if (!category.equals(cartBO.getCategory())) {
			cartBO.setCategory(category);
		}
		cartBO.setPrice(rq.getPrice());
		cartBO.setOffer(rq.getOffer());
		cartBO.setEnabled(rq.isEnabled());
		cartRepo.save(cartBO);
		CartRs cartRs = CartMapper.mapToCartRs(cartBO);
		return ResponseUtils.success(new CartDataRs(message, cartRs));
	}

	@Override
	public BaseRs retrieveCart() throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing saveCompany(CompanyRq) ->");
		}

		try {
			List<CartBO> bos = cartRepo.findAll();
			List<CartRs> rslist = CartMapper.mapToCartRsList(bos);
			String message = MessageCodes.MC_RETRIEVED_SUCCESSFUL;
			return ResponseUtils.success(new CartDataRsList(message, rslist));
		} catch (Exception e) {
			log.error("Exception in retrieveAuditor(IdRq) - " + e);
			return ResponseUtils.failure(e);
		}

	}

}
