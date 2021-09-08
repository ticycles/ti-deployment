package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trackandtrail.dto.EventProjectionDto;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalBooking;
import com.trackandtrail.model.event.Event;
import com.trackandtrail.model.event.EventUser;
import com.trackandtrail.model.rideactivity.RideActivity;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

	@Query(nativeQuery=true,value="select * from event where user_id =:userId order  by created_on desc")
	List<Event> findByUserId(Long userId);
	
	
	@Query(nativeQuery=true,value="select * from event e where  e.event_type_id=:eventTypeId and e.user_id=:userId")
	Optional<Event> findByEvent(Long userId,Long eventTypeId);
	
	
	@Query(nativeQuery=true,value="select  e.*,count(u.user_id) as members_joined from event e left join event_user u on u.event_id = e.id group by e.id order by created_on desc")
	List<Event> getEventList();
	
	@Query(nativeQuery=true,value="select  e.*,count(u.user_id) as members_joined from event e left join event_user u on u.event_id = e.id  where e.id =:id group by e.id ")
	Optional<Event> getEvent(Long id);
	
	


}
