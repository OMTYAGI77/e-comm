package com.one.aim.service;

import com.one.aim.rq.VendorRq;
import com.one.vm.core.BaseRs;

public interface VendorService {

	public BaseRs saveVendor(VendorRq rq) throws Exception;

	public BaseRs retrieveVendor() throws Exception;

	// public AdminBO getAdminBOById(Long id);

}
