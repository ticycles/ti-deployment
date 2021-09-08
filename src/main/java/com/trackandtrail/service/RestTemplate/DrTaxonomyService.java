package com.trackandtrail.service.RestTemplate;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.model.ecommercev2.EcomOrders;
import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.model.ecommercev2.EcomTaxonomy;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;
import com.trackandtrail.model.stock.Stock;

public interface DrTaxonomyService {

//	public List<EcomTaxonomy> getTaxonomy()throws Exception;
//
//	public List<EcomTaxonomyTerm> getTaxonomyTerm() throws Exception;
//	
//	public List<EcomProduct> getProduct() throws Exception;
//	
//	public List<EcomProductVariant> getProductVariant()throws Exception;
//	
//	
	
	public BaseResponseDTO getProductStockavailablityWithPinCode(String product_id,String pincode) throws Exception;
	
//	public List<EcomOrders> getAllordersList()throws Exception;

	
	

	

}
