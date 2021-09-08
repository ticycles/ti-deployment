package com.trackandtrail.service.impl.ecommercev2;

import org.springframework.beans.factory.annotation.Autowired;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.repository.ecommercev2.EcomProductVariantSpecRepo;
import com.trackandtrail.service.ecommercev2.EcomProductVariantSpecificationService;
import com.trackandtrail.util.GenericUtils;

public class EcomProductVariantSpecificationServiceImpl implements EcomProductVariantSpecificationService {

	@Autowired
	EcomProductVariantSpecRepo evRepo;
	@Override
	public BaseResponseDTO getAllSpec(EcomProduct product) throws Exception {
		return GenericUtils.wrapOrEmptyList(evRepo.findByProduct(product));	
	}

}
