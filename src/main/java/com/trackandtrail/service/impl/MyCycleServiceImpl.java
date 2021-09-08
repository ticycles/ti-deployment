package com.trackandtrail.service.impl;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.MyCycleDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.MyCycleMapper;
import com.trackandtrail.model.mycycle.MyCycle;
import com.trackandtrail.repository.MyCycleRepository;
import com.trackandtrail.service.MyCycleService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class MyCycleServiceImpl implements MyCycleService {
	
	
	@Autowired
	MyCycleRepository myCycleRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	private EntityManager entityManager;

	

	@Override
	public BaseResponseDTO save(MyCycleDto myCycleDto) throws Exception {
		myCycleRepository.save(objectMapper.convertValue(myCycleDto, MyCycle.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}
	
	@Override
	public BaseResponseDTO update(MyCycleDto myCycleDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<MyCycle> myCycle = myCycleRepository.findById(myCycleDto.getId());
		if (myCycle.isPresent()) {
			MyCycleMapper.toMyCycle(myCycleDto, myCycle.get());
			myCycleRepository.save(myCycle.get());
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No Cycle found for given Id.");
		}
		return baseResponseDTO;
	}
	
	
	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(myCycleRepository.findAll());
		
	}
	
	
	@Override
	public BaseResponseDTO getByUserId(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(myCycleRepository.findByUserId(userId));			
		
	}
	
	
	

	
}
