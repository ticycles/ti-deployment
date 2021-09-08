package com.trackandtrail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackandtrail.model.registerbike.BikeBrand;

public interface BikeBrandRepository extends JpaRepository<BikeBrand, Long> {
	
	

}
