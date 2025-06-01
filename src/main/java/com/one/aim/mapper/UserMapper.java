package com.one.aim.mapper;

import com.one.aim.bo.UserBO;
import com.one.aim.rs.UserRs;
import com.one.utils.Utils;

import lombok.extern.slf4j.Slf4j;

//this data for front-end person
@Slf4j
public class UserMapper {

	public static UserRs mapToUserRs(UserBO bo) {

		if (log.isDebugEnabled()) {
			log.debug("Executing mapToUserRs(UserBO) ->");
		}

		try {
			UserRs rs = null;

			if (null == bo) {
				log.warn("UserBO is NULL");
				return rs;
			}
			rs = new UserRs();
			rs.setDocId(bo.getId());
			if (Utils.isNotEmpty(bo.getUsername())) {
				rs.setUserName(bo.getUsername());
			}
			if (Utils.isNotEmpty(bo.getPhoneno())) {
				rs.setPhoneNo(bo.getPhoneno());
			}
			if (Utils.isNotEmpty(bo.getEmail())) {
				rs.setEmail(bo.getEmail());
			}
			return rs;
		} catch (Exception e) {
			log.error("Exception in mapToUserRs(UserBO) - " + e);
			return null;
		}
	}

}
