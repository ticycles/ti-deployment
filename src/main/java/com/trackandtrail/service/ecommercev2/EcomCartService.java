package com.trackandtrail.service.ecommercev2;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomCartDto;

public interface EcomCartService {
	
	
	public BaseResponseDTO save(EcomCartDto ecomCartDto) throws Exception;

	public BaseResponseDTO getAll() throws Exception;

	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception;
	
	public BaseResponseDTO getById(Long userId) throws Exception;

}
