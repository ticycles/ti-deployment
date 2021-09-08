package com.trackandtrail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.repository.MyProfileRepository;
import com.trackandtrail.repository.ThisWeekChartRepository;
import com.trackandtrail.service.MyProfileService;
import com.trackandtrail.util.DateUtil;
import com.trackandtrail.util.GenericUtils;

@Service
public class MyProfileSeviceImpl implements MyProfileService{
	
	@Autowired
	MyProfileRepository myProfileRepository;
	
	@Autowired
	ThisWeekChartRepository thisWeekrepo;
	

	
	@Override
	public BaseResponseDTO getByUserId(Long userId,String startDate,String endDate) throws Exception {
		String[] thisWeek = DateUtil.getCurrentWeekStartEndDate();		
		return GenericUtils.wrapOrEmptyList(myProfileRepository.findByUserId(userId, startDate, endDate));
	}

	@Override
	public BaseResponseDTO getById(Long userId,String startDate,String endDate) throws Exception {
		String[] thisWeek = DateUtil.getCurrentWeekStartEndDate();		

		return GenericUtils.wrapOrEmptyList(thisWeekrepo.findByMyProfileUserId(userId, startDate, endDate));
	}

}
