package com.one.aim.bo;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class CartBO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String pname;

	private String description;

	private long price;

	private String category;

	private boolean enabled = true;

	private int offer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserBO user;

	private Long sellerid;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "cart_attachments", joinColumns = @JoinColumn(name = "cart_id"))
	private List<AttachmentBO> cartatts;

}
