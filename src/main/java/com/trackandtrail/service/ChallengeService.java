package com.trackandtrail.service;

import java.util.List;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.ChallengeDto;
import com.trackandtrail.dto.ChallengeUserDto;
import com.trackandtrail.model.challenge.Challenge;

public interface ChallengeService {

	public BaseResponseDTO save(ChallengeDto challengeDTO) throws Exception;

	public BaseResponseDTO update(ChallengeDto challengeDTO) throws Exception;

	public BaseResponseDTO getAllRideType() throws Exception;

	public BaseResponseDTO getAll() throws Exception;

	public BaseResponseDTO getById(Long id) throws Exception;

	public BaseResponseDTO getByUserId(Long userId) throws Exception;


	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception;

	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception;
	
	public BaseResponseDTO deleteById(Long id, boolean flag) throws Exception;



	public BaseResponseDTO doFilterAndPaginate(Long userid, String gender, String age, String privacy, String search,
			Integer pageNo, Integer pageSize) throws Exception;

}
