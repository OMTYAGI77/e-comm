package com.one.aim.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.one.aim.bo.AddressBO;

@Repository
public interface AddressRepo extends JpaRepository<AddressBO, Long>{

}
