package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.BikeServiceDto;

public interface BikeServiceService {
	
	
	public BaseResponseDTO save(BikeServiceDto bikeServiceDto) throws Exception;

	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception;
	
	public BaseResponseDTO getById(Long id) throws Exception;
	
	public BaseResponseDTO getByUserId(Long userId)throws Exception;

	public BaseResponseDTO getAll() throws Exception;

}
