package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.view.ChallengeCount;
import com.trackandtrail.view.EventCount;

public interface ChallengeCountRepository extends JpaRepository<ChallengeCount, Long> {
	
	
	@Query(nativeQuery=true,value="select  c.challenge_id,count(c.user_id) as joined from challenge_user c group by c.challenge_id")
	List<ChallengeCount> getChallengeMemberCount();


}
