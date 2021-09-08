package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.mycycle.MyCycle;
import com.trackandtrail.model.registerbike.BikeBrand;
import com.trackandtrail.model.userpreference.UserPreference;

@Repository
public interface MyCycleRepository extends JpaRepository<MyCycle, Long> {
	
	
	
	List<MyCycle> findByUserId(Long id);


}
