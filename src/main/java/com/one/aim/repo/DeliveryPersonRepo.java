package com.one.aim.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.one.aim.bo.DeliveryPersonBO;

public interface DeliveryPersonRepo extends JpaRepository<DeliveryPersonBO, Long> {

}
