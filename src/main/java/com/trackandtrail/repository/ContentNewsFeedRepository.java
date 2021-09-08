package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.view.NewsFeed.ChallengeNewsFeed;
import com.trackandtrail.view.NewsFeed.ContentNewsFeed;

public interface ContentNewsFeedRepository extends JpaRepository<ContentNewsFeed, Long> {

	@Query(nativeQuery = true,value ="SELECT *,c.content_id,c.user_id FROM content c where c.user_id in "
			+ "(select f.user_id from user_follow f where f.following_user_id = :followingUserId and f.user_id != :followingUserId)"
			+ " ORDER BY c.content_id  DESC  ")
	List<ContentNewsFeed> contentByFriends(Long followingUserId);
	
	
}

