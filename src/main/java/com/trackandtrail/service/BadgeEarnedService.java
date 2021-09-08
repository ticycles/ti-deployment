package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;

public interface BadgeEarnedService {
	
	public BaseResponseDTO getAllBadgeByUserId(Long userId)throws Exception;
	

	
	public BaseResponseDTO getByUserIdOrderBycreatedDateDsc(Long userId)throws Exception;


}
