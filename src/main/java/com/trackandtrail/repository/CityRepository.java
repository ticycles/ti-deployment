package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.registerbike.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{
	
	List<City> findByStateId(Long StateId);


}
