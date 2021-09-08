package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.NewsFeed.ChallengeNewsFeedCmnt;

@Repository
public interface ChallengeNewsFeedCmntRepository extends JpaRepository<ChallengeNewsFeedCmnt,Long>{

	List<ChallengeNewsFeedCmnt> findByChallengeNewsFeedId(Long id);


}
