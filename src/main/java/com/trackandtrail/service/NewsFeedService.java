package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ChallengeNewsFeedCmntDto;
import com.trackandtrail.dto.ContentNewsFeedCmntDto;

public interface NewsFeedService {
	
	
	public BaseResponseDTO contentByFriends(Long userId)throws Exception;
	
	public BaseResponseDTO challengesByFriends(Long userId)throws Exception;
	
	public BaseResponseDTO getSuggestedForYou(Long userId)throws Exception;

	public BaseResponseDTO save(ContentNewsFeedCmntDto cmntDto)throws Exception;

	public BaseResponseDTO save(ChallengeNewsFeedCmntDto cmntDto) throws Exception;

	public BaseResponseDTO getChallengeLikeComnt(Long id) throws Exception;

	public BaseResponseDTO getContentLikeComnt(Long id) throws Exception;


	

}
