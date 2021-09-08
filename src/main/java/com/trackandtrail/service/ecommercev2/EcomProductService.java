package com.trackandtrail.service.ecommercev2;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.MyCycleDto;
import com.trackandtrail.dto.ecommercev2.EcomProductDto;
import com.trackandtrail.dto.ecommercev2.EcomProductVariantDto;
import com.trackandtrail.dto.ecommercev2.EcomTaxonomyDto;

public interface EcomProductService {

//	BaseResponseDTO doFilterAndPaginate(String catogery, String priceRange, String brands, String bikeTypes,
//			String roadType, String age, String gender, String gearType, String suspension, String persona, String size,
//			String color, Integer pageNo, Integer pageSize, String sortBy) throws Exception;

	public BaseResponseDTO getAllProducts(Long categoryId) throws Exception;

	public BaseResponseDTO getProductByVariantId(Long variantId) throws Exception;
	
	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception;

	public BaseResponseDTO getVariantById(Long variantId) throws Exception;
	
	public BaseResponseDTO getVariantByProductIdColorIdSizeId(Long productId, Long colorId, Long sizeId) throws Exception;
	
	BaseResponseDTO listFilterAndPaginate(	
			@RequestParam Long categoryId,
			@RequestParam Long productType,
			@RequestParam Double priceRangeMin,
			@RequestParam Double priceRangeMax,
			@RequestParam List<String> termIds, 
			@RequestParam List<String> color,
			@RequestParam List<String> size,  
			@RequestParam Integer pageNo,
			@RequestParam Integer pageSize, 
			@RequestParam String sortBy) throws Exception;

	public BaseResponseDTO getAllOurProducts() throws Exception;

	public BaseResponseDTO getSimilarProducts(Long variantId, Long categoryId) throws Exception;

	public BaseResponseDTO getAvailableProductList(Long categoryId) throws Exception;
	
	public BaseResponseDTO save(EcomProductDto ecomProductDto) throws Exception;
	
	public BaseResponseDTO createEcomProductVarients(EcomProductVariantDto ecomProductVariantDto) throws Exception;

	
	public BaseResponseDTO update(EcomProductDto ecomProductDto) throws Exception;
	


	public BaseResponseDTO deleteByVid(Long vid) throws Exception;
	
	



}
