package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.user.UserFollow;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
	
	UserFollow findByUserIdAndFollowingId(Long userId,Long unfollowerId);
	
	@Query(nativeQuery=true,value = "Select following_user_id from user_follow where user_id =:userId")
	List<Long> getFollowingIds(Long userId);
	
	@Query(nativeQuery=true,value="Select * from user_follow where user_id =:id and following_user_id=:followingId")
	Optional<UserFollow> findByUserAndFollowingId(Long id,Long followingId);

	
}
