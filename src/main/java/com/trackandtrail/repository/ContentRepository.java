package com.trackandtrail.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.content.Content;
import com.trackandtrail.model.event.Event;
import com.trackandtrail.view.ContentPoint;


@Repository
public interface ContentRepository extends JpaRepository<Content,Long> {
	
	
	@Modifying
	@Transactional
	@Query(nativeQuery=true,value="update content set views=views+1 where  content_id =:contentId")
	int updateViewsCount(Long contentId);
	
	@Query(nativeQuery=true,value="select * from content where user_id =:userId order  by created_on desc")
	List<Content> findByUserId(Long userId);

	@Query(nativeQuery=true,value="select * from content order  by created_on desc")
	List<Content> findAllContent();


	




}
