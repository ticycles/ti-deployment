package com.trackandtrail.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.UserGroupDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.model.user.UserGroup;
import com.trackandtrail.model.user.UserGroupMember;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.UserFollowRepository;
import com.trackandtrail.repository.UserGroupMemberRepository;
import com.trackandtrail.repository.UserGroupRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.UserGroupService;
import com.trackandtrail.util.RequestStatusUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/usergroup")
public class UserGroupController {

	@Autowired
	UserGroupService userGroupService;

	@Autowired
	public UserGroupMemberRepository userMemberRepo;

	@Autowired
	public UserGroupRepository userGroupRepo;

	@Autowired
	public UserFollowRepository userFollowRepo;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@PostMapping("/save")
	public ResponseEntity<BaseResponseDTO> save(@RequestBody UserGroupDto userGroupDTO) throws Exception {
		if (userGroupDTO.getUserGroupId() != null)
			throw new BadRequestException("userGroupId cannot contain value");
		return new ResponseEntity<BaseResponseDTO>(userGroupService.save(userGroupDTO), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@PutMapping("/update")
	public ResponseEntity<BaseResponseDTO> update(@RequestBody UserGroupDto userGroupDTO) throws Exception {
		if (userGroupDTO.getUserGroupId() == null)
			throw new BadRequestException("userGroupId cannot be null");
		return new ResponseEntity<BaseResponseDTO>(userGroupService.update(userGroupDTO), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@GetMapping("/getall")
	public ResponseEntity<BaseResponseDTO> getAll() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userGroupService.getAll(), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/getby-usergroupid")
	public ResponseEntity<BaseResponseDTO> getById(@RequestParam Long userGroupId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userGroupService.getByUserGroupId(userGroupId), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@DeleteMapping("/deletebyid")
	public ResponseEntity<BaseResponseDTO> deleteByUserGroupId(@RequestParam Long userGroupId,
			@RequestParam Boolean flag) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userGroupService.deleteByUserGroupId(userGroupId, flag),
				HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "changeStatusById", response = BaseResponseDTO.class)
	@GetMapping(value = "/changeStatusById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> changeStatusById(@RequestParam Long id, @RequestParam Integer statusId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userGroupService.changeStatusById(id, statusId), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "searchAndPaginate", response = BaseResponseDTO.class)
	@PostMapping(value = "/searchAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> searchAndPaginate(@RequestBody PaginationDTO paginationDTO)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userGroupService.searchAndPaginate(paginationDTO), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/getMyGroups")
	public ResponseEntity<BaseResponseDTO> getMyGroups(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(userGroupService.getMyGroup(userId), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
//	@ApiOperation(value = "findGroupUser", response = BaseResponseDTO.class)
//	@GetMapping(value = "/findGroupUser", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<BaseResponseDTO> findAppUsers(@RequestParam Long id) throws Exception {
//		BaseResponseDTO resp = new BaseResponseDTO();
//
//		List<UserGroup> users = new ArrayList<>();
////		users = userGroupRepo.getGroupName(groupId);
//
//		users = userGroupRepo.findAll();
//
//		Optional<UserGroupMember> groupMember = userMemberRepo.findGroupMember(id);
//
//		if (groupMember.isPresent()) {
//
//			users = users.stream().map(user -> {
//				user.setUserGroupJoined(false);
//				if (groupMember.get().getUserGroup().getUserGroupId() == user.getUserGroupId()) {
//					user.setUserGroupJoined(true);
//				}
//				return user;
//			}).collect(Collectors.toList());
////	
//			resp.setResponseContent(users);
//			resp.setMsg("listed successfully");
//			resp.setErrorCode(RequestStatusUtil.SUCCESS_RESPONSE.getErrorCode());
//
//			return new ResponseEntity<BaseResponseDTO>(resp, HttpStatus.OK);
//		}
//		users = userGroupRepo.findAll();
//		users = users.stream().map(user -> {
//			user.setUserGroupJoined(false);
//			
//			return user;
//		}).collect(Collectors.toList());
//		resp.setResponseContent(users);
//		resp.setMsg("listed successfully");
//		resp.setErrorCode(RequestStatusUtil.SUCCESS_RESPONSE.getErrorCode());
//		return new ResponseEntity<BaseResponseDTO>(resp, HttpStatus.OK);
//	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "findGroupUser", response = BaseResponseDTO.class)
	@GetMapping(value = "/findGroupUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> findAppUsers(@RequestParam Long id) throws Exception {
		BaseResponseDTO resp = new BaseResponseDTO();

		List<UserGroup> users = new ArrayList<>();

		users = userGroupRepo.findAll();

		List<Long> groupMember = userMemberRepo.findGroupMember(id);

		users = users.stream().map(user -> {
			user.setUserGroupJoined(false);
			if (groupMember.contains(user.getUserGroupId())) {
				user.setUserGroupJoined(true);

			}
			return user;
		}).collect(Collectors.toList());
//	
		resp.setResponseContent(users);
		resp.setMsg("listed successfully");
		resp.setErrorCode(RequestStatusUtil.SUCCESS_RESPONSE.getErrorCode());

		return new ResponseEntity<BaseResponseDTO>(resp, HttpStatus.OK);

	}
}
