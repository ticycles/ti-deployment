package com.trackandtrail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackandtrail.model.challenge.RideType;

public interface RideTypeRepository extends JpaRepository<RideType, Long> {

}
