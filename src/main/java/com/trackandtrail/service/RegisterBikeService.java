package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.RegisterBikeDto;

public interface RegisterBikeService {
	
	public BaseResponseDTO save(RegisterBikeDto registerBikeDto) throws Exception;

	public BaseResponseDTO update(RegisterBikeDto registerBikeDto) throws Exception;

	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception;
	
	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception;

	public BaseResponseDTO getById(Long id) throws Exception;

	public BaseResponseDTO getAll() throws Exception;
	
	public BaseResponseDTO getByUserId(Long userId)throws Exception;

	
	
	




	

	
	

}
