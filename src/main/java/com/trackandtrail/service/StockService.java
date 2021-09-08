package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.StockDto;

public interface StockService {
	
	public BaseResponseDTO save(StockDto stockDto) throws Exception;

	public BaseResponseDTO update(StockDto stockDto) throws Exception;

	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception;

	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception;

	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception;

	public BaseResponseDTO getById(Long id) throws Exception;

	public BaseResponseDTO getAll() throws Exception;
	
	public BaseResponseDTO getAllStock() throws Exception;


}
