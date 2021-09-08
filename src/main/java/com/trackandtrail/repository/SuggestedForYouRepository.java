package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.view.NewsFeed.ContentNewsFeed;
import com.trackandtrail.view.NewsFeed.SuggestedForYou;

public interface SuggestedForYouRepository extends JpaRepository<SuggestedForYou, Long> {

	
	@Query(nativeQuery=true,value = "select  f.user_id as who,  f.following_user_id as whom, u.*  from user_follow f "
			+ "left join users u on u.user_id =  f.following_user_id "
			+ "where  f.user_id in(select distinct following_user_id from user_follow where user_id =:userId and following_user_id !=:userId)  and f.following_user_id !=:userId group by f.following_user_id")
	   List<SuggestedForYou> getSuggestedForYou(Long userId);

}
