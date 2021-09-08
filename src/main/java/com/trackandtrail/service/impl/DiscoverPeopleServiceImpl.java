package com.trackandtrail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.repository.DiscoverPeopleRepository;
import com.trackandtrail.service.DiscoverPeopleService;
import com.trackandtrail.util.GenericUtils;


@Service
public class DiscoverPeopleServiceImpl implements DiscoverPeopleService {
	
	
	@Autowired
	DiscoverPeopleRepository DiscoverPeopleRepository;
	
	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(DiscoverPeopleRepository.findAll());
	}

}
