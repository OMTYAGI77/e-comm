package com.one.aim.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.one.aim.bo.AddressBO;
import com.one.aim.bo.CartBO;
import com.one.aim.bo.OrderBO;
import com.one.aim.constants.MessageCodes;
import com.one.aim.repo.AddressRepo;
import com.one.aim.repo.CartRepo;
import com.one.aim.repo.OrderRepo;
import com.one.aim.repo.UserRepo;
import com.one.aim.rq.OrderRq;
import com.one.aim.rs.data.OrderDataRs;
import com.one.aim.service.OrderService;
import com.one.utils.AuthUtils;
import com.one.vm.core.BaseRs;
import com.one.vm.utils.ResponseUtils;

@Service
public class OderServiceImpl implements OrderService {

	@Autowired
	private UserRepo userRepo;

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
		order.setUserid(AuthUtils.findLoggedInUser().getDocId());;
		order.setTotalAmount(total);
		order.setPaymentMethod(rq.getPaymentMethod().toUpperCase());
		order.setOrderTime(LocalDateTime.now());
		order.setShippingAddress(address);
		order.setOrderedItems(cartItems);
		String message = MessageCodes.MC_SAVED_SUCCESSFUL;
		orderRepo.save(order);
		return ResponseUtils.success(new OrderDataRs(message));

	}

}
