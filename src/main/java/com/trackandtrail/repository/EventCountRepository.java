package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.event.Event;
import com.trackandtrail.view.EventCount;

public interface EventCountRepository extends JpaRepository<EventCount, Long>{
	
	@Query(nativeQuery=true,value="select  e.*,count(e.user_id) as joined from event_user e group by e.event_id")
	List<EventCount> getEventMemberCount();

}
