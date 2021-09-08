package com.trackandtrail.service.ecommercev2;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomAddressDetailsDto;

public interface EcomAddressDetailService {

	public BaseResponseDTO save(EcomAddressDetailsDto ecomAddressDetailsDto) throws Exception;

	public BaseResponseDTO update(EcomAddressDetailsDto addressDetailsDto) throws Exception;

	public BaseResponseDTO getAllAddress() throws Exception;

	public BaseResponseDTO getById(Long userId) throws Exception;

}
