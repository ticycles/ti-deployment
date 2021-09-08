package com.trackandtrail.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.rideactivity.RideActivity;

@Repository
public interface RideActivityRepository extends JpaRepository<RideActivity, Long> {

	List<RideActivity> findByUserId(Long id);
	
	
	@Query(nativeQuery = true,value ="SELECT * FROM ride_activity r LEFT JOIN ride_acitivity_count_view v on r.ride_id = v.ride_id where r.user_id in "
			+ "(select f.user_id from user_follow f where f.following_user_id = :followingUserId and f.user_id != :followingUserId)"
			+ " ORDER BY r.ride_id  DESC  ")
	List<RideActivity> findByFollowingUserId(Long followingUserId);


	@Query(nativeQuery = true,value = "select *,sum(avg_distance) as avg_distance from ride_activity where user_id=:id and created_on = current_date() ")
	RideActivity getTodayOverallDistance(Long id);


	

}

