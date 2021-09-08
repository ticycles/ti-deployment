package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;

public interface LeaderBoardService {
	
	
	public BaseResponseDTO getAll() throws Exception;

	public BaseResponseDTO getByUserId(Long followingUserId)throws Exception;


}
