package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.WishListDto;

public interface WishListService {
	
	
	public BaseResponseDTO save(WishListDto wishListDto) throws Exception;

	
	public BaseResponseDTO getAll() throws Exception;
	
	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception;



}
