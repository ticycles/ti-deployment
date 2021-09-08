package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.dto.request.BadgeConfigResponse;
import com.trackandtrail.model.configuration.BadgeConfiguration;

public interface BadgeConfigurationRepository extends JpaRepository<BadgeConfiguration, Long>{

	
	@Query(nativeQuery=true,value="select * from badge_configuration b "
			+ "left join badge_reward_conf c on c.badge_reward_conf_id = b.badge_reward_conf_id where c.module=:module")
	List<BadgeConfiguration> findByMModule(String module );

	
	@Query(nativeQuery=true,value="SELECT b.* FROM badge_configuration b LEFT JOIN badge_reward_conf f on f.badge_reward_conf_id = b.badge_reward_conf_id "
			+ "WHERE :averageDistance between b.range_from and b.range_to and f.module =:module ")
	Optional<BadgeConfiguration> getUserBadge(Float averageDistance, String module);


	@Query(nativeQuery=true,value="SELECT b.* FROM badge_configuration b LEFT JOIN badge_reward_conf f on f.badge_reward_conf_id = b.badge_reward_conf_id "
			+ "WHERE :userCountPerDay between b.range_from and b.range_to and f.module =:module ")
	Optional<BadgeConfiguration> getUserBadge(Long userCountPerDay, String module);

	
	

}
