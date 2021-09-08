package com.trackandtrail.service;


import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.ManageDealerDto;

public interface ManageDealerService{


	public BaseResponseDTO save(ManageDealerDto manageDealerDto) throws Exception;

	public BaseResponseDTO update(ManageDealerDto manageDealerDto) throws Exception;

	public BaseResponseDTO getAll() throws Exception;

	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO)throws Exception;

	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception;

	public BaseResponseDTO getById(Long id) throws Exception;
	
	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception;

	public BaseResponseDTO getActiveDealers() throws Exception;

}