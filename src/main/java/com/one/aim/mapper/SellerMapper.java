package com.one.aim.mapper;

import com.one.aim.bo.SellerBO;
import com.one.aim.rs.SellerRs;
import com.one.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SellerMapper {

	public static SellerRs mapToSellerRs(SellerBO bo) {

		if (log.isDebugEnabled()) {
			log.debug("Executing mapToAdminRs(AdminBO) ->");
		}

		try {
			SellerRs rs = null;

			if (null == bo) {
				log.warn("AdminBO is NULL");
				return rs;
			}
			rs = new SellerRs();
			rs.setDocId(String.valueOf(bo.getId()));
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
			log.error("Exception in mapToAdminRs(AdminBO) - " + e);
			return null;
		}
	}

}
