package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.rideactivity.RideActivity;
import com.trackandtrail.view.LeaderBoardFriends;

public interface LeaderBoardFriendsRepository extends JpaRepository<LeaderBoardFriends, Float>{
	
	@Query(nativeQuery = true,value ="SELECT sum(r.avg_distance) as avg_distance,r.user_id FROM ride_activity r "			
			+ " where r.user_id in (select f.user_id from user_follow f where f.following_user_id = :followingUserId and f.user_id != :followingUserId ) "
			+ "group by r.user_id")
	List<LeaderBoardFriends> findByUserId(Long followingUserId);


}
