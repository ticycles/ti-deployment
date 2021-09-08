package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.NewsFeed.ContentNewsFeedCmnt;


@Repository
public interface ContentNewsFeedCmntRepository extends JpaRepository<ContentNewsFeedCmnt, Long> {

	List<ContentNewsFeedCmnt> findByContentNewsFeedId(Long id);

}
