package com.one.aim.rq;

import com.one.vm.core.BaseVM;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRq extends BaseVM {

	private static final long serialVersionUID = 1L;
	//private Long userId;
	private String paymentMethod;

	private String fullName;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String phone;

}
