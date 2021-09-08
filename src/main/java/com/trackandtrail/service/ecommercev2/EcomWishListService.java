package com.trackandtrail.service.ecommercev2;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomWishListDto;

public interface EcomWishListService {

	public BaseResponseDTO save(EcomWishListDto ecommerceWishListDto) throws Exception;

	public BaseResponseDTO getAll() throws Exception;

	public BaseResponseDTO getByUserId(Long userId) throws Exception;

	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception;

}
