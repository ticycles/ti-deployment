package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackandtrail.model.registerbike.BikeModel;

public interface BikeModelRepository extends JpaRepository<BikeModel, Long>{
	
	List<BikeModel> findByBikeBrandId(Long id);


}


