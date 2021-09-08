package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.rideactivity.RideActivity;
import com.trackandtrail.view.NewsFeed.ChallengeNewsFeed;

public interface ChallengeNewsFeedRepository extends JpaRepository<ChallengeNewsFeed, Long> {
	
	@Query(nativeQuery = true,value ="SELECT *,c.challenge_id,c.user_id FROM challenge c where c.user_id in "
			+ "(select f.user_id from user_follow f where f.following_user_id = :followingUserId and f.user_id != :followingUserId)"
			+ " ORDER BY c.challenge_id  DESC  ")
	List<ChallengeNewsFeed> challengesByFriends(Long followingUserId);
}


