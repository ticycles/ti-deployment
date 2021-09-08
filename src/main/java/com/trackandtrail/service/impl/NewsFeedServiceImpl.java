package com.trackandtrail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ChallengeNewsFeedCmntDto;
import com.trackandtrail.dto.ContentNewsFeedCmntDto;
import com.trackandtrail.model.NewsFeed.ChallengeNewsFeedCmnt;
import com.trackandtrail.model.NewsFeed.ContentNewsFeedCmnt;
import com.trackandtrail.model.rideActivityComment.RideActivityComment;
import com.trackandtrail.repository.ChallengeNewsFeedCmntRepository;
import com.trackandtrail.repository.ChallengeNewsFeedRepository;
import com.trackandtrail.repository.ContentNewsFeedCmntRepository;
import com.trackandtrail.repository.ContentNewsFeedRepository;
import com.trackandtrail.repository.SuggestedForYouRepository;
//import com.trackandtrail.repository.ChallengeNewsFeedRepository;
import com.trackandtrail.service.NewsFeedService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class NewsFeedServiceImpl implements NewsFeedService {

	@Autowired
	ChallengeNewsFeedRepository challengeNewsFeedRepository;

	@Autowired
	ContentNewsFeedRepository contentNewsFeedRepository;

	@Autowired
	SuggestedForYouRepository suggestedForYouRepository;

	@Autowired
	ContentNewsFeedCmntRepository contentCmntRepo;
	
	@Autowired
	ChallengeNewsFeedCmntRepository challengeCmntRepo;


	@Autowired
	ObjectMapper objectMapper;

	@Override
	public BaseResponseDTO contentByFriends(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(contentNewsFeedRepository.contentByFriends(userId));
	}

	@Override
	public BaseResponseDTO challengesByFriends(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(challengeNewsFeedRepository.challengesByFriends(userId));
	}

	@Override
	public BaseResponseDTO getSuggestedForYou(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(suggestedForYouRepository.getSuggestedForYou(userId));
	}

	@Override
	public BaseResponseDTO save(ContentNewsFeedCmntDto cmntDto) throws Exception {
		contentCmntRepo.save(objectMapper.convertValue(cmntDto, ContentNewsFeedCmnt.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO save(ChallengeNewsFeedCmntDto cmntDto) throws Exception {
		challengeCmntRepo.save(objectMapper.convertValue(cmntDto, ChallengeNewsFeedCmnt.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO getChallengeLikeComnt(Long id) throws Exception {
		return GenericUtils.wrapOrEmptyList(challengeCmntRepo.findByChallengeNewsFeedId(id));

	}

	@Override
	public BaseResponseDTO getContentLikeComnt(Long id) throws Exception {
		return GenericUtils.wrapOrEmptyList(contentCmntRepo.findByContentNewsFeedId(id));

	}

}
