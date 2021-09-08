package com.trackandtrail.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.view.ContentPoint;

public interface ContentPointRepository extends JpaRepository<ContentPoint, Long> {

	
	@Query(nativeQuery=true,value="select count(user_id) as user_count_per_day from content where user_id=:id and DATE(created_on)=:todayDate ")
	ContentPoint getContentUserCount(Long id,String todayDate);

	
	@Query(nativeQuery = true,value = "select count(user_id) as user_count_per_day from content where user_id=:id ")
	Optional<ContentPoint> getOverallPoint(Long id);
}
