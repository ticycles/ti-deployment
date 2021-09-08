package com.trackandtrail.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.PointsEarnedHistoryDto;
import com.trackandtrail.model.configuration.PointsEarnedHistory;
import com.trackandtrail.repository.PointsEarnedHistoryRepository;
import com.trackandtrail.service.PointsEarnedHistoryService;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class PointsEarnedHistoryServiceImpl implements PointsEarnedHistoryService {

	@Override
	public BaseResponseDTO save(PointsEarnedHistoryDto pointsEarnedHistoryDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
//
//	@Autowired
//	PointsEarnedHistoryRepository pointsHistoryRepo;
//
//	@Autowired
//	ObjectMapper objectMapper;

//	@Override
//	@Transactional
//	public BaseResponseDTO save(PointsEarnedHistoryDto pointsEarnedHistoryDto) throws Exception {
//		BaseResponseDTO resp = null;
//		Optional<PointsEarnedHistory> pointHistory = pointsHistoryRepo.findByUserIdAndModuleSlug(
//				pointsEarnedHistoryDto.getUser().getId(), pointsEarnedHistoryDto.getModuleSlug());
//		if (pointHistory.isPresent()) {
//			pointsHistoryRepo.deleteById(pointHistory.get().getId());
//			pointsHistoryRepo.save(objectMapper.convertValue(pointsEarnedHistoryDto, PointsEarnedHistory.class));
//			resp = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
//					RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
//			return resp;
//		} else {
//
//			pointsHistoryRepo.save(objectMapper.convertValue(pointsEarnedHistoryDto, PointsEarnedHistory.class));
//			resp = new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
//					RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
//			return resp;
//
//		}
//
//	}
//
//}
