package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.view.BikeFilter;

public interface BikeFilterRepository extends JpaRepository<BikeFilter, Integer> {
	
	
	@Query(nativeQuery = true,value = "select distinct size,color,brand_id,price from bike_rental_management")
	List<BikeFilter> findAllBikeFilter();

}
