package com.one.aim.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.one.aim.bo.AdminBO;
import com.one.aim.bo.CartBO;
import com.one.aim.bo.SellerBO;
import com.one.aim.bo.VendorBO;
import com.one.aim.constants.ErrorCodes;
import com.one.aim.constants.MessageCodes;
import com.one.aim.mapper.CartMapper;
import com.one.aim.repo.AdminRepo;
import com.one.aim.repo.CartRepo;
import com.one.aim.repo.SellerRepo;
import com.one.aim.repo.VendorRepo;
import com.one.aim.rq.CartRq;
import com.one.aim.rs.CartRs;
import com.one.aim.rs.data.CartDataRs;
import com.one.aim.rs.data.CartDataRsList;
import com.one.aim.service.CartService;
import com.one.aim.service.FileService;
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

	@Autowired
	SellerRepo sellerRepo;

	@Autowired
	VendorRepo vendorRepo;

	@Autowired
	FileService fileService;

	@Override
	public BaseRs saveCart(CartRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing saveCompany(CompanyRq) ->");
		}

		AdminBO adminBO = adminRepo.findByIdAndUsername(AuthUtils.findLoggedInUser().getDocId(),
				AuthUtils.findLoggedInUser().getUserName());
		SellerBO sellerBO = sellerRepo.findByIdAndUsername(AuthUtils.findLoggedInUser().getDocId(),
				AuthUtils.findLoggedInUser().getUserName());
		VendorBO vendorBO = vendorRepo.findByIdAndUsername(AuthUtils.findLoggedInUser().getDocId(),
				AuthUtils.findLoggedInUser().getUserName());

		if (null == adminBO && null == sellerBO && null == vendorBO) {
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
				log.error(ErrorCodes.EC_CART_NOT_FOUND);
				return ResponseUtils.failure(ErrorCodes.EC_CART_NOT_FOUND);
			}
			if (null == adminBO && rq.isVarified()) {
				log.error(ErrorCodes.EC_UNAUTHORIZED_ACCESS);
				return ResponseUtils.failure(ErrorCodes.EC_UNAUTHORIZED_ACCESS);
			} else {
				cartBO.setVarified(rq.isVarified());
			}

		} else {
			cartBO = new CartBO(); // SAVE
			message = MessageCodes.MC_SAVED_SUCCESSFUL;
		}
		String pName = Utils.getValidString(rq.getPName());
		if (!pName.equals(Utils.getValidString(cartBO.getPname()))) {
			cartBO.setPname(pName);
		}
		Long sellerId = AuthUtils.findLoggedInUser().getDocId();
		cartBO.setSellerid(sellerId);

		// cartBO.setCartatts(fileService.prepareAttBOs(rq.getAtts(), null));

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
		cartBO.setCartatts(fileService.prepareAttBOs(rq.getAtts(), null));
		cartRepo.save(cartBO);
		CartRs cartRs = CartMapper.mapToCartMinRs(cartBO);
		return ResponseUtils.success(new CartDataRs(message, cartRs));
	}

	@Override
	public BaseRs retrieveCarts(int limit, int offset) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing saveCompany(CompanyRq) ->");
		}

		try {
			int page = offset / limit;
			PageRequest pageRequest = PageRequest.of(page, limit);
			Page<CartBO> cartPage = cartRepo.findAllByVarifiedIsTrue(pageRequest);

			List<CartRs> rslist = CartMapper.mapToCartMinRsList(cartPage.getContent());
			String message = MessageCodes.MC_RETRIEVED_SUCCESSFUL;
			return ResponseUtils.success(new CartDataRsList(message, rslist));
		} catch (Exception e) {
			log.error("Exception in retrieveAuditor(IdRq) - " + e);
			return ResponseUtils.failure(e);
		}

	}

	@Override
	public BaseRs retrieveCartsByCategory(String category) {

		if (log.isDebugEnabled()) {
			log.debug("Executing retrieveCartsByCategory(category) ->");
		}

		List<CartBO> bos = cartRepo.findAllByCategoryAndVarifiedIsTrue(category);
		List<CartRs> rslist = CartMapper.mapToCartMinRsList(bos);
		String message = MessageCodes.MC_RETRIEVED_SUCCESSFUL;
		return ResponseUtils.success(new CartDataRsList(message, rslist));
	}

	@Override
	public BaseRs retrieveCart(String id) {

		if (log.isDebugEnabled()) {
			log.debug("Executing retrieveCartsByCategory(category) ->");
		}

		Optional<CartBO> optBo = cartRepo.findById(Long.valueOf(id));
		CartBO cartBO = null;
		if (!optBo.isEmpty()) {
			cartBO = optBo.get();
		}
		CartRs rs = CartMapper.mapToCartMinRs(cartBO);
		String message = MessageCodes.MC_RETRIEVED_SUCCESSFUL;
		return ResponseUtils.success(new CartDataRs(message, rs));
	}

	@Override
	public BaseRs retrieveCartByEmpType() {

		if (log.isDebugEnabled()) {
			log.debug("Executing retrieveCartsByCategory(category) ->");
		}

		List<CartBO> cartBOs = cartRepo.findAllBySellerid(AuthUtils.findLoggedInUser().getDocId());
		if (Utils.isEmpty(cartBOs)) {
			log.error(MessageCodes.MC_NO_RECORDS_FOUND);
			String message = MessageCodes.MC_NO_RECORDS_FOUND;
			return ResponseUtils.success(new CartDataRs(message));
		}
		List<CartRs> rsList = CartMapper.mapToCartRsList(cartBOs);
		String message = MessageCodes.MC_RETRIEVED_SUCCESSFUL;
		return ResponseUtils.success(new CartDataRsList(message, rsList));
	}

	@Override
	public BaseRs searchCartsByPname(String pname, int offset, int limit) {

		if (log.isDebugEnabled()) {
			log.debug("Executing searchCartsByPname(category) ->");
		}
		try {
			Pageable pageable = PageRequest.of(offset / limit, limit);
			Page<CartBO> cartPage = cartRepo.findByPnameContainingIgnoreCase(pname, pageable);
			List<CartRs> rslist = CartMapper.mapToCartMinRsList(cartPage.getContent());
			String message = MessageCodes.MC_RETRIEVED_SUCCESSFUL;
			return ResponseUtils.success(new CartDataRsList(message, rslist));
		} catch (Exception e) {
			log.error("Exception in searchCartsByPname(IdRq) - " + e);
			return ResponseUtils.failure(e);
		}
	}

}
