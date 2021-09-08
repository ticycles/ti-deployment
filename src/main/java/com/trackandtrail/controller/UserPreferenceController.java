package com.trackandtrail.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.UserPreferenceDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.model.userpreference.UserPreference;
import com.trackandtrail.repository.UserPreferenceRepository;
import com.trackandtrail.service.UserPreferenceService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user-preference")
@CrossOrigin
public class UserPreferenceController {

	@Autowired
	UserPreferenceRepository userPreferenceRepository;

	@Autowired
	UserPreferenceService userPreferenceService;

	//// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createUserPreference", response = BaseResponseDTO.class)
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody UserPreferenceDto userPreferenceDto)
			throws CusDataIntegrityViolationException, Exception {

		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;

		if (userPreferenceDto.getUserModel().getId() == null)
			throw new BadRequestException("Id cannot be null");

		return new ResponseEntity<BaseResponseDTO>(userPreferenceService.update(userPreferenceDto), HttpStatus.OK);
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping(value = "/getByUserid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserPreference> getByUserId(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<UserPreference>(userPreferenceService.getByUserId(userId), HttpStatus.OK);
	}

}
