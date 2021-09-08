package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.StoreDetailDto;

public interface StoreDetailService {
	
	public BaseResponseDTO save(StoreDetailDto storeDetailDto) throws Exception;
    
    public BaseResponseDTO getAll() throws Exception;	
    
	public BaseResponseDTO getById(Long id) throws Exception;

	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception;
		
	public BaseResponseDTO update(StoreDetailDto storeDetailDto) throws Exception;


	
	



}
