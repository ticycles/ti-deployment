package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.MyCycleDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.repository.MyCycleRepository;
import com.trackandtrail.service.MyCycleService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(name="/myCycle")
@CrossOrigin
public class MyCycleController {
	
	@Autowired
	MyCycleService myCycleService;
	
	@Autowired
	MyCycleRepository myCycleRepository;
	
	
////	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createMyCycle", response = BaseResponseDTO.class)
	@PostMapping(value = "myCycle/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> save(@RequestBody MyCycleDto myCycleDto)
			throws CusDataIntegrityViolationException, Exception {
	if (myCycleDto.getId() != null)
			throw new BadRequestException("Id must be null");
	return new ResponseEntity<BaseResponseDTO>(myCycleService.save(myCycleDto), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateMyCycle", response = BaseResponseDTO.class)
	@PutMapping(value = "myCycle/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> update(@RequestBody MyCycleDto myCycleDto)
			throws CusDataIntegrityViolationException, Exception {
		if (myCycleDto.getId() == null)
			throw new BadRequestException("Id cannot be null");
		return new ResponseEntity<BaseResponseDTO>(myCycleService.update(myCycleDto), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllMyCycle", response = BaseResponseDTO.class)
	@GetMapping(value = "myCycle/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getall() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(myCycleService.getAll(), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping(value = "myCycle/getByUserid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getByUserId(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(myCycleService.getByUserId(userId), HttpStatus.OK);
	}

	

}
