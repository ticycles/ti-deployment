package com.trackandtrail.controller.ecommercev2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomAddressDetailsDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.service.ecommercev2.EcomAddressDetailService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("Ecom/address")
public class EcomAddressDetailController {
	
	@Autowired
	private EcomAddressDetailService addressDetailService;
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createAddressDetails", response = BaseResponseDTO.class)
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody EcomAddressDetailsDto ecomAddressDetailsDto)
			throws CusDataIntegrityViolationException, Exception {
		if (ecomAddressDetailsDto.getId() != null)
			throw new BadRequestException("Id must be null");
		return new ResponseEntity<BaseResponseDTO>(addressDetailService.save(ecomAddressDetailsDto), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateAddressDetails", response = BaseResponseDTO.class)
	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> update(@RequestBody EcomAddressDetailsDto addressDetailsDto)
			throws CusDataIntegrityViolationException, Exception {
		if (addressDetailsDto.getId() == null)
			throw new BadRequestException("Id cannot be null");
		return new ResponseEntity<BaseResponseDTO>(addressDetailService.update(addressDetailsDto), HttpStatus.OK);
	}
	


	
	

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
		@ApiOperation(value = "getAllAddressDetails", response = BaseResponseDTO.class)
		@GetMapping(value = "/getAllAddressDetails", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<BaseResponseDTO> getAllCycle() throws Exception {
			return new ResponseEntity<BaseResponseDTO>(addressDetailService.getAllAddress(), HttpStatus.OK);
		}
		
		
//		@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
		@ApiOperation(value = "getByUserId", response = BaseResponseDTO.class)
		@GetMapping(value = "/getByUserId", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<BaseResponseDTO> getById(@RequestParam Long userId) throws Exception {
			return new ResponseEntity<BaseResponseDTO>(addressDetailService.getById(userId), HttpStatus.OK);
		}

}
