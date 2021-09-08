package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.PointsEarnedHistoryDto;

public interface PointsEarnedHistoryService {

	public BaseResponseDTO save(PointsEarnedHistoryDto pointsEarnedHistoryDto) throws Exception;

}
