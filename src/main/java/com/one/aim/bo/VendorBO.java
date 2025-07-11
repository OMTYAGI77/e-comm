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
@Table(name = "vendor")
public class VendorBO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String username;

	private String email;

	private String phoneno;

	private String gst;

	private String adhaar;

	private String pancard;

	private String password;

	private boolean isVarified = false;

	private boolean login = false;

//	@ElementCollection(fetch = FetchType.LAZY)
//	@CollectionTable(name = "admin_attachments", joinColumns = @JoinColumn(name = "admin_id"))
//	private List<AttachmentBO> atts;

}
