package com.one.aim.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.one.aim.bo.UserBO;

@Repository
public interface UserRepo extends JpaRepository<UserBO, Long> {

	public UserBO findByUsername(String username);

	public UserBO findByEmail(String email);
	
	public UserBO findByEmailOrUsername(String email, String username);

}
