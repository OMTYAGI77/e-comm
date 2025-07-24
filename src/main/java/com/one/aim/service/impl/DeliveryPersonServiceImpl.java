package com.one.aim.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.one.aim.bo.DeliveryPersonBO;
import com.one.aim.constants.ErrorCodes;
import com.one.aim.constants.MessageCodes;
import com.one.aim.mapper.DeliveryPersonMapper;
import com.one.aim.repo.DeliveryPersonRepo;
import com.one.aim.rq.DeliveryPersonRq;
import com.one.aim.rs.DeliveryPersonRs;
import com.one.aim.rs.data.DeliveryPersonDataRs;
import com.one.aim.service.DeliveryPersonService;
import com.one.constants.StringConstants;
import com.one.utils.Utils;
import com.one.vm.core.BaseRs;
import com.one.vm.utils.ResponseUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeliveryPersonServiceImpl implements DeliveryPersonService {
	
	@Autowired
	DeliveryPersonRepo deliveryPersonRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public BaseRs saveDeliveryPerson(DeliveryPersonRq rq) {
		
		if (log.isDebugEnabled()) {
			log.debug("Executing saveDeliveryPerson(CompanyRq) ->");
		}

		//List<String> errors = UserHelper.validateUser(rq);

//		if (Utils.isNotEmpty(errors)) {
//			log.error(ErrorCodes.EC_INVALID_INPUT);
//			return ResponseUtils.failure(ErrorCodes.EC_INVALID_INPUT, errors);
//		}
		String docId = Utils.getValidString(rq.getDocId());
		String message = StringConstants.EMPTY;
		DeliveryPersonBO userBO = null;
		if (Utils.isNotEmpty(docId)) { // UPDATE
			long id = Long.parseLong(docId);
			Optional<DeliveryPersonBO> optUserBO = deliveryPersonRepo.findById(id);
			userBO = optUserBO.get();
			if (userBO == null) {
				log.error(ErrorCodes.EC_USER_NOT_FOUND);
				return ResponseUtils.failure(ErrorCodes.EC_USER_NOT_FOUND);
			}
		} else {
			userBO = new DeliveryPersonBO(); // SAVE
			message = MessageCodes.MC_SAVED_SUCCESSFUL;
		}
		String email = Utils.getValidString(rq.getEmail());
		if (!email.equals(Utils.getValidString(userBO.getEmail()))) {
			userBO.setEmail(email);
		}
		String userName = Utils.getValidString(rq.getName());
		if (!userName.equals(userBO.getName())) {
			userBO.setName(userName);
		}
		String phoneNo = Utils.getValidString(rq.getPhoneno());
		if (!phoneNo.equals(userBO.getPhone())) {
			userBO.setPhone(phoneNo);
		}
		String rawPassword = Utils.getValidString(rq.getPassword());
		String existingEncodedPassword = userBO.getPassword();

		if (!passwordEncoder.matches(rawPassword, existingEncodedPassword)) {
			String hashedPassword = passwordEncoder.encode(rawPassword);
			System.out.println("Hashed Password: " + hashedPassword);
			userBO.setPassword(hashedPassword);
		}
//		userBO.setAtts(fileService.prepareAttBOs(rq.getElExemptionAtts(), null));
		deliveryPersonRepo.save(userBO);
		DeliveryPersonRs deliveryPersonRs = DeliveryPersonMapper.mapToDeliveryPersonRs(userBO);
		return ResponseUtils.success(new DeliveryPersonDataRs(message, deliveryPersonRs));
	}

}
