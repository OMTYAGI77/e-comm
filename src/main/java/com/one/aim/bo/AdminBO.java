package com.one.aim.bo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "admin")
public class AdminBO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String username;

	private String email;

	private String phoneno;

	private String password;

}