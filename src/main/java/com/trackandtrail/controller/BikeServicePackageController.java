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
import com.trackandtrail.service.BikeServiceFeatureService;
import com.trackandtrail.service.BikeServicePackageService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bike-service-package")
@CrossOrigin
public class BikeServicePackageController {
	
	@Autowired
	private BikeServicePackageService bikeServicePackageService;
	
	@Autowired
	private BikeServiceFeatureService bikeServiceFeatureService;
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllBikeServicePackage", response = BaseResponseDTO.class)
	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getall() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(bikeServicePackageService.getAll(), HttpStatus.OK);
	}

	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getbyPackageid", response = BaseResponseDTO.class)
	@GetMapping(value = "feature/getByPackageId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getByUserId(@RequestParam Long id) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(bikeServiceFeatureService.getById(id), HttpStatus.OK);
	}
}
