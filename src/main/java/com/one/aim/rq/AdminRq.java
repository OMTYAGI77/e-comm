package com.one.aim.rq;

import java.util.Collections;
import java.util.List;

import com.one.vm.core.BaseVM;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminRq extends BaseVM {

	private static final long serialVersionUID = 1L;

	private String docId;

	private String userName;

	private String email;

	private String phoneNo;

	private String password;
	
	 private List<AttachmentRq> elExemptionAtts = Collections.emptyList();
}
