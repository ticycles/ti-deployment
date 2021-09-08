package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.service.BikeModelService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/bikeModel")
@CrossOrigin
public class BikeModelController {
	
	@Autowired
	BikeModelService bikeModelService;
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getByBrandId", response = BaseResponseDTO.class)
	@GetMapping(value = "/getModelByBrandId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getByBrandId(@RequestParam Long brandId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(bikeModelService.getByBrandId(brandId), HttpStatus.OK);
	}

}
