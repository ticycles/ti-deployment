package com.trackandtrail.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.view.ChallengePoints;

public interface ChallengePointsRepository extends JpaRepository<ChallengePoints, Long>{

	@Query(nativeQuery = true,value = "select count(user_id) as challenge_user_count from challenge where user_id=:id and DATE(created_on)=:todayDate ")
	ChallengePoints getChallengeUserCount(Long id, String todayDate);
	
	@Query(nativeQuery = true,value = "select count(user_id) as challenge_user_count from challenge_user where user_id=:id and DATE(created_on)=:todayDate ")
	ChallengePoints getJoinedChallengeUserCount(Long id, String todayDate);

	@Query(nativeQuery = true,value = "select count(user_id) as challenge_user_count from challenge where user_id=:id ")
	Optional<ChallengePoints> getOverallPoints(Long id);
	
	@Query(nativeQuery = true,value = "select count(user_id) as challenge_user_count from challenge_user where user_id=:id ")
	Optional<ChallengePoints> getOverallUserPoints(Long id);

}
