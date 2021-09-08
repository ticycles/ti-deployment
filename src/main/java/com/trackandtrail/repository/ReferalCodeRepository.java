package com.trackandtrail.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.dto.ReferalCodeDto;
import com.trackandtrail.model.ReferalCode.ReferalCode;
import com.trackandtrail.model.userpreference.UserPreference;

public interface ReferalCodeRepository extends JpaRepository<ReferalCode, Long> {
	
	Optional<ReferalCode> findByUserId(Long id);
	
	
	ReferalCode findByUserId(String referalCode);
	
	
	Optional<ReferalCode> findByCode(String code);


}
