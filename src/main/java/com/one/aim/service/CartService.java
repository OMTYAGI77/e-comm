package com.one.aim.service;

import com.one.aim.rq.CartRq;
import com.one.vm.core.BaseRs;

public interface CartService {
	
	public BaseRs saveCart(CartRq rq) throws Exception;
	
	public BaseRs retrieveCart() throws Exception;


}
