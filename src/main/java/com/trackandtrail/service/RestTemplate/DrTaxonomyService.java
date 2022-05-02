package com.trackandtrail.service.RestTemplate;

import java.util.List;


import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.model.ecommercev2.EcomOrders;
import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.model.ecommercev2.EcomTaxonomy;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;

public interface DrTaxonomyService {

	public List<EcomTaxonomy> getTaxonomy(String value)throws Exception;
	
	

	public List<EcomTaxonomyTerm> getTaxonomyTerm(String value) throws Exception;
	
	public List<EcomProduct> getProduct() throws Exception;
	
	public List<EcomProductVariant> getProductVariant(String type,String sort,String limit,String offset)throws Exception;
	
	public BaseResponseDTO getProductStockavailablityWithPinCode(String product_id,String pincode) throws Exception;

	public List<EcomOrders> getAllordersList(String sort,String sortOrder,String limit,String offSet,String status) throws Exception ;


	
	

	

}
