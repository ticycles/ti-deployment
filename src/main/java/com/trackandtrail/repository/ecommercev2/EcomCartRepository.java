package com.trackandtrail.repository.ecommercev2;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.ecommercev2.EcomCart;

public interface EcomCartRepository extends JpaRepository<EcomCart, Long>{
	
	List<EcomCart> findByUserId(Long id);
	
	Long deleteByUserId(Long userId);

	@Query(nativeQuery=true,value="select * from ecomm_add_to_cart c  where  c.variant_id=:variantId and c.user_id=:userId")
	Optional<EcomCart> findByCard(Long userId, Long variantId);


}
