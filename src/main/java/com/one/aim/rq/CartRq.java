package com.one.aim.rq;

import com.one.vm.core.BaseVM;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartRq extends BaseVM {

	private static final long serialVersionUID = 1L;

	private String docId;

	private String pName;

	private String description;

	private long price;

	private String category;

	private boolean enabled = true;

	private int offer;

}
