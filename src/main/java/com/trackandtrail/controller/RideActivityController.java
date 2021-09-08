package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.RideActivityCommentsDto;
import com.trackandtrail.dto.RideActivityDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.repository.RideActivityCommentsRepository;
import com.trackandtrail.service.RideActivityCommentService;
import com.trackandtrail.service.RideActivityService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ride-activity")
@CrossOrigin
public class RideActivityController {

	@Autowired
	RideActivityService rideActivityService;

	@Autowired
	RideActivityCommentsRepository rideActivityCommentsRepository;

	@Autowired
	RideActivityCommentService rideActivityCommentService;

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@PostMapping("/save")
	public ResponseEntity<BaseResponseDTO> save(@RequestBody RideActivityDto rideActivityDTO) throws Exception {
		if (rideActivityDTO.getRideId() != null)
			throw new BadRequestException("Id cannot contain value");
		return new ResponseEntity<BaseResponseDTO>(rideActivityService.save(rideActivityDTO), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/ride-history")
	public ResponseEntity<BaseResponseDTO> getByUserId(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(rideActivityService.getByUserId(userId), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/ride-details")
	public ResponseEntity<BaseResponseDTO> getByRideId(@RequestParam Long rideId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(rideActivityService.getByRideId(rideId), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
//	@GetMapping("/ridehistoryofcuruser")
//	public ResponseEntity<BaseResponseDTO> getByCurrentUserId() throws Exception {
//		return new ResponseEntity<BaseResponseDTO>(rideActivityService.getByCurrentUserId(), HttpStatus.OK);
//	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/newsFeedRide")
	public ResponseEntity<BaseResponseDTO> getByRideActivityUserId(@RequestParam Long followingUserId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(rideActivityService.getByRideActivityUserId(followingUserId),
				HttpStatus.OK);
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createRideActivityComment", response = BaseResponseDTO.class)
	@PostMapping(value = "/like", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody RideActivityCommentsDto rideActivityCommentsDto)
			throws CusDataIntegrityViolationException, Exception {

		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;

		if (rideActivityCommentsDto.getUser().getId() == null)

			throw new BadRequestException("Id cannot be null");

		return new ResponseEntity<BaseResponseDTO>(rideActivityCommentService.update(rideActivityCommentsDto),
				HttpStatus.OK);

	}

//		@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createRideActivityComment", response = BaseResponseDTO.class)
	@PostMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> createComment(@RequestBody RideActivityCommentsDto rideActivityCommentsDto)
			throws CusDataIntegrityViolationException, Exception {

		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;

		if (rideActivityCommentsDto.getUser().getId() == null)

			throw new BadRequestException("Id cannot be null");

		return new ResponseEntity<BaseResponseDTO>(rideActivityCommentService.updateComment(rideActivityCommentsDto),
				HttpStatus.OK);

	}

//		@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/getCommentInfoByUserIdandRideActivityId")
	@ApiOperation(value = "getCommentInfoByUserIdandRideActivityId", response = BaseResponseDTO.class)
	public ResponseEntity<BaseResponseDTO> getByRideActivityRideIdAndUserId(@RequestParam Long id, Long rideId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(
				rideActivityCommentService.findByRideActivityRideIdAndUserId(id, rideId), HttpStatus.OK);
	}
	//

//		@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getallCommentsByRideId", response = BaseResponseDTO.class)
	@GetMapping("/getallCommentsByRideId")
	public ResponseEntity<BaseResponseDTO> getallCommentsByRideId(@RequestParam Long id) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(rideActivityCommentService.getallCommentsByRideId(id),
				HttpStatus.OK);
	}

//		@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAllByIsLike", response = BaseResponseDTO.class)
	@GetMapping("/getAllByIsLike")
	public ResponseEntity<BaseResponseDTO> getAllByIsLikeByRideId(@RequestParam Long rideId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(rideActivityCommentService.getAllByIsLike(rideId), HttpStatus.OK);

	}

//		@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllByComment", response = BaseResponseDTO.class)
	@GetMapping(value = "/getAllByComment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getAllByCommentByRideId(@RequestParam Long rideId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(rideActivityCommentService.getAllByComment(rideId), HttpStatus.OK);
	}

}
