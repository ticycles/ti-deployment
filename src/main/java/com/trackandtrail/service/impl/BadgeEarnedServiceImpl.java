package com.trackandtrail.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.repository.BadgeEarnedRepository;
import com.trackandtrail.service.BadgeEarnedService;
import com.trackandtrail.util.GenericUtils;

@Service
public class BadgeEarnedServiceImpl implements BadgeEarnedService{
	
	@Autowired
	BadgeEarnedRepository badgeEarnedRepository;

	@Override
	public BaseResponseDTO getAllBadgeByUserId(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(badgeEarnedRepository.findAllBadgesByUserId(userId));
	}


	@Override
	public BaseResponseDTO getByUserIdOrderBycreatedDateDsc(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(badgeEarnedRepository.findByUserIdOrderByCreatedDateDesc(userId));
	}
}


