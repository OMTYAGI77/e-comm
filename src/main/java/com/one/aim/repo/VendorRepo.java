package com.one.aim.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.one.aim.bo.VendorBO;

@Repository
public interface VendorRepo extends JpaRepository<VendorBO, Long> {

	public VendorBO findByUsername(String username);

	public VendorBO findByIdAndUsername(Long id, String username);

	public VendorBO findByEmailOrUsername(String email, String username);

}