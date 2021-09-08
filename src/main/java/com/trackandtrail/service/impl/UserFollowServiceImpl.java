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
import com.trackandtrail.dto.UserFollowDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.model.user.UserFollow;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.UserFollowRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.AlertService;
import com.trackandtrail.service.UserFollowService;
import com.trackandtrail.util.GenericSearchandPaginationUtil;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class UserFollowServiceImpl implements UserFollowService {

	@Autowired
	UserFollowRepository userFollowRepository;

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	private AlertService alertService;
	

	@Autowired
	private UserRepository userRepository;

	
	@Autowired
	EntityManager entityManager;

	@Override
	public BaseResponseDTO save(UserFollowDto userFollowDto) throws Exception {
		UserFollow follow = objectMapper.convertValue(userFollowDto, UserFollow.class);
		userFollowRepository.save(follow);
		
		  Optional<UserModel> follower=userRepository.findById(userFollowDto.getUser().getId());
	        Optional<UserModel> following=userRepository.findById(userFollowDto.getFollowing().getId());
	        
	        if(follower.isPresent() && following.isPresent()) 
		        alertService.sendFollowAlert(follower.get(),following.get());

	        
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(userFollowRepository.findAll());
	}

	@Override
	public BaseResponseDTO getById(Long id) throws Exception {
		return GenericUtils.wrapOrNotFound(userFollowRepository.findById(id));
	}

	@Override
	public BaseResponseDTO deleteById(Long id) throws Exception {
		Optional<UserFollow> badgeReward = userFollowRepository.findById(id);
		if (badgeReward.isPresent()) {
			userFollowRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("No User Follow found for given Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}
	
	@Override
	public BaseResponseDTO unfollowByUserId(Long userId,Long unfollowerId) throws Exception {
		UserFollow badgeReward = userFollowRepository.findByUserIdAndFollowingId(userId, unfollowerId);
		if (badgeReward!=null) {
			userFollowRepository.deleteById(badgeReward.getId());
		} else {
			throw new ResourceNotFoundException("No Such Follower found for given Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

	
	@Override
	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<UserFollow> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames= new HashMap<String, String>();
		columnNames.put("user", "firstName");
		columnNames.put("user", "lastName");
		columnNames.put("following", "firstName");
		columnNames.put("following", "lastName");
		
		return GenericUtils.wrapOrEmptyPagination(
				util.searchByColumns(paginationDTO, columnNames, UserFollow.class, entityManager));
	}
	
	@Override
	public BaseResponseDTO followersListAndPaginate(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<UserFollow> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames= new HashMap<String, String>();
		columnNames.put("user", "id");
		columnNames.put("following", "id");
		
		return GenericUtils.wrapOrEmptyPagination(
				util.searchBySpecificColumns(paginationDTO, columnNames, UserFollow.class, entityManager));
	}
	
}
