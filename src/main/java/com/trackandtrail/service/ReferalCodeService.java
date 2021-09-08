package com.trackandtrail.service;

import org.springframework.http.ResponseEntity;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ReferAndEarnDto;
import com.trackandtrail.dto.ReferalCodeDto;
import com.trackandtrail.dto.UserPreferenceDto;

public interface ReferalCodeService {
	
	
	public BaseResponseDTO save(ReferalCodeDto referalCodeDto) throws Exception;
	
	public BaseResponseDTO create(ReferAndEarnDto referalAndEarnDto) throws Exception;



}
