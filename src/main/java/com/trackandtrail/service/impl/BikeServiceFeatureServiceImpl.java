package com.trackandtrail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.repository.BikeServiceFeatureRepository;
import com.trackandtrail.service.BikeServiceFeatureService;
import com.trackandtrail.util.GenericUtils;

@Service
public class BikeServiceFeatureServiceImpl implements BikeServiceFeatureService  {
	
	@Autowired
	private BikeServiceFeatureRepository bikeServiceFeatureRepository;
	
	
	@Override
	public BaseResponseDTO getById(Long id) throws Exception {
		return GenericUtils.wrapOrEmptyList(bikeServiceFeatureRepository.findByBikeServicePackageId(id));
	}
	}


