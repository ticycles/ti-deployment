package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.bikeservicepackage.BikeServiceFeature;
import com.trackandtrail.model.rideactivity.RideActivity;


@Repository
public interface BikeServiceFeatureRepository extends JpaRepository<BikeServiceFeature, Long> {
	
	
	List<BikeServiceFeature> findByBikeServicePackageId(Long id);


}
