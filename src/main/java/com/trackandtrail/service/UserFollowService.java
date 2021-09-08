package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.UserFollowDto;

public interface UserFollowService {
	public BaseResponseDTO save(UserFollowDto userFollowDto) throws Exception;

	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception;

	public BaseResponseDTO deleteById(Long id) throws Exception;

	public BaseResponseDTO getById(Long id) throws Exception;

	public BaseResponseDTO getAll() throws Exception;

	public BaseResponseDTO unfollowByUserId(Long userId, Long unfollowerId) throws Exception;

	public BaseResponseDTO followersListAndPaginate(PaginationDTO paginationDTO) throws Exception;
}
