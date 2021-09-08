package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.service.BikeBrandService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/bikeBrand")
@CrossOrigin
public class BikeBrandController {
	
	
	@Autowired
	private BikeBrandService bikeBrandService;
	
	
	
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAllBikeBrand", response = BaseResponseDTO.class)
	@GetMapping("/getall")
	public ResponseEntity<BaseResponseDTO> getAllBrand() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(bikeBrandService.getAll(), HttpStatus.OK);
		
	}

}
