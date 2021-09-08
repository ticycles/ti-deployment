package com.trackandtrail.mapper.ecommercev2;

import com.trackandtrail.dto.ecommercev2.EcomProductVariantDto;
import com.trackandtrail.model.ecommercev2.EcomProductVariant;

public class EcomProductVariantMapper {

	
	public static void toDto(EcomProductVariant s, EcomProductVariantDto d) {
		if (s == null) {
			return;
		}
		
		if (s.getId() != null)
		d.setId(s.getId());
		
		if (s.getVariantCode() != null)
			d.setVariantCode(s.getVariantCode());
		
		if (s.getVariantName() != null)
			d.setVariantName(s.getVariantName());
		
		if (s.getProduct() != null)
			d.setProduct(s.getProduct());
		
		if (s.getPrice() != null)
			d.setPrice(s.getPrice());
		
		if (s.getProductUrl() != null)
			d.setProductUrl(s.getProductUrl());
		
		if (s.getQuantity() != null)
			d.setQuantity(s.getQuantity());
		
		if (s.getDiscountPercentage() != null)
			d.setDiscountPercentage(s.getDiscountPercentage());
		
		if (s.getDiscountAmount() != null)
			d.setDiscountAmount(s.getDiscountAmount());
		
		if (s.getDescription() != null)
			d.setDescription(s.getDescription());
		
		if (s.getAverageRating() != null)
			d.setAverageRating(s.getAverageRating());
		
//		if (s.getVariantSpec() != null)
//			d.setVariantSpec(s.getVariantSpec());
		
		
		
		if (s.getImage1() != null)
			d.setImage1(s.getImage1());
		
		if (s.getImage2() != null)
			d.setImage2(s.getImage2());
		
		if (s.getImage3() != null)
			d.setImage3(s.getImage3());
		
		if (s.getImage4() != null)
			d.setImage4(s.getImage4());
		
		if (s.getImage5() != null)
			d.setImage5(s.getImage5());
		
		
		if (s.getVideoUrl() != null)
			d.setVideoUrl(s.getVideoUrl());
		
		if (s.getProductType() != null)
			d.setProductType(s.getProductType());
		
		if (s.getColorTermId() != null)
			d.setColorTermId(s.getColorTermId());
		
		if (s.getSizeTermId() != null)
			d.setSizeTermId(s.getSizeTermId());
		
		if (s.getStatus() != null)
			d.setStatus(s.getStatus());
		
		if (s.getCreatedBy() != null)
			d.setCreatedBy(s.getCreatedBy());
		
		if (s.getCreatedDate() != null)
			d.setCreatedDate(s.getCreatedDate());
		
		if (s.getLastModifiedBy() != null)
			d.setLastModifiedBy(s.getLastModifiedBy());
		
		if (s.getLastModifiedDate() != null)
			d.setLastModifiedDate(s.getLastModifiedDate());
		
		
		
		
		
		
		
		
		
	}
	
}
