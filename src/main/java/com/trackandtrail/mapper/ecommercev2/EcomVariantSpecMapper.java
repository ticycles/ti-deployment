package com.trackandtrail.mapper.ecommercev2;

import com.trackandtrail.dto.ecommercev2.EcomProductVariantSpecificationDto;
import com.trackandtrail.model.ecommercev2.EcomProductSpecification;

public class EcomVariantSpecMapper {

	public static void toDto(EcomProductSpecification s , EcomProductVariantSpecificationDto d) {
		
		if (s == null) {
			return;
		}
		
		if (s.getId() != null)
			d.setId(s.getId());
		
		if (s.getSpecKey() != null)
			d.setSpecKey(s.getSpecKey().getValue());
		
		if (s.getSpecValue() != null)
			d.setSpecValue(s.getSpecValue());
		
	}
	
}
