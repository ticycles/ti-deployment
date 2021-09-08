package com.trackandtrail.service;


import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.UserGroupDto;

public interface UserGroupService{
	
	public BaseResponseDTO save(UserGroupDto userGroupDTO) throws Exception;
	
	public BaseResponseDTO update(UserGroupDto userGroupDTO) throws Exception;
	
	public BaseResponseDTO getAll() throws Exception;
	
	public BaseResponseDTO getByUserGroupId(Long id)throws Exception;

	
	public BaseResponseDTO deleteByUserGroupId(Long id,boolean flag)throws Exception;

	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception;

	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception;

	public BaseResponseDTO getMyGroup(Long userId) throws Exception;
	
}
