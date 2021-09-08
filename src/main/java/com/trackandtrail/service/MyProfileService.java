package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;


public interface MyProfileService {
	
	public BaseResponseDTO getByUserId(Long userId,String startDate,String endDate)throws Exception;
	

	public BaseResponseDTO getById(Long userId,String startDate,String endDate)throws Exception;


}
