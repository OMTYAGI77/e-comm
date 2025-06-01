package com.one.aim.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.one.aim.bo.UserBO;

@Repository
public interface UserRepo extends JpaRepository<UserBO, Long> {

	UserBO findByUsername(String username);

}
