package com.one.aim.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.one.aim.bo.SellerBO;

@Repository
public interface SellerRepo extends JpaRepository<SellerBO, Long> {

	public SellerBO findByUsername(String username);

	public SellerBO findByIdAndUsername(Long id, String username);

}
