package com.one.aim.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.one.aim.bo.UserBO;
import com.one.aim.bo.WishlistBO;

@Repository
public interface WishlistRepo extends JpaRepository<WishlistBO, Long> {
	
    List<WishlistBO> findByUser(UserBO user);
}
