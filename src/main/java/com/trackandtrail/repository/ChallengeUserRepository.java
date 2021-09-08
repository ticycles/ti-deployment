package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.challenge.ChallengeUser;
import com.trackandtrail.model.event.EventUser;

@Repository
public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM challenge_user c  where c.challenge_id =:challengeId and c.user_id =:id")
	List<ChallengeUser> getJoinInfo(Long id, Long challengeId);

	@Query(nativeQuery = true, value = "select *  FROM challenge_user u where u.challenge_id=:challengeId")
	List<ChallengeUser> getOverAllParticipants(Long challengeId);

	@Query(nativeQuery = true, value = "SELECT * FROM user_follow uf inner join challenge_user c on uf.following_user_id = c.user_id where c.challenge_id =:challengeId and uf.user_id =:userId")
	List<ChallengeUser> getRegisteredFriends(Long userId, Long challengeId);
	
	
	@Query(nativeQuery=true,value = "select * from challenge where user_id=:id")
	Optional<ChallengeUser> findByUserModelId(Long id);

	ChallengeUser findByChallengeIdAndUserId(Long challengeId, Long userId);
	
	List<ChallengeUser> findByUserId(Long userId);

}