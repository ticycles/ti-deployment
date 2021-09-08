package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.BadgeRewardConfDto;

public interface BadgeRewardService {

	public BaseResponseDTO save(BadgeRewardConfDto badgeRewardConfDto) throws Exception;


	public BaseResponseDTO getByModule(String module) throws Exception;


	public BaseResponseDTO getEarnPointsConfig() throws Exception;

}
