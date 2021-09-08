package com.trackandtrail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackandtrail.model.bikeservicepackage.BikeServicePackage;

public interface BikeServicePackageRepository extends JpaRepository<BikeServicePackage, Long> {

}
