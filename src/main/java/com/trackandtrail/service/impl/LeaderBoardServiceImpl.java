package com.trackandtrail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.repository.BikeBrandRepository;
import com.trackandtrail.repository.LeaderBoardFriendsRepository;
import com.trackandtrail.repository.LeaderBoardRepository;
import com.trackandtrail.service.LeaderBoardService;
import com.trackandtrail.util.GenericUtils;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService{
	
	
	
	@Autowired 
	private LeaderBoardRepository leaderBoardRepository;
	@Autowired
	LeaderBoardFriendsRepository leaderBoardFriendsRepository;
	
	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(leaderBoardRepository.findAll());
		
	}


	@Override
	public BaseResponseDTO getByUserId(Long followingUserId) throws Exception {
		return GenericUtils.wrapOrEmptyList(leaderBoardFriendsRepository.findByUserId(followingUserId));
	}
}
