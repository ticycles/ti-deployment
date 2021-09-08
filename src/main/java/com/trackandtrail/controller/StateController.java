package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.service.StateService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/state")
public class StateController {
	
	
	
	@Autowired
	StateService stateService;
	
	
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAllRegisterBike", response = BaseResponseDTO.class)
	@GetMapping("state/getall")
	public ResponseEntity<BaseResponseDTO> getAll() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(stateService.getAll(), HttpStatus.OK);
	}

}
