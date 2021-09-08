package com.trackandtrail.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.view.MyEarnPoints;

public interface MyEarnPointsRepository extends JpaRepository<MyEarnPoints, Integer> {
	
	
	@Query(nativeQuery = true,value = "SELECT sum(h.earn_point) as earn_point FROM points_earned_history h "
			+ "where h.user_id=:userId group by h.user_id")
	 Optional<MyEarnPoints> getSumEarnPointByUserId(Long userId);
	

}
