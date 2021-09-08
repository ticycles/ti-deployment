package com.trackandtrail.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.PointsConfigurationDto;
import com.trackandtrail.dto.request.PointConfigResponseDto;
import com.trackandtrail.model.configuration.PointsConfiguration;
import com.trackandtrail.repository.MyEarnPointsRepository;
import com.trackandtrail.repository.PointsConfigurationRepository;
import com.trackandtrail.service.PointsConfigurationService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RequestStatusUtil;
import com.trackandtrail.view.MyEarnPoints;

@Service
public class PointsConfigurationServiceImpl implements PointsConfigurationService {

	@Autowired
	PointsConfigurationRepository pointsConfig;
	
	@Autowired
	MyEarnPointsRepository myEarnPointsRepository;

	@Override
	@Transactional
	public BaseResponseDTO save(PointsConfigurationDto pointsConfigurationDto) throws Exception {


		BaseResponseDTO resp = new BaseResponseDTO();

		List<PointsConfiguration> point = new ArrayList<PointsConfiguration>();
		pointsConfigurationDto.getPointReward().forEach(pointConfig -> {
			PointsConfiguration pointsConfiguration = new PointsConfiguration();

			pointsConfiguration.setNickName(pointConfig.getNickName());
			pointsConfiguration.setTitle(pointConfig.getTitle());
			pointsConfiguration.setRangeFrom(pointConfig.getRangeFrom());
			pointsConfiguration.setRangeTo(pointConfig.getRangeTo());
			point.add(pointsConfiguration);
		});

		
		pointsConfig.deleteAll();
		pointsConfig.saveAll(point);
		resp.setMsg("Points created successfully");
		resp.setErrorCode(200);
		return resp;
	}
	
	
	
	@Override
	public BaseResponseDTO getAllPoints() throws Exception {
		return GenericUtils.wrapOrEmptyList(pointsConfig.findAll());

	}
	
	
	@Override
	public BaseResponseDTO getMyPoint(Long userId) throws Exception {
		BaseResponseDTO resp =new BaseResponseDTO();
		Optional<MyEarnPoints> points = myEarnPointsRepository.getSumEarnPointByUserId(userId);
		if(points.isPresent()) {

		
		Optional<PointsConfiguration> pointConfig = pointsConfig.getPointConfigByEarnPoints(points.get().getEarnPoint());
		if(pointConfig.isPresent()) {
			
			PointConfigResponseDto pointResponseDto = new PointConfigResponseDto();
			
			pointResponseDto.setNickName(pointConfig.get().getNickName());
			pointResponseDto.setRangeFrom(pointConfig.get().getRangeFrom());
			pointResponseDto.setRangeTo(pointConfig.get().getRangeTo());
			pointResponseDto.setTitle(pointConfig.get().getTitle());
			pointResponseDto.setTotalEarnedPoints(points.get().getEarnPoint());
			resp.setResponseContent(pointResponseDto);

			resp.setResponseContent(pointResponseDto);
			resp.setErrorCode(RequestStatusUtil.SUCCESS_RESPONSE.getErrorCode());
			resp.setMsg(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription());
			
			return resp;
			
			
		}
		
		
		}
		resp.setResponseContent("Total earned points " + points.get().getEarnPoint());
		resp.setErrorCode(RequestStatusUtil.SUCCESS_RESPONSE.getErrorCode());
		resp.setMsg(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription());
		return resp;

	}

	

}
