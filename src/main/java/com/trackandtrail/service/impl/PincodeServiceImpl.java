package com.trackandtrail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.repository.PincodeRepository;
import com.trackandtrail.service.PincodeService;
import com.trackandtrail.util.GenericUtils;

@Service
public class PincodeServiceImpl implements PincodeService {

	@Autowired
	private PincodeRepository pincodeRepository;
	

	@Override
	public BaseResponseDTO getById(Long cityId) throws Exception {
		return GenericUtils.wrapOrEmptyList(pincodeRepository.findByCityId(cityId));
	}
}
