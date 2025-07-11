package com.one.aim.rs;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartRs implements Serializable {

	private static final long serialVersionUID = 1L;

	private String docId;

	private String pName;

	private String description;

	private long price;

	private String category;

	private boolean enabled = true;

	private int offer;
	
	private boolean isVarified;
	
	private int totalItem;

	private int soldItem;

	private List<AttachmentRs> atts = Collections.emptyList();

}
