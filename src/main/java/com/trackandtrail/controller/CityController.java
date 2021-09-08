package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.service.CityService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/city")
public class CityController {
	
	@Autowired
	private CityService cityService;
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getByStateId", response = BaseResponseDTO.class)
	@GetMapping(value = "/getByStateId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getByStateId(@RequestParam Long stateId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(cityService.getById(stateId), HttpStatus.OK);
	}
	

}
