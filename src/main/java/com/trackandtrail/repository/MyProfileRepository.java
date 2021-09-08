package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.rideactivity.RideActivity;
import com.trackandtrail.view.MyProfile;

public interface MyProfileRepository extends JpaRepository<MyProfile, Float> {

	@Query(nativeQuery = true, value = "SELECT avg(avg_distance) as distance, avg(avg_speed) as speed, avg(avg_time) as ride_time,  user_id  FROM ride_activity where user_id = :id and DATE(created_on) >= :startDate  and  DATE(created_on) <= :endDate ")
	List<MyProfile> findByUserId(Long id, String startDate, String endDate);


	

}



