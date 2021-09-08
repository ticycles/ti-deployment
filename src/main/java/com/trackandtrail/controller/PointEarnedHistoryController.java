package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.PointsEarnedHistoryDto;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.service.PointsEarnedHistoryService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/point_earned_history")
public class PointEarnedHistoryController {
	
	@Autowired
	PointsEarnedHistoryService pointsEarnedHistoryService;

//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "createUserPoints", response = BaseResponseDTO.class)
	@PostMapping("/createUserPoints")
	public ResponseEntity<BaseResponseDTO> createUserPoints(@RequestBody PointsEarnedHistoryDto pointsEarnedHistoryDto)
			throws CusDataIntegrityViolationException, Exception {

		return new ResponseEntity<BaseResponseDTO>(pointsEarnedHistoryService.save(pointsEarnedHistoryDto), HttpStatus.OK);
	}

}
