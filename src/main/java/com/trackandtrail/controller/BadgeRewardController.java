package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.BadgeRewardConfDto;
import com.trackandtrail.dto.PointsConfigurationDto;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.service.BadgeRewardService;
import com.trackandtrail.service.PointsConfigurationService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/badgereward")
@CrossOrigin
public class BadgeRewardController {

	@Autowired
	BadgeRewardService badgeRewardService;
	
	@Autowired
	PointsConfigurationService pointsConfigService;
	
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "createBadge", response = BaseResponseDTO.class)
	@PostMapping("/createBadge")
	public ResponseEntity<BaseResponseDTO> createBadge(@RequestBody BadgeRewardConfDto badgeRewardConfDto)
			throws CusDataIntegrityViolationException, Exception {

		return new ResponseEntity<BaseResponseDTO>(badgeRewardService.save(badgeRewardConfDto), HttpStatus.OK);
	}
	
	
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getBadgesByModule", response = BaseResponseDTO.class)
	@GetMapping("/getBadgesByModule")
	public ResponseEntity<BaseResponseDTO> getAllBadges(String module) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(badgeRewardService.getByModule(module), HttpStatus.OK);
		
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "pointsConfiguration", response = BaseResponseDTO.class)
	@PostMapping("/pointsConfiguration")
	public ResponseEntity<BaseResponseDTO> pointsConfiguration(@RequestBody PointsConfigurationDto pointsConfigurationDto)
			throws CusDataIntegrityViolationException, Exception {

		return new ResponseEntity<BaseResponseDTO>(pointsConfigService.save(pointsConfigurationDto), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAllPointsConfiguration", response = BaseResponseDTO.class)
	@GetMapping("/getAllPointsConfiguration")
	public ResponseEntity<BaseResponseDTO> getAllPoints() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(pointsConfigService.getAllPoints(), HttpStatus.OK);
		
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getMyPoints", response = BaseResponseDTO.class)
	@GetMapping("/getMyPoints")
	public ResponseEntity<BaseResponseDTO> getMyPoint(Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(pointsConfigService.getMyPoint(userId), HttpStatus.OK);
		
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getEarnPointsConfig", response = BaseResponseDTO.class)
	@GetMapping("/getEarnPointsConfig")
	public ResponseEntity<BaseResponseDTO> getEarnPointsConfig() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(badgeRewardService.getEarnPointsConfig(), HttpStatus.OK);
		
	}
	
}
