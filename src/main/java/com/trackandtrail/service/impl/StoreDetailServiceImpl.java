package com.trackandtrail.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.StoreDetailDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.StoreDetailMapper;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail;
import com.trackandtrail.repository.StoreDetailRepository;
import com.trackandtrail.service.StoreDetailService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class StoreDetailServiceImpl implements StoreDetailService {
	
	
	@Autowired
	StoreDetailRepository storeDetailRepository;
	
	@Autowired
	ObjectMapper objectMapper;

	@Override
	public BaseResponseDTO save(StoreDetailDto storeDetailDto) throws Exception {
		storeDetailRepository.save(objectMapper.convertValue(storeDetailDto, StoreDetail.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}
	
	
	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(storeDetailRepository.findAll());
	}

	@Override
	public BaseResponseDTO getById(Long id) throws Exception {
		return GenericUtils.wrapOrNotFound(storeDetailRepository.findById(id));
	}
	
	
	@Override
	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception {
		Optional<StoreDetail> storeDetail = storeDetailRepository.findById(id);
		if (storeDetail.isPresent()) {
			if (isSoftDelete) {
				storeDetail.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				storeDetailRepository.save(storeDetail.get());
			} else {
				storeDetailRepository.deleteById(id);
			}
		} else {
			throw new ResourceNotFoundException("No Store found for given Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}
	

	@Override
	public BaseResponseDTO update(StoreDetailDto storeDetailDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<StoreDetail> storeDetail = storeDetailRepository.findById(storeDetailDto.getId());
		if (storeDetail.isPresent()) {
			StoreDetailMapper.toBadgeReward(storeDetailDto, storeDetail.get());
			storeDetailRepository.save(storeDetail.get());
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
					RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No Store found for given Id.");
		}
		return baseResponseDTO;
	}

}


