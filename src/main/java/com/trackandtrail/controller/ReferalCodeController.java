package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ReferAndEarnDto;
import com.trackandtrail.dto.ReferalCodeDto;
import com.trackandtrail.dto.UserPreferenceDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.repository.ReferalCodeRepository;
import com.trackandtrail.repository.UserPreferenceRepository;
import com.trackandtrail.service.ReferalCodeService;
import com.trackandtrail.service.UserPreferenceService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/referalCode")
public class ReferalCodeController {

	@Autowired
	ReferalCodeRepository referalCodeRepository;

	@Autowired
	ReferalCodeService referalCodeService;

	//// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createReferalCode", response = BaseResponseDTO.class)
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody ReferalCodeDto referalCodeDto)
			throws CusDataIntegrityViolationException, Exception {

		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;

		if (referalCodeDto.getUser().getId() == null)
			throw new BadRequestException("Id must be null");

		return new ResponseEntity<BaseResponseDTO>(referalCodeService.save(referalCodeDto), HttpStatus.OK);
	}

	//// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "referAndEarn", response = BaseResponseDTO.class)
	@PostMapping(value = "referAndEarn/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody ReferAndEarnDto referalAndEarnDto)
			throws CusDataIntegrityViolationException, Exception {

		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;

		if (referalAndEarnDto.getUser().getId() == null)
			throw new BadRequestException("Id must be null");

		return new ResponseEntity<BaseResponseDTO>(referalCodeService.create(referalAndEarnDto), HttpStatus.OK);
	}

}
