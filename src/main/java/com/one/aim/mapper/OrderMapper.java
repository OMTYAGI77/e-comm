package com.one.aim.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.one.aim.bo.CartBO;
import com.one.aim.bo.OrderBO;
import com.one.aim.rs.OrderRs;
import com.one.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderMapper {

	public static OrderRs mapToOrderRs(OrderBO bo) {

		if (log.isDebugEnabled()) {
			log.debug("Executing mapToOrderRs(OrderBO) ->");
		}

		try {
			OrderRs rs = null;

			if (null == bo) {
				log.warn("UserBO is NULL");
				return rs;
			}
			rs = new OrderRs();

			rs.setDocId(String.valueOf(bo.getId()));
			long totalAmount = 0;
			if (Utils.isNotEmpty(bo.getOrderedItems())) {
				rs.setOrderedItems(CartMapper.mapToCartRsList(bo.getOrderedItems()));
				for (CartBO cartBO : bo.getOrderedItems()) {
					totalAmount = totalAmount + cartBO.getPrice();
				}
			}
			rs.setTotalAmount(totalAmount);
			rs.setOrderTime(bo.getOrderTime());
			rs.setPaymentMethod(bo.getPaymentMethod());
			return rs;
		} catch (Exception e) {
			log.error("Exception in mapToOrderRs(OrderBO) - " + e);
			return null;
		}
	}

	public static List<OrderRs> mapToOrderRsList(List<OrderBO> bos) {

		if (log.isDebugEnabled()) {
			log.debug("Executing mapToOrderRsList(OrderBO) ->");
		}

		try {
			if (Utils.isEmpty(bos)) {
				log.warn("OrderBO is NULL");
				return Collections.emptyList();
			}

			List<OrderRs> rsList = new ArrayList<>();
			for (OrderBO bo : bos) {
				OrderRs rs = mapToOrderRs(bo);
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
