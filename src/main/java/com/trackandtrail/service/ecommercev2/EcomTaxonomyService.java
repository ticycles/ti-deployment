package com.trackandtrail.service.ecommercev2;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomTaxonomyDto;
import com.trackandtrail.dto.ecommercev2.EcommerceTaxonomyDto;

public interface EcomTaxonomyService {
	
	
	public BaseResponseDTO getAllTaxonomy() throws Exception;

	public BaseResponseDTO getTaxonomyByCategoryId(Long categoryId) throws Exception;
	
	public BaseResponseDTO save(EcommerceTaxonomyDto ecommerceTaxonomyDto) throws Exception;
	
	public BaseResponseDTO update(EcommerceTaxonomyDto ecommerceTaxonomyDto) throws Exception;
	
	public BaseResponseDTO deleteById(Long vid) throws Exception;
	
	
//	public BaseResponseDTO deleteById(Long vid) throws Exception;




}
