package com.trackandtrail.service;

import java.util.Optional;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ChallengeUserDto;
import com.trackandtrail.dto.EventUserDto;
import com.trackandtrail.model.event.EventUser;

public interface EventUserService {

	public BaseResponseDTO save(EventUserDto eventUserDto) throws Exception;


	public BaseResponseDTO getJoinInfo(Long id, Long eventId) throws Exception;

	public BaseResponseDTO getOverAllParticipants(Long eventId) throws Exception;

	public BaseResponseDTO getRegisteredFriends(Long userId, Long eventId) throws Exception;

	public BaseResponseDTO unjoin(Long eventId, Long userId) throws Exception;


}
