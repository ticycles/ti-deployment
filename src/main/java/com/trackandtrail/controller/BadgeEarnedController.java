package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.mail.iap.Response;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.service.BadgeEarnedService;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/badge_earned/")
public class BadgeEarnedController {
	
	@Autowired
	private BadgeEarnedService badgeEarnedService;
	
	
	@ApiOperation(value = "getAllBadgeByUserId",response =  BaseResponseDTO.class)
	@GetMapping(value = "getAllBadgeByUserId",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO>getAllBadgeByUserId(Long userId)throws Exception{
		return new ResponseEntity<BaseResponseDTO>(badgeEarnedService.getAllBadgeByUserId(userId),HttpStatus.OK);
	}
	
	

	@ApiOperation(value = "getByUserIdOrderBycreatedDateDsc",response =  BaseResponseDTO.class)
	@GetMapping(value = "getByUserIdOrderBycreatedDateDsc",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO>getByUserIdOrderBycreatedDateDsc(Long userId)throws Exception{
		return new ResponseEntity<BaseResponseDTO>(badgeEarnedService.getByUserIdOrderBycreatedDateDsc(userId),HttpStatus.OK);
	}
	

}
