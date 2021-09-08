package com.trackandtrail.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackandtrail.model.ReferalCode.ReferAndEarn;
import com.trackandtrail.model.ReferalCode.ReferalCode;

public interface ReferAndEarnRepository extends JpaRepository<ReferAndEarn, Long> {
	
	
	Optional<ReferAndEarn> findByUserId(Long id);


}
