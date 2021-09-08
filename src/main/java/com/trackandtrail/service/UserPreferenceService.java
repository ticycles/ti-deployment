package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.UserPreferenceDto;
import com.trackandtrail.model.userpreference.UserPreference;

public interface UserPreferenceService {
	
	

	public BaseResponseDTO update(UserPreferenceDto userPreferenceDto) throws Exception;
	
	
	
	public UserPreference getByUserId(Long userId)throws Exception;



}
