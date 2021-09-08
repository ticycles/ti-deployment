package com.trackandtrail.repository.ecommercev2;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.event.EventUser;

public interface EcomProductRepository extends JpaRepository<EcomProduct, Long>{
	
	
	Optional<EcomProduct>  findByVid(Long vid);
	

	
	@Transactional
	 @Modifying
	 @Query(nativeQuery=true,value="delete from ecomm_product p where p.vid=:vid")
	int findByEcomProductByVid(Long vid);
	
	
//	EcomProduct findByid(Long id);
}
