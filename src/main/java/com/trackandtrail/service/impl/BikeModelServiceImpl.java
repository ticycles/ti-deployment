package com.trackandtrail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.repository.BikeModelRepository;
import com.trackandtrail.service.BikeModelService;
import com.trackandtrail.util.GenericUtils;

@Service
public class BikeModelServiceImpl implements BikeModelService {
	
	@Autowired
	BikeModelRepository bikeModelRepository;
	
	@Override
	public BaseResponseDTO getByBrandId(Long brandId) throws Exception {
		return GenericUtils.wrapOrEmptyList(bikeModelRepository.findByBikeBrandId(brandId));
	}
	

}
