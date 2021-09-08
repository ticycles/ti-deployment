package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ChallengeUserDto;

public interface ChallengeUserService {

	public BaseResponseDTO save(ChallengeUserDto challengeUserDTO) throws Exception;

	public BaseResponseDTO getChallengeMemberCount() throws Exception;

	public BaseResponseDTO unjoin(Long challengeId, Long userId) throws Exception;

	public BaseResponseDTO getJoinInfo(Long id, Long challengeId) throws Exception;

	public BaseResponseDTO getOverAllParticipants(Long challengeId) throws Exception;

	public BaseResponseDTO getRegisteredFriends(Long userId, Long challengeId) throws Exception;
	
	public BaseResponseDTO getByUserId(Long userId) throws Exception;


}
