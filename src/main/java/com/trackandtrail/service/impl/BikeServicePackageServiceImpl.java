package com.trackandtrail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.repository.BikeServicePackageRepository;
import com.trackandtrail.service.BikeServicePackageService;
import com.trackandtrail.util.GenericUtils;


@Service
public class BikeServicePackageServiceImpl implements BikeServicePackageService  {
	
	
	@Autowired 
	BikeServicePackageRepository bikeServicePackageRepository;
	
	
	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(bikeServicePackageRepository.findAll());
	}

}
