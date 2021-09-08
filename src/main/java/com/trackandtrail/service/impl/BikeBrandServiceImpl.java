package com.trackandtrail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.repository.BikeBrandRepository;
import com.trackandtrail.service.BikeBrandService;
import com.trackandtrail.util.GenericUtils;

@Service
public class BikeBrandServiceImpl implements BikeBrandService {
	
	
	

	@Autowired 
	private BikeBrandRepository bikeBrandRepository;
	
	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(bikeBrandRepository.findAll());
		
	}
}
