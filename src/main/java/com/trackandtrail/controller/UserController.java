package com.trackandtrail.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.LoginDto;
import com.trackandtrail.dto.StockDto;
import com.trackandtrail.dto.UserDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.UserFollowRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.UserService;
import com.trackandtrail.util.RequestStatusUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController {

//	https://www.javaguides.net/2019/10/spring-boot-crud-operations-example-with-jpa-hibernate.html

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	public static final String SALT = "C-Y-C";

	
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public UserFollowRepository userFollowRepo;
	
	@Autowired
	public UserRepository userRepo;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createUser", response = BaseResponseDTO.class)
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> createUser(@RequestBody UserDto userDTO) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			logger.info("UserController:: createUser :: contentModel :: ", userDTO.toString());
			if (userDTO.getId() != null)
				throw new BadRequestException("Id must be null");
			
			
			Optional<UserModel> user = userRepo
					.findByEmail(userDTO.getEmail());
//
			if (user.isPresent()) {
				responseModel.setMsg("Email Already Exist.");
				responseModel.setErrorCode(RequestStatusUtil.DUPLICATE_ENTRY.getErrorCode());
				httpStatus = HttpStatus.CONFLICT;
				return new ResponseEntity<>(responseModel, httpStatus);
				
			}	
			responseModel = userService.save(userDTO);
			httpStatus = HttpStatus.OK;
		} catch (DataIntegrityViolationException de) {
			responseModel.setMsg(de.getMostSpecificCause().toString()
					.replace("java.sql.SQLIntegrityConstraintViolationException: ", ""));
			httpStatus = HttpStatus.CONFLICT;
		} catch (Exception ex) {
			responseModel.setMsg("Exception occurred while creating the user");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error("UserController:: createUser :: Exception :: " + ex);

		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("UserController:: createUser :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateUser", response = BaseResponseDTO.class)
	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> updateUser(@RequestBody UserDto userDto)
			throws CusDataIntegrityViolationException, Exception {
		if (userDto.getId() == null)
			throw new BadRequestException("Id cannot be null");
		return new ResponseEntity<BaseResponseDTO>(userService.update(userDto), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllUser", response = BaseResponseDTO.class)
	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getall() {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = userService.getAll();
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("UserController:: update :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while update the user");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("UserController:: update :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getbyid", response = BaseResponseDTO.class)
	@GetMapping(value = "/getbyid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getById(@RequestParam Long id) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = userService.getById(id);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("UserController:: getById :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while getById the user");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("UserController:: getById :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deletebyid", response = BaseResponseDTO.class)
	@DeleteMapping(value = "/deletebyid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> deletebyid(@RequestParam Long id, @RequestParam Boolean flag) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = userService.deleteById(id, flag);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("UserController:: deletebyid :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while deletebyid the user");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("UserController:: deletebyid :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "changeStatusById", response = BaseResponseDTO.class)
	@GetMapping(value = "/changeStatusById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> changeStatusById(@RequestParam Long id, @RequestParam Integer statusId) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = userService.changeStatusById(id, statusId);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("UserController:: changeStatusById :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while changeStatusById the user");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("UserController:: changeStatusById :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "searchAndPaginate", response = BaseResponseDTO.class)
	@PostMapping(value = "/searchAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> searchAndPaginate(@RequestBody PaginationDTO paginationDTO) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = userService.searchAndPaginate(paginationDTO);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("UserController:: searchAndPaginate :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while searchAndPaginate the user");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("UserController:: searchAndPaginate :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "getListofNonMobileUsersWithPaginate", response = BaseResponseDTO.class)
	@PostMapping(value = "/getnonmobileusers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getListofNonMobileUsersWithPaginate(
			@RequestBody PaginationDTO paginationDTO) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = userService.getNonMobileUserListWithPaginate(paginationDTO);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("UserController:: getListofNonMobileUsersWithPaginate :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while getListofNonMobileUsersWithPaginate the user");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("UserController:: getListofNonMobileUsersWithPaginate :: responseEntity :: ",
				responseModel.toString());
		return responseEntity;
	}

	@ApiOperation(value = "passwordReset", response = BaseResponseDTO.class)
	@PostMapping(value = "/passwordReset", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> passwordReset(@RequestBody LoginDto loginDto) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = userService.resetPassword(loginDto);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("UserController:: passwordReset :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while passwordReset the user");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("UserController:: passwordReset :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

	@ApiOperation(value = "forgetPassword", response = BaseResponseDTO.class)
	@PostMapping(value = "/forgetPassword", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> forgetPassword(@RequestParam String emailId) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = userService.forgetPassword(emailId);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("UserController:: forgetPassword :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while forgetPassword the user");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("UserController:: forgetPassword :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAllMobileUser", response = BaseResponseDTO.class)
	@GetMapping("/getAllMobileUser")
	public ResponseEntity<BaseResponseDTO> getAllMobileUser() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userService.getAllMobileUser(), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAllAdminUser", response = BaseResponseDTO.class)
	@GetMapping("/getAllAdminUser")
	public ResponseEntity<BaseResponseDTO> getAllAdminUser() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userService.getAllAdminUser(), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAllRole", response = BaseResponseDTO.class)
	@GetMapping("/getAllRole")
	public ResponseEntity<BaseResponseDTO> getAllRole() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userService.getAllRole(), HttpStatus.OK);
	}
	
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "findAppUsers", response = BaseResponseDTO.class)
	@GetMapping(value = "/findAppUsers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> findAppUsers(@RequestParam(required = false) Long id,@RequestParam(required = false) String search) throws Exception {
		
		List<Long> userFollowingList = userFollowRepo.getFollowingIds(id);
			List<UserModel> users = new ArrayList<>();
			users = userRepo.getFriends(search);

			if (StringUtils.hasLength(search)) {
				users = userRepo.getFriends(search);
				
			} else {
				users = userRepo.findAll();
			}
			users = users.stream().map(user -> {
				user.setFollowing(false);
				if (id != user.getId() && userFollowingList.contains(Long.valueOf(user.getId()))) {
					user.setFollowing(true);
				}
				return user;
			}).collect(Collectors.toList());
//		
			BaseResponseDTO resp = new BaseResponseDTO();
			resp.setResponseContent(users);
			resp.setMsg("listed successfully");
		
		return new ResponseEntity<BaseResponseDTO>(resp, HttpStatus.OK);
	}
	
}
