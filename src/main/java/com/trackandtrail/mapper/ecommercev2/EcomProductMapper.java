package com.trackandtrail.mapper.ecommercev2;

import com.trackandtrail.dto.ecommercev2.EcomProductDto;
import com.trackandtrail.model.ecommercev2.EcomProduct;

public class EcomProductMapper {
	
	public static void toEcomProduct(EcomProductDto s, EcomProduct d) {
		if (s == null) {
			return;
		}
		if (s.getCategory() != null)
			d.setCategory(s.getCategory());
		
		if (s.getProductName() != null)
			d.setProductName(s.getProductName());
		
		if(s.getVid()!=null)
			d.setVid(s.getVid());
		

		
	}

}
