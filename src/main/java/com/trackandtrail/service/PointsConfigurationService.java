package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.PointsConfigurationDto;

public interface PointsConfigurationService {

	public BaseResponseDTO save(PointsConfigurationDto pointsConfigurationDto) throws Exception;

	public BaseResponseDTO getAllPoints() throws Exception;

	public BaseResponseDTO getMyPoint(Long userId) throws Exception;

}
