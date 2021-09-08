package com.trackandtrail.service.ecommercev2;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomReviewDto;

public interface EcomReviewService {
	
	public BaseResponseDTO save(EcomReviewDto ecomReviewDto) throws Exception;

	public BaseResponseDTO update(EcomReviewDto ecomReviewDto) throws Exception;
	

	public BaseResponseDTO getAll() throws Exception;

	public BaseResponseDTO getRewiewAndComment(Long userId, Long variantId) throws Exception;

	public BaseResponseDTO getById(Long variantId) throws Exception;


}
