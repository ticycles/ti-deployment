package com.trackandtrail.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.ChallengeDto;
import com.trackandtrail.dto.ChallengeUserDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.service.ChallengeService;
import com.trackandtrail.service.ChallengeUserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {

	private static final Logger logger = LoggerFactory.getLogger(ChallengeController.class);

	@Autowired
	ChallengeService challengeService;

	@Autowired
	ChallengeUserService challengeUserService;

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createChallenge", response = BaseResponseDTO.class)
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody ChallengeDto challengeDto)
			throws CusDataIntegrityViolationException, Exception {
		if (challengeDto.getId() != null)
			throw new BadRequestException("Id must be null");
		return new ResponseEntity<BaseResponseDTO>(challengeService.save(challengeDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_MOBILE')")
	@ApiOperation(value = "updateChallenge", response = BaseResponseDTO.class)
	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> updateChallenge(@RequestBody ChallengeDto challengeDTO) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			logger.info("ChallengeController:: update :: contentModel :: ", challengeDTO.toString());
			if (challengeDTO.getId() == null)
				throw new BadRequestException("Id cannot be null");
			responseModel = challengeService.update(challengeDTO);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {

			logger.error("ChallengeController:: update :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while update the challenge");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("ChallengeController:: update :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_MOBILE')")
	@ApiOperation(value = "getall", response = BaseResponseDTO.class)
	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getall() {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = challengeService.getAll();
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("ChallengeController:: getall :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while getall");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("UserController:: getall :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_MOBILE')")
	@ApiOperation(value = "getByChallengeId", response = BaseResponseDTO.class)
	@GetMapping(value = "/getByChallengeId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getbyid(@RequestParam Long id) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = challengeService.getById(id);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("ChallengeController:: getbyid :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while getbyid");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("ChallengeController:: getbyid :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE')")
	@ApiOperation(value = "deletebyid", response = BaseResponseDTO.class)
	@DeleteMapping(value = "challenge/deletebyid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> delete(@RequestParam Long id, @RequestParam Boolean flag) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = challengeService.deleteById(id, flag);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("ChallengeController:: delete :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while delete the challenge");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("ChallengeController:: delete :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}



//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "changeStatusById", response = BaseResponseDTO.class)
	@GetMapping(value = "/changeStatusById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> changeStatusById(@RequestParam Long id, @RequestParam Integer statusId) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = challengeService.changeStatusById(id, statusId);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("ChallengeController:: changeStatusById :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while changeStatusById the challenge");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("ChallengeController:: changeStatusById :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_MOBILE')")
	@ApiOperation(value = "searchAndPaginate", response = BaseResponseDTO.class)
	@PostMapping(value = "/searchAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> searchAndPaginate(@RequestBody PaginationDTO paginationDTO) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = challengeService.searchAndPaginate(paginationDTO);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("ChallengeController:: searchAndPaginate :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while searchAndPaginate the challenge");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("ChallengeController:: searchAndPaginate :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_MOBILE')")
	@ApiOperation(value = "createChallengeUser", response = BaseResponseDTO.class)
	@PostMapping(value = "join/challenge", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> createChallengeUser(@RequestBody ChallengeUserDto challengeUserDTO) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			logger.info("ChallengeUserController:: createChallengeUser :: contentModel :: ",
					challengeUserDTO.toString());
			if (challengeUserDTO.getId() != null)
				throw new BadRequestException("Id must be null");
			responseModel = challengeUserService.save(challengeUserDTO);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {

			logger.error("ChallengeUserController:: createChallengeUser :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while create the challengeuser");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("ChallengeUserController:: createChallengeUser :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deleteChallengeUser", response = BaseResponseDTO.class)
	@DeleteMapping("/unJoinChallenge")
	public ResponseEntity<BaseResponseDTO> removeUserByuserIdAndEventId(@RequestParam Long challengeId,
			@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(challengeUserService.unjoin(challengeId, userId),
				HttpStatus.OK);
	}





//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAll", response = BaseResponseDTO.class)
	@GetMapping("rideType/getall")
	public ResponseEntity<BaseResponseDTO> getAllRideType() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(challengeService.getAllRideType(), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getByUser", response = BaseResponseDTO.class)
	@GetMapping("/getByUserId")
	public ResponseEntity<BaseResponseDTO> getByUserId(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(challengeService.getByUserId(userId), HttpStatus.OK);
	}



//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getJoinInfo", response = BaseResponseDTO.class)
	@GetMapping(value = "/getJoinInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getJoinInfo(@RequestParam Long id, @RequestParam Long challengeId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(challengeUserService.getJoinInfo(id, challengeId), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getOverAllParticipants", response = BaseResponseDTO.class)
	@GetMapping(value = "/getOverAllParticipants", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getOverAllParticipants(@RequestParam Long challengeId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(challengeUserService.getOverAllParticipants(challengeId),
				HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getRegisteredFriends", response = BaseResponseDTO.class)
	@GetMapping(value = "/getRegisteredFriends", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getRegisteredFriends(@RequestParam Long userId,
			@RequestParam Long challengeId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(challengeUserService.getRegisteredFriends(userId, challengeId),
				HttpStatus.OK);
	}

	@ApiOperation(value = "doFilerAndPaginate", response = BaseResponseDTO.class)
	@GetMapping(value = "/doFilerAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> doFilerAndPaginate(@RequestParam Long userid,@RequestParam String gender,@RequestParam String age,
			@RequestParam String privacy,@RequestParam String search,@RequestParam Integer pageNo,@RequestParam Integer pageSize) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(
				challengeService.doFilterAndPaginate(userid, gender, age, privacy, search, pageNo, pageSize),
				HttpStatus.OK);
	}

	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/getChallengeMemberCount")
	public ResponseEntity<BaseResponseDTO> getChallengeMemberCount() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(challengeUserService.getChallengeMemberCount(), HttpStatus.OK);
	}
	
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "userChallengeDetails", response = BaseResponseDTO.class)
	@GetMapping("/userChallengeDetails")
	public ResponseEntity<BaseResponseDTO> getChallengeByUserId(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(challengeUserService.getByUserId(userId), HttpStatus.OK);
	}
}
