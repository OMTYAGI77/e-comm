package com.one.aim.service;

import com.one.aim.rq.SellerRq;
import com.one.vm.core.BaseRs;

public interface SellerService {

	public BaseRs saveSeller(SellerRq rq) throws Exception;

	public BaseRs retrieveSeller() throws Exception;

	public BaseRs retrieveSellerCarts() throws Exception;;

}