package com.trackandtrail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackandtrail.model.bikerentalmanagement.StoreDetail;

public interface StoreDetailRepository extends JpaRepository<StoreDetail, Long> {

	StoreDetail findByNameIgnoreCase(String name);	

}
