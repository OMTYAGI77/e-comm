package com.one.aim.service;

import com.one.aim.rq.OrderRq;
import com.one.vm.core.BaseRs;

public interface OrderService {
	
	public BaseRs placeOrder(OrderRq rq) throws Exception;

}
