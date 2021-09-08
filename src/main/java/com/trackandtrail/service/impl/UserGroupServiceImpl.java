package com.trackandtrail.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.DocumentManagementDTO;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.UserGroupDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.UserGroupMapper;
import com.trackandtrail.model.user.UserGroup;
import com.trackandtrail.repository.UserGroupMemberRepository;
import com.trackandtrail.repository.UserGroupRepository;
import com.trackandtrail.service.UserGroupService;
import com.trackandtrail.util.DocumentManagementUtil;
import com.trackandtrail.util.GenericSearchandPaginationUtil;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	UserGroupRepository userGroupRepository;
	
	@Autowired
	UserGroupMemberRepository userGroupMemberRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	EntityManager entityManager;

	@Override
	public BaseResponseDTO save(UserGroupDto userGroupDTO) throws Exception {
		if (userGroupDTO.getImage() != null && !userGroupDTO.getImage().isEmpty()) {
			String imgRes = DocumentManagementUtil.saveDocs(new DocumentManagementDTO("GROUP",
					userGroupDTO.getGroupName(), userGroupDTO.getImage(), userGroupDTO.getExtensionType()));
			if (imgRes.equalsIgnoreCase(RequestStatusUtil.FAILURE_RESPONSE.getErrorDescription()))
				userGroupDTO.setImage("");
			else
				userGroupDTO.setImage(imgRes);
		}
		userGroupRepository.save(objectMapper.convertValue(userGroupDTO, UserGroup.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO update(UserGroupDto userGroupDTO) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<UserGroup> userGroup = userGroupRepository.findById(userGroupDTO.getUserGroupId());
		if (userGroup.isPresent()) {
			if (userGroupDTO.getImage() != null && !userGroupDTO.getImage().isEmpty()) {
				String imgRes = DocumentManagementUtil.saveDocs(new DocumentManagementDTO("GROUP",
						userGroupDTO.getGroupName(), userGroupDTO.getImage(), userGroupDTO.getExtensionType()));
				if (imgRes.equalsIgnoreCase(RequestStatusUtil.FAILURE_RESPONSE.getErrorDescription()))
					userGroupDTO.setImage("");
				else
					userGroupDTO.setImage(imgRes);
			} else {
				userGroupDTO.setImage(userGroup.get().getImage());
			}
			UserGroup group = new UserGroup();
			UserGroupMapper.toUserGroup(userGroupDTO, group);
			userGroupRepository.save(group);
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
					RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No Group found for given Id.");
		}
		return baseResponseDTO;
	}

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(userGroupRepository.findAllGroups());
	}

	@Override
	public BaseResponseDTO getByUserGroupId(Long userGroupId) throws Exception {
		return GenericUtils.wrapOrNotFound(userGroupRepository.findById(userGroupId));
	}

	@Override
	public BaseResponseDTO deleteByUserGroupId(Long userGroupId, boolean flag) throws Exception {
		Optional<UserGroup> userGroup = userGroupRepository.findById(userGroupId);
		if (userGroup.isPresent()) {
			if (flag) {
				userGroup.get().setStatus(1);
				userGroupRepository.save(userGroup.get());
			} else {
				userGroupRepository.deleteById(userGroupId);
			}
		} else {
			throw new ResourceNotFoundException("No Group found for given Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception {
		Optional<UserGroup> userGroup = userGroupRepository.findById(id);
		BaseResponseDTO responseDTO = null;
		if (userGroup.isPresent()) {
			switch (statusId) {
			case 2:
				userGroup.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
				break;
			case 0:
				userGroup.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			case 1:
				userGroup.get().setStatus(RecordStatusUtil.RECORD_ACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.ACTIVE.getErrorDescription(),
						RequestStatusUtil.ACTIVE.getErrorCode());
				break;
			default:
				userGroup.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			}
			userGroupRepository.save(userGroup.get());
		} else {
			throw new ResourceNotFoundException("No User Group found for given Id.");
		}
		return responseDTO;
	}

	@Override
	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<UserGroup> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames = new HashMap<>();
		columnNames.put("user", "username");
		return GenericUtils.wrapOrEmptyPagination(
				util.searchByColumns(paginationDTO, columnNames, UserGroup.class, entityManager));
	}

	
	@Override
	public BaseResponseDTO getMyGroup(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(userGroupRepository.findMyGroup(userId));
	}
	
}
