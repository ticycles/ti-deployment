package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;

public interface BikeModelService {
	
	
	public BaseResponseDTO getByBrandId(Long brandId)throws Exception;


}
