package com.trackandtrail.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.UserGroupMemberDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.model.user.UserGroupMember;
import com.trackandtrail.repository.UserGroupMemberRepository;
import com.trackandtrail.service.UserGroupMemberService;
import com.trackandtrail.util.GenericSearchandPaginationUtil;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class UserGroupMemberServiceImpl implements UserGroupMemberService {

	@Autowired
	UserGroupMemberRepository userGroupMemberRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	EntityManager entityManager;

	@Override
	public BaseResponseDTO save(UserGroupMemberDto userGroupMemberDTO) throws Exception {
		Optional<UserGroupMember> groupMember = userGroupMemberRepository.findByUserGroupUserGroupIdAndUserId(
				userGroupMemberDTO.getUserGroup().getUserGroupId(), userGroupMemberDTO.getUser().getId());
		if (!groupMember.isPresent()) {

			userGroupMemberRepository.save(objectMapper.convertValue(userGroupMemberDTO, UserGroupMember.class));

		} else {
			throw new ResourceNotFoundException("User already added to this group");
		}

		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO removeUserByUserIdAndGroupId(Long groupId, Long userId) throws Exception {
		Optional<UserGroupMember> userGroupMember = userGroupMemberRepository
				.findByUserGroupUserGroupIdAndUserId(groupId, userId);
		if (userGroupMember.isPresent()) {
			userGroupMemberRepository.deleteById(userGroupMember.get().getId());
		} else {
			throw new ResourceNotFoundException("No Such Participant found for given Group Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<UserGroupMember> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames = new HashMap<String, String>();
		columnNames.put("user", "firstName");
		columnNames.put("user", "lastName");
		columnNames.put("userGroup", "groupName");
		columnNames.put("userGroup", "userGroupId");

		return GenericUtils.wrapOrEmptyPagination(
				util.searchBySpecificColumns(paginationDTO, columnNames, UserGroupMember.class, entityManager));
	}

	@Override
	public BaseResponseDTO findJoinedGroup(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(userGroupMemberRepository.findByUserId(userId));
	}

	@Override
	public BaseResponseDTO getByGroupId(Long groupId) throws Exception {
		return GenericUtils.wrapOrEmptyList(userGroupMemberRepository.findByUserGroupUserGroupId(groupId));
	}
	
}
