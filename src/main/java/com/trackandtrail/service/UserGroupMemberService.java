package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.UserGroupMemberDto;

public interface UserGroupMemberService {

	public BaseResponseDTO save(UserGroupMemberDto userGroupMemberDTO) throws Exception;

	public BaseResponseDTO removeUserByUserIdAndGroupId(Long groupId, Long userId) throws Exception;

	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception;

	public BaseResponseDTO findJoinedGroup(Long userId) throws Exception;

	public BaseResponseDTO getByGroupId(Long groupId) throws Exception;

}
