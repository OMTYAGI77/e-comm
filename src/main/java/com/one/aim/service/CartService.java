package com.one.aim.service;

import com.one.aim.rq.CartRq;
import com.one.vm.core.BaseRs;

public interface CartService {

	public BaseRs saveCart(CartRq rq) throws Exception;

	public BaseRs retrieveCarts(int limit, int offset) throws Exception;

	public BaseRs retrieveCartsByCategory(String category);

	public BaseRs retrieveCart(String id);

	public BaseRs retrieveCartByEmpType();

	public BaseRs searchCartsByPname(String pname, int offset, int limit);

}
