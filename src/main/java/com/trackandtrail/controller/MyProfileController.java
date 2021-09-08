package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.service.MyProfileService;
import com.trackandtrail.util.DateUtil;

@RestController
@RequestMapping("/myProfile")
public class MyProfileController {

	@Autowired
	MyProfileService myProfileService;

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/thisWeekAverage")
	public ResponseEntity<BaseResponseDTO> getByUserId(@RequestParam Long userId,@RequestParam String startDate,@RequestParam String endDate) throws Exception {

		return new ResponseEntity<BaseResponseDTO>(myProfileService.getByUserId(userId,startDate,endDate), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/thisWeekchart")
	public ResponseEntity<BaseResponseDTO> getById(@RequestParam Long userId,@RequestParam String startDate,@RequestParam String endDate) throws Exception {

		return new ResponseEntity<BaseResponseDTO>(myProfileService.getById(userId,startDate,endDate), HttpStatus.OK);
	}

}
