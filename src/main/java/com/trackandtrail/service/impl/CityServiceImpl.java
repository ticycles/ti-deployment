package com.trackandtrail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.repository.CityRepository;
import com.trackandtrail.service.CityService;
import com.trackandtrail.util.GenericUtils;


@Service
public class CityServiceImpl implements CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	
	@Override
	public BaseResponseDTO getById(Long stateId) throws Exception {
		return GenericUtils.wrapOrEmptyList(cityRepository.findByStateId(stateId));
	}

}
