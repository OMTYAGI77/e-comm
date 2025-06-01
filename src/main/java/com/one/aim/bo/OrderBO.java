package com.one.aim.bo;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderBO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long totalAmount;

	private String paymentMethod;

	private LocalDateTime orderTime;

	private Long userid;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private AddressBO shippingAddress;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	private List<CartBO> orderedItems;

}
