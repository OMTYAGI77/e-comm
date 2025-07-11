package com.one.aim.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.one.aim.bo.OrderBO;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderBO, Long> {

	List<OrderBO> findAllByUserid(Long userid);

}