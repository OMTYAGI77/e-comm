package com.one.aim.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.one.aim.bo.CartBO;
import com.one.aim.rs.CartRs;
import com.one.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CartMapper {

	public static CartRs mapToCartMinRs(CartBO bo) {

		if (log.isDebugEnabled()) {
			log.debug("Executing mapToCartRs(CartBO) ->");
		}

		try {
			CartRs rs = null;

			if (null == bo) {
				log.warn("UserBO is NULL");
				return rs;
			}
			rs = new CartRs();
			rs.setDocId(String.valueOf(bo.getId()));
			if (Utils.isNotEmpty(bo.getPname())) {
				rs.setPName(bo.getPname());
			}
			if (Utils.isNotEmpty(bo.getDescription())) {
				rs.setDescription(bo.getDescription());
			}
			if (Utils.isNotEmpty(bo.getCategory())) {
				rs.setCategory(bo.getCategory());
			}
			rs.setPrice(bo.getPrice());
			rs.setOffer(bo.getOffer());
			rs.setAtts(AttachmentMapper.mapToAttachmentRsList(bo.getCartatts()));
			return rs;
		} catch (Exception e) {
			log.error("Exception in mapToCartRs(CartBO) - " + e);
			return null;
		}
	}

	public static List<CartRs> mapToCartMinRsList(List<CartBO> bos) {

		if (log.isDebugEnabled()) {
			log.debug("Executing mapToCartRsList(CartBO) ->");
		}

		try {
			if (Utils.isEmpty(bos)) {
				log.warn("UserBO is NULL");
				return Collections.emptyList();
			}
			List<CartRs> rsList = new ArrayList<>();
			for (CartBO bo : bos) {
				CartRs rs = mapToCartMinRs(bo);
				if (null != rs) {
					rsList.add(rs);
				}
			}
			return rsList;
		} catch (Exception e) {
			log.error("Exception in mapToCartRsList(CartBO) - " + e);
			return Collections.emptyList();
		}
	}

	public static CartRs mapToCartRs(CartBO bo) {

		if (log.isDebugEnabled()) {
			log.debug("Executing mapToCartRs(CartBO) ->");
		}

		try {
			CartRs rs = null;

			if (null == bo) {
				log.warn("UserBO is NULL");
				return rs;
			}
			rs = new CartRs();
			rs.setDocId(String.valueOf(bo.getId()));
			if (Utils.isNotEmpty(bo.getPname())) {
				rs.setPName(bo.getPname());
			}
			if (Utils.isNotEmpty(bo.getDescription())) {
				rs.setDescription(bo.getDescription());
			}
			if (Utils.isNotEmpty(bo.getCategory())) {
				rs.setCategory(bo.getCategory());
			}
			rs.setTotalItem(bo.getTotalitem());
			rs.setSoldItem(bo.getSolditem());
			rs.setPrice(bo.getPrice());
			rs.setOffer(bo.getOffer());
			rs.setVarified(bo.isVarified());

			rs.setAtts(AttachmentMapper.mapToAttachmentRsList(bo.getCartatts()));
			return rs;
		} catch (Exception e) {
			log.error("Exception in mapToCartRs(CartBO) - " + e);
			return null;
		}
	}

	public static List<CartRs> mapToCartRsList(List<CartBO> bos) {

		if (log.isDebugEnabled()) {
			log.debug("Executing mapToCartRsList(CartBO) ->");
		}

		try {
			if (Utils.isEmpty(bos)) {
				log.warn("UserBO is NULL");
				return Collections.emptyList();
			}
			List<CartRs> rsList = new ArrayList<>();
			for (CartBO bo : bos) {
				CartRs rs = mapToCartRs(bo);
				if (null != rs) {
					rsList.add(rs);
				}
			}
			return rsList;
		} catch (Exception e) {
			log.error("Exception in mapToCartRsList(CartBO) - " + e);
			return Collections.emptyList();
		}
	}

}
