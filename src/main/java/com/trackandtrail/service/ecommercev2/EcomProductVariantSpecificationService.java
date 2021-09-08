package com.trackandtrail.service.ecommercev2;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.ecommercev2.EcomProductVariant;

public interface EcomProductVariantSpecificationService {
	
	public BaseResponseDTO getAllSpec(EcomProduct product) throws Exception;
	
}
