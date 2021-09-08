package com.trackandtrail.repository.ecommercev2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.ecommercev2.EcomProductSpecification;

public interface EcomProductVariantSpecRepo extends JpaRepository<EcomProductSpecification, Long>{

	List<EcomProductSpecification> findByProduct(EcomProduct product);

	
	
	

}
