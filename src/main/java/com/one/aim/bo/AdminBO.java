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
	
	private boolean login = false;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "admin_attachments", joinColumns = @JoinColumn(name = "admin_id"))
	private List<AttachmentBO> atts;

}