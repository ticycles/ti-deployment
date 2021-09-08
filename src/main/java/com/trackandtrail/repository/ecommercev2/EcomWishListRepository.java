package com.trackandtrail.repository.ecommercev2;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.ecommercev2.EcomWishList;

public interface EcomWishListRepository extends JpaRepository<EcomWishList, Long>{

	List<EcomWishList> findByUserId(Long userId);

	@Query(nativeQuery=true,value="select * from ecomm_wish_list w  where  w.variant_id=:variantId and w.user_id=:userId")
	Optional<EcomWishList> findByWishListId(Long userId, Long variantId);

}
