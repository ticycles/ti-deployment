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
import com.trackandtrail.service.PincodeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pincode")
public class PincodeController {
	
	
	@Autowired
	private PincodeService pincodeService;
	
	
	

	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getByCityId", response = BaseResponseDTO.class)
	@GetMapping(value = "/getByCityId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getByCityId(@RequestParam Long cityId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(pincodeService.getById(cityId), HttpStatus.OK);
	}
	

}
