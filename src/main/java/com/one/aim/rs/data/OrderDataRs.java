package com.one.aim.rs.data;

import com.one.vm.core.BaseDataRs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDataRs extends BaseDataRs {
	private static final long serialVersionUID = 1L;

	public OrderDataRs(String message) {
		super(message);
	}

}
