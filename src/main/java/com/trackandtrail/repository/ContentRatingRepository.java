package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.content.ContentRating;
import com.trackandtrail.model.rideActivityComment.RideActivityComment;

public interface ContentRatingRepository extends JpaRepository<ContentRating, Long> {

	
	@Query(nativeQuery=true,value="select * from  content_rating c where c.content_id=:contentId and c.user_id =:userId")
	List<ContentRating> getRatingAndComment(Long userId, Long contentId);
	
	@Query(nativeQuery=true,value="select * from content_rating c where  c.content_id=:contentId")
	List<ContentRating> getRatingAndCommentByContentId(Long contentId);
	
	
	@Query(nativeQuery=true,value="select * from content_rating c where  c.content_id=:contentId and c.user_id=:userId")
	Optional<ContentRating> findBycontentrating(Long userId,Long contentId);
	
	
	
//	@Query(nativeQuery=true,value="select * from content_rating where is_like=:isLike and content_id=:contentId")
//	Optional<ContentRating> getByIsLike(Boolean isLike,Long contentId);
//	
//
//	@Query(nativeQuery=true,value="select * from content_rating where comment!=:comment and content_id=:contentId")
//	Optional<ContentRating> getByComment(String comment,Long contentId);
	
	
	@Query(nativeQuery = true, value = "SELECT * FROM content_rating p where p.content_id=:contentId and p.rating_star is not null")
	List<ContentRating> findByContentIdByRatingStar(Long contentId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM content_rating p where p.content_id=:contentId and p.is_like=1")
	List<ContentRating> findByContentIdByIsLike(Long contentId);
	
	
	@Query(nativeQuery = true, value = "SELECT * FROM content_rating p where p.content_id=:contentId and p.comment is not null")
	List<ContentRating> findByContentIdByComment(Long contentId);
	
	
	

	
	
}
