package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.configuration.PointsConfiguration;

public interface PointsConfigurationRepository extends JpaRepository<PointsConfiguration, Long> {

	@Query(nativeQuery=true,value="select * from points_configuration where pointReward=:pointReward")
	Optional<PointsConfiguration> findByNickNmae(List<PointsConfiguration> pointReward);
	
	
	
	@Query(nativeQuery = true,value = "SELECT * FROM points_configuration WHERE  :earnPoint between range_from and range_to LIMIT 1 ")
	Optional<PointsConfiguration> getPointConfigByEarnPoints(Integer earnPoint);

}
