package com.one.aim.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.one.aim.bo.AdminBO;

@Repository
public interface AdminRepo extends JpaRepository<AdminBO, Long> {

	public AdminBO findByUsername(String username);

	public AdminBO findByIdAndUsername(Long id, String username);

	public AdminBO findByEmailOrUsername(String email, String username);

}
