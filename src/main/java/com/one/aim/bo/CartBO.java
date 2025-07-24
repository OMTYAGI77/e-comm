package com.one.aim.bo;

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

	private boolean varified = false;

	private boolean enabled = true;

	private int totalitem;
	
	private int solditem;
	
	private int offer;

	private Long cartempid;
	
	private String cartempname;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserBO user;

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name = "cart_id") // creates a unidirectional relationship
//	private List<AttachmentBO> cartatts = new ArrayList<>();

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name = "cart_id")
//	private List<AttachmentBO> cartatts;

}
