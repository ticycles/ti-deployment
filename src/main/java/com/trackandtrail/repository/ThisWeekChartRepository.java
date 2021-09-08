package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.rideactivity.RideActivity;

public interface ThisWeekChartRepository  extends JpaRepository<RideActivity, Long>{
	
	
	@Query(nativeQuery = true, value = "SELECT * FROM ride_activity r   where r.user_id = :id and DATE(created_on) >= :startDate  and  DATE(created_on) <= :endDate")
	List<RideActivity> findByMyProfileUserId(Long id, String startDate, String endDate);

}
