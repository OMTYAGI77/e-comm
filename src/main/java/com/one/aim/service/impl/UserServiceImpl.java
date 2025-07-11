package com.one.aim.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.one.aim.bo.AdminBO;
import com.one.aim.bo.SellerBO;
import com.one.aim.bo.UserBO;
import com.one.aim.bo.UserSessionBO;
import com.one.aim.constants.ErrorCodes;
import com.one.aim.constants.MessageCodes;
import com.one.aim.helper.UserHelper;
import com.one.aim.mapper.UserMapper;
import com.one.aim.repo.AdminRepo;
import com.one.aim.repo.SellerRepo;
import com.one.aim.repo.UserRepo;
import com.one.aim.repo.UserSessionRepo;
import com.one.aim.rq.UserRq;
import com.one.aim.rs.UserRs;
import com.one.aim.rs.data.LoginDataRs;
import com.one.aim.rs.data.UserDataRs;
import com.one.aim.service.FileService;
import com.one.aim.service.UserService;
import com.one.constants.StringConstants;
import com.one.security.jwt.JwtUtils;
import com.one.service.impl.UserDetailsImpl;
import com.one.utils.AuthUtils;
import com.one.utils.Utils;
import com.one.vm.core.BaseRs;
import com.one.vm.utils.ResponseUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserSessionRepo userSessionRepo;

	@Autowired
	SellerRepo sellerRepo;

	@Autowired
	AdminRepo adminRepo;

	@Autowired
	FileService fileService;

	// Sign Up
	@Override
	public BaseRs saveUser(UserRq rq) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing saveCompany(CompanyRq) ->");
		}

		List<String> errors = UserHelper.validateUser(rq);

		if (Utils.isNotEmpty(errors)) {
			log.error(ErrorCodes.EC_INVALID_INPUT);
			return ResponseUtils.failure(ErrorCodes.EC_INVALID_INPUT, errors);
		}
		String docId = Utils.getValidString(rq.getDocId());
		String message = StringConstants.EMPTY;
		UserBO userBO = null;
		if (Utils.isNotEmpty(docId)) { // UPDATE
			long id = Long.parseLong(docId);
			Optional<UserBO> optUserBO = userRepo.findById(id);
			userBO = optUserBO.get();
			if (userBO == null) {
				log.error(ErrorCodes.EC_USER_NOT_FOUND);
				return ResponseUtils.failure(ErrorCodes.EC_USER_NOT_FOUND);
			}
		} else {
			userBO = new UserBO(); // SAVE
			message = MessageCodes.MC_SAVED_SUCCESSFUL;
		}
		String email = Utils.getValidString(rq.getEmail());
		if (!email.equals(Utils.getValidString(userBO.getEmail()))) {
			userBO.setEmail(email);
		}
		String userName = Utils.getValidString(rq.getUserName());
		if (!userName.equals(userBO.getUsername())) {
			userBO.setUsername(userName);
		}
		String phoneNo = Utils.getValidString(rq.getPhoneNo());
		if (!phoneNo.equals(userBO.getPhoneno())) {
			userBO.setPhoneno(phoneNo);
		}
		String rawPassword = Utils.getValidString(rq.getPassword());
		String existingEncodedPassword = userBO.getPassword();

		if (!passwordEncoder.matches(rawPassword, existingEncodedPassword)) {
			String hashedPassword = passwordEncoder.encode(rawPassword);
			System.out.println("Hashed Password: " + hashedPassword);
			userBO.setPassword(hashedPassword);
		}
		userBO.setAtts(fileService.prepareAttBOs(rq.getElExemptionAtts(), null));
		userRepo.save(userBO);
		UserRs userRs = UserMapper.mapToUserRs(userBO);
		return ResponseUtils.success(new UserDataRs(message, userRs));
	}

	@Override
	public BaseRs signIn(Authentication authentication) throws Exception {

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		String accessToken = jwtUtils.generateAccessToken(authentication);
		String refreshToken = jwtUtils.generateRefreshToken(authentication);

//		UserBO user = userRepo.findByEmailOrUsername(usernameOrEmpNumber, usernameOrEmpNumber);
//		AdminBO admin = adminRepo.findByEmailOrUsername(usernameOrEmpNumber, usernameOrEmpNumber);
//		SellerBO seller = sellerRepo.findByEmailOrUsername(usernameOrEmpNumber, usernameOrEmpNumber);
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
//				sellerRepo.save(seller);
//			}
//		}
		UserSessionBO usBO = new UserSessionBO();
		usBO.setUsername(userDetails.getUsername());
		usBO.setId(userDetails.getId());
		usBO.setRefreshtoken(refreshToken);
		usBO.setLoggedintime(LocalDateTime.now());
		// userSessionRepo.save(usBO);

		String message = MessageCodes.MC_LOGIN_SUCCESSFUL;
		return ResponseUtils.success(new LoginDataRs(message, accessToken, refreshToken, userDetails.getId(),
				userDetails.getUsername(), userDetails.getName(), userDetails.getEmail()));
	}

//	public UserBO getUserBOById(Long id) {
//		Optional<UserBO> user = userRepo.findById(id);
//        return user.orElse(null);
//	}

//	public UserBO getUserBOById(Long id) {
//		Optional<UserBO> user = userRepo.findById(AuthUtils.findLoggedInUser().getDocId());
//        return user.orElse(null);
//	}

	public BaseRs retrieveUser() throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing retrieveUser() ->");
		}
		try {
			Optional<UserBO> optUser = userRepo.findById(AuthUtils.findLoggedInUser().getDocId());
			if (optUser.isEmpty()) {
				log.error(ErrorCodes.EC_USER_NOT_FOUND);
				return ResponseUtils.failure(ErrorCodes.EC_USER_NOT_FOUND);
			}
			UserBO userBO = optUser.get();
			UserRs userRs = UserMapper.mapToUserRs(userBO);
			String message = MessageCodes.MC_RETRIEVED_SUCCESSFUL;
			return ResponseUtils.success(new UserDataRs(message, userRs));
		} catch (Exception e) {
			log.error("Exception retrieveUser() ->" + e);
			return null;
		}

	}

	@Override
	public BaseRs logout() throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing saveCompany(CompanyRq) ->");
		}

		Long id = AuthUtils.findLoggedInUser().getDocId();
		Optional<UserBO> OptUserBO = userRepo.findById(id);
		if (!OptUserBO.isEmpty()) {
			UserBO userBO = OptUserBO.get();
			userBO.setLogin(false);
			userRepo.save(userBO);
		}
		Optional<AdminBO> OptAdminBO = adminRepo.findById(id);
		if (!OptAdminBO.isEmpty()) {
			AdminBO adminBO = OptAdminBO.get();
			adminBO.setLogin(false);
			adminRepo.save(adminBO);
		}
		Optional<SellerBO> OptSellerBO = sellerRepo.findById(id);
		if (!OptSellerBO.isEmpty()) {
			SellerBO sellerBO = OptSellerBO.get();
			sellerBO.setLogin(false);
			sellerRepo.save(sellerBO);
		}
		// userSessionRepo.save(usBO);

		String message = MessageCodes.MC_LOGOUT_SUCCESSFUL;
		SecurityContextHolder.clearContext();
		return ResponseUtils.success(new LoginDataRs(message));
	}

}
