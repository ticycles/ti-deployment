package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.registerbike.Pincode;


@Repository
public interface PincodeRepository extends JpaRepository<Pincode, Long> {
	
	
	List<Pincode> findByCityId(Long cityId);

}
