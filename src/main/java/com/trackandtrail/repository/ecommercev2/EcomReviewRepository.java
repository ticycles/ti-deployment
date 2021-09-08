package com.trackandtrail.repository.ecommercev2;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.ecommercev2.EcomReview;

@Repository
public interface EcomReviewRepository extends JpaRepository<EcomReview, Long>{
	

	@Query(nativeQuery=true,value="select * from  ecomm_review e where e.variant_id=:variantId and e.user_id =:userId")
	List<EcomReview> getReviewAndComment(Long userId, Long variantId);

	List<EcomReview> findByVariantId(Long variantId);

	Optional<EcomReview> findByUserIdAndVariantId(Long id, Long variantId);

}
