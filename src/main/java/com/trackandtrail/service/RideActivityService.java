package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.RideActivityDto;

public interface RideActivityService {
		
	public BaseResponseDTO save(RideActivityDto rideActivityDTO) throws Exception;
	
	public BaseResponseDTO getByUserId(Long userId)throws Exception;
	
	public BaseResponseDTO getByRideActivityUserId(Long followingUserId)throws Exception;

	
	public BaseResponseDTO getByRideId(Long userRideId)throws Exception;

	public BaseResponseDTO getByCurrentUserId() throws Exception;

}
