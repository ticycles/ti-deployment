package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.challenge.ChallengeUser;
import com.trackandtrail.model.event.EventUser;
import com.trackandtrail.model.user.UserGroupMember;
import com.trackandtrail.view.EventCount;
import com.trackandtrail.view.EventPoint;

public interface EventUserRepository extends JpaRepository<EventUser, Long> {
	

	@Query(nativeQuery = true, value = "SELECT * FROM event_user e  where e.event_id =:eventId and e.user_id =:id")
	List<EventUser> getJoinInfo(Long id, Long eventId);

	@Query(nativeQuery = true, value = "select *  FROM event_user e where e.event_id=:eventId")
	List<EventUser> getOverAllParticipants(Long eventId);

	@Query(nativeQuery = true, value = "SELECT * FROM user_follow uf inner join event_user e on uf.following_user_id = e.user_id where e.event_id =:eventId and uf.user_id =:userId")
	List<EventUser> getRegisteredFriends(Long userId, Long eventId);
	
	EventUser findByEventIdAndUserId(Long eventId, Long userId);

	
	
	
	
}
