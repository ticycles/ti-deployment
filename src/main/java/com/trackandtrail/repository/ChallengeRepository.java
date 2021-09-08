package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.challenge.ChallengeUser;
import com.trackandtrail.model.rideactivity.RideActivity;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

	@Query(nativeQuery=true,value="select * from challenge where user_id =:id order  by created_on desc")
	List<Challenge> findByUserId(Long id);

	@Query(nativeQuery=true,value="select * from challenge  order  by created_on desc")
	List<Challenge> findAllChallenge();
	
	
	


	
}
