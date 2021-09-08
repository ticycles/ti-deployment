package com.trackandtrail.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.UserGroupMemberDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.model.user.UserGroupMember;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.UserFollowRepository;
import com.trackandtrail.repository.UserGroupMemberRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.UserGroupMemberService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/usrgrpmem")
public class UserGroupMemberController {

	@Autowired
	UserGroupMemberService userGroupMemberService;
	
	@Autowired
	UserGroupMemberRepository userGroupRepo;
	
	@Autowired
	UserFollowRepository userFollowRepo;
	
	@Autowired
	UserRepository userRepo;
	
	

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@PostMapping("/save")
	public ResponseEntity<BaseResponseDTO> save(@RequestBody UserGroupMemberDto userGroupMemberDTO) throws Exception {
		if (userGroupMemberDTO.getId() != null)
			throw new BadRequestException("Id cannot contain value");
		return new ResponseEntity<BaseResponseDTO>(userGroupMemberService.save(userGroupMemberDTO), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/removefrmgrp")
	public ResponseEntity<BaseResponseDTO> removeUserByuserIdAndGroupId(@RequestParam Long groupId,
			@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userGroupMemberService.removeUserByUserIdAndGroupId(groupId, userId),
				HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "groupMembers", response = BaseResponseDTO.class)
	@GetMapping(value = "/groupMembers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getByGroupId(@RequestParam Long groupId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userGroupMemberService.getByGroupId(groupId),
				HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/getMyJoinedGroups")
	public ResponseEntity<BaseResponseDTO> getMyGroups(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userGroupMemberService.findJoinedGroup(userId), HttpStatus.OK);
	}


}
