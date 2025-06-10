package com.one.aim.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.one.aim.bo.CartBO;
import com.one.aim.bo.UserBO;
import com.one.aim.bo.WishlistBO;
import com.one.aim.constants.ErrorCodes;
import com.one.aim.constants.MessageCodes;
import com.one.aim.mapper.WishlistMapper;
import com.one.aim.repo.CartRepo;
import com.one.aim.repo.UserRepo;
import com.one.aim.repo.WishlistRepo;
import com.one.aim.rs.WishlistRs;
import com.one.aim.rs.data.WishlistDataRs;
import com.one.aim.rs.data.WishlistDataRsList;
import com.one.aim.service.WishlistService;
import com.one.utils.AuthUtils;
import com.one.vm.core.BaseRs;
import com.one.vm.utils.ResponseUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WishlistServiceImpl implements WishlistService {

	@Autowired
	CartRepo cartRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	WishlistRepo wishlistRepo;

	@Override
	public BaseRs addToWishlist(String cartId) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing addToWishlist(cartId) ->");
		}

		Optional<CartBO> optCartBO = cartRepo.findById(Long.valueOf(cartId));

		if (optCartBO.isEmpty()) {
			log.error(ErrorCodes.EC_INVALID_INPUT);
			return ResponseUtils.failure(ErrorCodes.EC_INVALID_INPUT);
		}
		CartBO cartBO = optCartBO.get();
		WishlistBO wishlistBO = new WishlistBO();
		wishlistBO.setPname(cartBO.getPname());
		wishlistBO.setPrice(cartBO.getPrice());
		wishlistBO.setCategory(cartBO.getCategory());
		wishlistBO.setOffer(cartBO.getOffer());
		wishlistBO.setDescription(cartBO.getDescription());
		Optional<UserBO> optUserBO = userRepo.findById(AuthUtils.findLoggedInUser().getDocId());
		if (optUserBO.isEmpty()) {
			log.error(ErrorCodes.EC_USER_NOT_FOUND);
			return ResponseUtils.failure(ErrorCodes.EC_USER_NOT_FOUND);
		}
		wishlistBO.setUser(optUserBO.get());
		wishlistRepo.save(wishlistBO);
		String message = MessageCodes.MC_ADDED_SUCCESSFUL;
		return ResponseUtils.success(new WishlistDataRs(message));
	}

	@Override
	public BaseRs getUserWishlist() throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing getUserWishlist(cartId) ->");
		}
		Optional<UserBO> optUserBO = userRepo.findById(AuthUtils.findLoggedInUser().getDocId());
		if (optUserBO.isEmpty()) {
			log.error(ErrorCodes.EC_USER_NOT_FOUND);
			return ResponseUtils.failure(ErrorCodes.EC_USER_NOT_FOUND);
		}
		List<WishlistBO> wishlistBOs = wishlistRepo.findByUser(optUserBO.get());

		List<WishlistRs> WishlistRsList = WishlistMapper.mapToWishlistRsList(wishlistBOs);
		String message = MessageCodes.MC_RETRIEVED_SUCCESSFUL;
		return ResponseUtils.success(new WishlistDataRsList(message, WishlistRsList));
	}

	@Override
	public BaseRs deleteUserWishlist(String wishid) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing getUserWishlist(cartId) ->");
		}

		wishlistRepo.deleteById(Long.valueOf(wishid));
		String message = MessageCodes.MC_DELETED_SUCCESSFUL;
		return ResponseUtils.success(new WishlistDataRs(message));
	}

}
