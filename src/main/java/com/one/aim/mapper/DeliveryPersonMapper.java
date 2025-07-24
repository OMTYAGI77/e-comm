package com.one.aim.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.one.aim.bo.DeliveryPersonBO;
import com.one.aim.rs.DeliveryPersonRs;
import com.one.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeliveryPersonMapper {

	public static DeliveryPersonRs mapToDeliveryPersonRs(DeliveryPersonBO bo) {

		if (log.isDebugEnabled()) {
			log.debug("Executing mapToDeliveryPersonRs(UserBO) ->");
		}

		try {
			DeliveryPersonRs rs = null;

			if (null == bo) {
				log.warn("UserBO is NULL");
				return rs;
			}
			rs = new DeliveryPersonRs();
			rs.setDocId(bo.getId());
			if (Utils.isNotEmpty(bo.getName())) {
				rs.setName(bo.getName());
			}
			if (Utils.isNotEmpty(bo.getPhone())) {
				rs.setPhone(bo.getPhone());
			}
			if (Utils.isNotEmpty(bo.getEmail())) {
				rs.setEmail(bo.getEmail());
			}
			if (Utils.isNotEmpty(bo.getOrders())) {
				rs.setOrders(OrderMapper.mapToOrderRsList(bo.getOrders()));
			}
			// rs.setAtts(AttachmentMapper.mapToAttachmentRsList(bo.getAtts()));
			return rs;
		} catch (Exception e) {
			log.error("Exception in mapToDeliveryPersonRs(UserBO) - " + e);
			return null;
		}
	}

	public static List<DeliveryPersonRs> mapToDeliveryPersonRsList(List<DeliveryPersonBO> bos) {

		if (log.isDebugEnabled()) {
			log.debug("Executing mapToOrderRsList(OrderBO) ->");
		}

		try {
			if (Utils.isEmpty(bos)) {
				log.warn("OrderBO is NULL");
				return Collections.emptyList();
			}

			List<DeliveryPersonRs> rsList = new ArrayList<>();
			for (DeliveryPersonBO bo : bos) {
				DeliveryPersonRs rs = mapToDeliveryPersonRs(bo);
				if (null != rs) {
					rsList.add(rs);
				}
			}
			return rsList;
		} catch (Exception e) {
			log.error("Exception in mapToOrderRsList(OrderBO) - " + e);
			return Collections.emptyList();
		}
	}

}
