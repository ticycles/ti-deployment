package com.trackandtrail.repository;

import java.util.Date;
import java.util.Optional;

import org.hibernate.type.LocalDateTimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.rideactivity.RideActivity;
import com.trackandtrail.view.RideActivityPoints;

public interface RideActivityPointsRepository extends JpaRepository<RideActivityPoints, Float> {
	

	@Query(nativeQuery = true,value = "select sum(avg_distance) as average_distance from ride_activity where user_id=:id and DATE(created_on)=:date ")
	RideActivityPoints getTodayOverallDistance(Long id,String date);
	
	
//	@Query(nativeQuery = true,value = "select sum(avg_distance) as average_distance from ride_activity where user_id=:id  ")
//	RideActivityPoints getTodayOverallDistance(Long id);
	
	
	@Query(nativeQuery = true,value = "select sum(avg_distance) as average_distance from ride_activity where user_id=:id ")
	Optional<RideActivityPoints> getOverallDistance(Long id);

	
}
