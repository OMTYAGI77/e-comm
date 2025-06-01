package com.one.aim.service;

import com.one.aim.rq.AddressRq;
import com.one.vm.core.BaseRs;

public interface AddressService {

	public BaseRs saveAddress(AddressRq rq) throws Exception;

}
