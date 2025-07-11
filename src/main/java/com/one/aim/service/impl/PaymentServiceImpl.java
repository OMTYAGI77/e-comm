package com.one.aim.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.one.aim.bo.PaymentBO;
import com.one.aim.bo.UserBO;
import com.one.aim.constants.ErrorCodes;
import com.one.aim.constants.MessageCodes;
import com.one.aim.mapper.PaymentMapper;
import com.one.aim.repo.PaymentRepo;
import com.one.aim.repo.UserRepo;
import com.one.aim.rq.PaymentRq;
import com.one.aim.rs.PaymentRs;
import com.one.aim.rs.data.PaymentDataRs;
import com.one.aim.service.PaymentService;
import com.one.constants.StringConstants;
import com.one.utils.AuthUtils;
import com.one.vm.core.BaseRs;
import com.one.vm.utils.ResponseUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	PaymentRepo paymentRepo;

	@Override
	public BaseRs processPayment(PaymentRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing saveCompany(CompanyRq) ->");
		}

		Long userId = AuthUtils.findLoggedInUser().getDocId();
		String message = StringConstants.EMPTY;
		UserBO userBO = null;
		if (null != userId) { // UPDATE
			Optional<UserBO> optUserBO = userRepo.findById(userId);
			userBO = optUserBO.get();
			if (userBO == null) {
				log.error(ErrorCodes.EC_USER_NOT_FOUND);
				return ResponseUtils.failure(ErrorCodes.EC_USER_NOT_FOUND);
			}
		} else {
			userBO = new UserBO(); // SAVE
			message = MessageCodes.MC_SAVED_SUCCESSFUL;
		}
		PaymentBO paymentBO = new PaymentBO();
		paymentBO.setAmount(Long.parseLong(rq.getAmount()));
		paymentBO.setPaymentMethod(rq.getPaymentMethod());
		paymentBO.setPaymentTime(LocalDateTime.now());
		paymentBO.setUser(userBO);
		paymentRepo.save(paymentBO);
		PaymentRs paymentRs = PaymentMapper.mapToPaymentRs(paymentBO);
		return ResponseUtils.success(new PaymentDataRs(message, paymentRs));
	}

}
