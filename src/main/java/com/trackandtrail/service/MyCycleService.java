package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.MyCycleDto;

public interface MyCycleService {
	
	public BaseResponseDTO update(MyCycleDto myCycle) throws Exception;
	
	public BaseResponseDTO save(MyCycleDto myCycle) throws Exception;

	public BaseResponseDTO getAll() throws Exception;
	
	public BaseResponseDTO getByUserId(Long userId)throws Exception;
	




	
	
	

}
