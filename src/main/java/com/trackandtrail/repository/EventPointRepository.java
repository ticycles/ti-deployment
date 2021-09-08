package com.trackandtrail.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.view.ContentPoint;
import com.trackandtrail.view.EventPoint;

public interface EventPointRepository extends JpaRepository<EventPoint, Long> {
	
	@Query(nativeQuery=true,value="select count(user_id) as event_user_count from event where user_id=:id and DATE(created_on)=:todayDate ")
	EventPoint getEventUserCount(Long id, String todayDate);

	@Query(nativeQuery=true,value="select count(user_id) as event_user_count from event where user_id=:id ")
	Optional<EventPoint> getOverallPoints(Long id);

	@Query(nativeQuery=true,value="select count(user_id) as event_user_count from event where user_id=:id ")
	EventPoint getOverallUserPoints(Long id);
	
	@Query(nativeQuery = true, value ="select count(*) as event_user_count from event_user e left join event v on v.id=e.event_id join users u on u.user_id = v.user_id \r\n"
			+ "where u.role_id = 2 and date(v.created_on) = :todayDate and e.user_id = :id")
	Optional<EventPoint> findUserRole(Long id, String todayDate);

}
