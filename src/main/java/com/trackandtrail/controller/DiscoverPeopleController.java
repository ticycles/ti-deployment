package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.service.DiscoverPeopleService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/discover-people")
@CrossOrigin
public class DiscoverPeopleController {
	
	@Autowired
	DiscoverPeopleService discoverPeopleService;
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAlldiscoverPeopleService", response = BaseResponseDTO.class)
	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getall() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(discoverPeopleService.getAll(), HttpStatus.OK);
	}

}
