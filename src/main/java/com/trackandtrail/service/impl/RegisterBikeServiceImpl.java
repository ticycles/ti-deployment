package com.trackandtrail.service.impl;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.RegisterBikeDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.RegisterBikeMapper;
import com.trackandtrail.model.registerbike.RegisterBike;
import com.trackandtrail.repository.RegisterBikeRepository;
import com.trackandtrail.service.RegisterBikeService;
import com.trackandtrail.util.GenericSearchandPaginationUtil;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;


@Service
public class RegisterBikeServiceImpl implements RegisterBikeService {
	
	
	@Autowired
	RegisterBikeRepository registerBikeRepository;
	
	
	

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private EntityManager entityManager;


	@Override
	public BaseResponseDTO save(RegisterBikeDto registerBikeDto) throws Exception {

		registerBikeRepository.save(objectMapper.convertValue(registerBikeDto, RegisterBike.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}


	@Override
	public BaseResponseDTO update(RegisterBikeDto registerBikeDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<RegisterBike> registerBike = registerBikeRepository.findById(registerBikeDto.getId());
		if (registerBike.isPresent()) {
			
			RegisterBikeMapper.toRegisterBike(registerBikeDto, registerBike.get());
		
			registerBikeRepository.save(registerBike.get());
		
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
					RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No registerbike found for given registerbikeId.");
		}
		return baseResponseDTO;
	}


	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(registerBikeRepository.findAll());
	}


	@Override
	public BaseResponseDTO getById(Long registerBikeId) throws Exception {
		return GenericUtils.wrapOrNotFound(registerBikeRepository.findById(registerBikeId));
	}
	
	
	@Override
	public BaseResponseDTO getByUserId(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(registerBikeRepository.findByUserId(userId));			
		
	}
	

	@Override
	public BaseResponseDTO deleteById(Long registerBikeId, boolean isSoftDelete)throws Exception {
		Optional<RegisterBike> registerbike = registerBikeRepository.findById(registerBikeId);
		if (registerbike.isPresent()) {
			if (isSoftDelete) {
				registerbike.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				registerBikeRepository.save(registerbike.get());
			} else {
				registerBikeRepository.deleteById(registerBikeId);
			}
		} else {
			throw new ResourceNotFoundException("No registerbike found for given registerBikeId.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

	

	@Override
	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<RegisterBike> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames = new HashMap<>();
		columnNames.put("user", "username");
		columnNames.put("state", "name");
		columnNames.put("city", "name");
		columnNames.put("pincode", "name");
		columnNames.put("bikeBrand", "name");
		columnNames.put("bikeModel", "name");
		return GenericUtils.wrapOrEmptyPagination(
				util.searchByColumns(paginationDTO, columnNames, RegisterBike.class, entityManager));
	}
	
	
	
	
}



