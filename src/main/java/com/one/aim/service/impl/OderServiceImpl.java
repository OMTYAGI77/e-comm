package com.one.aim.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.one.aim.bo.AddressBO;
import com.one.aim.bo.AdminBO;
import com.one.aim.bo.CartBO;
import com.one.aim.bo.OrderBO;
import com.one.aim.bo.UserBO;
import com.one.aim.constants.ErrorCodes;
import com.one.aim.constants.MessageCodes;
import com.one.aim.mapper.OrderMapper;
import com.one.aim.repo.AddressRepo;
import com.one.aim.repo.AdminRepo;
import com.one.aim.repo.CartRepo;
import com.one.aim.repo.OrderRepo;
import com.one.aim.repo.UserRepo;
import com.one.aim.rq.OrderRq;
import com.one.aim.rs.OrderRs;
import com.one.aim.rs.data.OrderDataRs;
import com.one.aim.rs.data.OrderDataRsList;
import com.one.aim.service.OrderService;
import com.one.utils.AuthUtils;
import com.one.utils.Utils;
import com.one.vm.core.BaseRs;
import com.one.vm.utils.ResponseUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OderServiceImpl implements OrderService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private AdminRepo admnRepo;


	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private AddressRepo addressRepo;

	@Override
	public BaseRs placeOrder(OrderRq rq) throws Exception {
		// TODO Auto-generated method stub
//		UserBO userBO = userRepo.findById(rq.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

//		List<CartBO> cartItems = cartRepo.findByUserId(rq.getUserId()).stream().filter(CartBO::isEnabled)
//				.collect(Collectors.toList());
		List<CartBO> cartItems = cartRepo.findAll();

		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}

		Long total = cartItems.stream().map(CartBO::getPrice).reduce(0L, (a, b) -> a + b);

		AddressBO address = new AddressBO();
		address.setFullName(rq.getFullName());
		address.setStreet(rq.getStreet());
		address.setCity(rq.getCity());
		address.setState(rq.getState());
		address.setZip(rq.getZip());
		address.setCountry(rq.getCountry());
		address.setPhone(rq.getPhone());
		address.setUserid(AuthUtils.findLoggedInUser().getDocId());
		addressRepo.save(address);

		for (CartBO item : cartItems) {
			item.setEnabled(false); // Mark as dispatched
		}

		OrderBO order = new OrderBO();
		order.setUserid(AuthUtils.findLoggedInUser().getDocId());
		;
		order.setTotalAmount(total);
		order.setPaymentMethod(rq.getPaymentMethod().toUpperCase());
		order.setOrderTime(LocalDateTime.now());
		order.setShippingAddress(address);
		order.setOrderedItems(cartItems);
		String message = MessageCodes.MC_SAVED_SUCCESSFUL;
		orderRepo.save(order);
		return ResponseUtils.success(new OrderDataRs(message));

	}

	@Override
	public BaseRs retrieveOrder(Long id) throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing retrieveOrder() ->");
		}
		try {
//			Optional<UserBO> optUser = userRepo.findById(AuthUtils.findLoggedInUser().getDocId());
//			if (optUser.isEmpty()) {
//				log.error(ErrorCodes.EC_USER_NOT_FOUND);
//				return ResponseUtils.failure(ErrorCodes.EC_USER_NOT_FOUND);
//			}
			Optional<OrderBO> optOrder = orderRepo.findById(id);
			if (optOrder.isEmpty()) {
				log.error(ErrorCodes.EC_ORDER_NOT_FOUND);
				return ResponseUtils.failure(ErrorCodes.EC_ORDER_NOT_FOUND);
			}
			OrderBO orderBO = optOrder.get();
			OrderRs orderRs = OrderMapper.mapToOrderRs(orderBO);
			String message = MessageCodes.MC_RETRIEVED_SUCCESSFUL;
			return ResponseUtils.success(new OrderDataRs(message, orderRs));
		} catch (Exception e) {
			log.error("Exception in retrieveOrder() ->" + e);
			return null;
		}
	}

	@Override
	public BaseRs retrieveOrders() throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Executing retrieveOrders() ->");
		}
		try {
			Long id = AuthUtils.findLoggedInUser().getDocId();
			Optional<UserBO> optUser = userRepo.findById(id);
			Optional<AdminBO> admin=admnRepo.findById(id);
			if (optUser.isEmpty() && admin.isEmpty()) {
				log.error(ErrorCodes.EC_USER_NOT_FOUND);
				return ResponseUtils.failure(ErrorCodes.EC_USER_NOT_FOUND);
			}
			List<OrderBO> orderBOs = orderRepo.findAllByUserid(id);
			if (Utils.isEmpty(orderBOs)) {
				log.error(ErrorCodes.EC_RECORD_NOT_FOUND);
				return ResponseUtils.failure(ErrorCodes.EC_RECORD_NOT_FOUND);
			}
			List<OrderRs> rsList = OrderMapper.mapToOrderRsList(orderBOs);
			String message = MessageCodes.MC_RETRIEVED_SUCCESSFUL;
			return ResponseUtils.success(new OrderDataRsList(message, rsList));
		} catch (Exception e) {
			log.error("Exception in retrieveOrders() ->" + e);
			return null;
		}
	}

}
