package com.trackandtrail.service.impl;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.BikeRentalProductDto;
import com.trackandtrail.dto.UserPreferenceDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.BikeRentalProductMapper;
import com.trackandtrail.mapper.UserPreferenceMapper;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;
import com.trackandtrail.model.userpreference.UserPreference;
import com.trackandtrail.repository.UserPreferenceRepository;
import com.trackandtrail.service.UserPreferenceService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService {

	@Autowired
	UserPreferenceRepository userPreferenceRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private EntityManager entityManager;

	@Override
	public BaseResponseDTO update(UserPreferenceDto userPreferenceDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		if (userPreferenceDto.getUserModel().getId() != null) {

			Optional<UserPreference> user = userPreferenceRepository
					.findByUserModelId(userPreferenceDto.getUserModel().getId());
			if (user.isPresent()) {
				UserPreferenceMapper.toUserPreference(userPreferenceDto, user.get());
				userPreferenceRepository.save(user.get());
				baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
				return baseResponseDTO;
			}

			else {
				userPreferenceRepository.save(objectMapper.convertValue(userPreferenceDto, UserPreference.class));
				baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.CREATED_RESPONSE.getErrorCode());

			}

		}

		return baseResponseDTO;
	}

	@Override
	public UserPreference getByUserId(Long userId) throws Exception {
		return userPreferenceRepository.findByUserPrefernceByUserId(userId);

	}
}
