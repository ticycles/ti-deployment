package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;

public interface PincodeService {
	
	public BaseResponseDTO getById(Long cityId)throws Exception;


}
