package com.trackandtrail.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.UserFollowDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.model.user.UserFollow;
import com.trackandtrail.repository.UserFollowRepository;
import com.trackandtrail.service.UserFollowService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/userfollow")
public class UserFollowController {

	@Autowired
	UserFollowService userFollowService;
	


	@Autowired
	UserFollowRepository userFollowRepository;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@PostMapping("/follow")
	public ResponseEntity<BaseResponseDTO> save(@RequestBody UserFollowDto userFollowDto) throws Exception {
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;

		if (userFollowDto.getId() != null)
			throw new BadRequestException("Id cannot contain value");

		Optional<UserFollow> userFollow = userFollowRepository
				.findByUserAndFollowingId(userFollowDto.getUser().getId(), userFollowDto.getFollowing().getId());
		
		if(userFollow.isPresent()) {
			responseModel.setMsg("User already followed");
			httpStatus = HttpStatus.CONFLICT;
			return new ResponseEntity<>(responseModel, httpStatus);
		}

		return new ResponseEntity<BaseResponseDTO>(userFollowService.save(userFollowDto), HttpStatus.OK);
		
		
	}

	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@GetMapping("/getall")
	public ResponseEntity<BaseResponseDTO> getAll() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userFollowService.getAll(), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/getbyid")
	public ResponseEntity<BaseResponseDTO> getById(@RequestParam Long id) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userFollowService.getById(id), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/unfollow")
	public ResponseEntity<BaseResponseDTO> unfollowByuserIdAndFollwerId(@RequestParam Long userId,
			@RequestParam Long unfollowerId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userFollowService.unfollowByUserId(userId, unfollowerId),
				HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@DeleteMapping("/deletebyid")
	public ResponseEntity<BaseResponseDTO> deleteById(@RequestParam Long id) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userFollowService.deleteById(id), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "searchAndPaginate", response = BaseResponseDTO.class)
	@PostMapping(value = "/searchAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> searchAndPaginate(@RequestBody PaginationDTO paginationDTO)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userFollowService.searchAndPaginate(paginationDTO), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "followersListAndPaginate", response = BaseResponseDTO.class)
	@PostMapping(value = "/followersList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> followersListAndPaginate(@RequestBody PaginationDTO paginationDTO)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userFollowService.followersListAndPaginate(paginationDTO),
				HttpStatus.OK);
	}

}
