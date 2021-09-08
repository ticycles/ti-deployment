package com.trackandtrail.service.ecommercev2;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcommerceTaxonomyTermDto;

public interface EcomTaxonomyTermService {
	
	public BaseResponseDTO save(EcommerceTaxonomyTermDto ecommerceTaxonomyTermDto) throws Exception;
	
	public BaseResponseDTO update(EcommerceTaxonomyTermDto ecommerceTaxonomyTermDto) throws Exception;
	
	
	public BaseResponseDTO deleteById(Long tid) throws Exception;


}
