package com.trackandtrail.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.configuration.BadgeConfiguration;
import com.trackandtrail.model.configuration.BadgeRewardConf;

public interface BadgeRewardConfRepository extends JpaRepository<BadgeRewardConf, Long>{
	
	@Query(nativeQuery=true,value="select * from badge_reward_conf where module =:module")
	Optional<BadgeRewardConf> findBadgeRewardConfModule(String module);

	@Query(nativeQuery=true,value="select * from badge_reward_conf where module =:module")
	Optional<BadgeRewardConf> deleteByModule(String module);

	

	
//	@Query(nativeQuery=true,value="select * from badge_reward_conf where activity_distance")
//	Optional<BadgeRewardConf> findByActivity();

}
