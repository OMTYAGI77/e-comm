package com.one.aim.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.one.aim.bo.UserBO;
import com.one.aim.rs.UserRs;

@Repository
public interface UserRepo extends JpaRepository<UserBO, Long> {

	public UserBO findByUsername(String username);

	
}
