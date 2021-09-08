package com.trackandtrail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.repository.StateRepository;
import com.trackandtrail.service.StateService;
import com.trackandtrail.util.GenericUtils;

@Service
public class StateServiceImpl implements StateService {
	
	@Autowired 
	private StateRepository stateRepository;
	
	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(stateRepository.findAllState());
		
	}
	

}
