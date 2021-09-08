package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.configuration.BadgeEarned;

public interface BadgeEarnedRepository extends JpaRepository<BadgeEarned, Long> {
	
	

	@Query(nativeQuery=true,value="select * from badges_earned where range_from =:rangeFrom and range_to =:rangeTo and user_id =:id and badge_name =:badgeName and module =:module")
	Optional<BadgeEarned> findRange(Float rangeFrom, Float rangeTo, Long id,String badgeName, String module);
	
	List<BadgeEarned> findAllBadgesByUserId(Long userId);

	

	List<BadgeEarned> findByUserIdOrderByCreatedDateDesc(Long userId);


	

}
