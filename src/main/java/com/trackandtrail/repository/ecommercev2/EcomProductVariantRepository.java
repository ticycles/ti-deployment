package com.trackandtrail.repository.ecommercev2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.model.ecommercev2.EcomProductSpecification;

public interface EcomProductVariantRepository extends JpaRepository<EcomProductVariant, Long> {

	@Query(nativeQuery = true, value = "select v.* ,d.* ,p.* from ecomm_variant v left join ecomm_variant_spec d on d.variant_spec_id=v.variant_id "
			+ "left join ecomm_product p on p.product_id = v.product_id where p.category_id =:categoryId")

//	@Query("Select p,e from EcomProductVariant p left join EcomProduct e on e.id = v.id where e.categoryId=:categoryId")
	List<EcomProductVariant> findByEcomProductByCategoryId(Long categoryId);
	
	@Query(nativeQuery = true, value = "select v.* ,d.* ,p.* from ecomm_variant v left join ecomm_variant_spec d on d.variant_spec_id=v.variant_id "
			+ "left join ecomm_product p on p.product_id = v.product_id where p.category_id =:categoryId and v.status=1")
	List<EcomProductVariant> findAvailableEcomProductByCategoryId(Long categoryId);


	// need to check whether it is needed or not
	@Query(nativeQuery = true, value = "select v.* from ecomm_variant v left join ecomm_variant_spec d on d.variant_spec_id=v.variant_id where v.variant_id=:variantId ")
	List<EcomProductVariant> findByVariantId(Long variantId);

	List<EcomProductVariant> findByProductId(Long productId);

	@Query(nativeQuery = true, value = "select v.* from ecomm_variant v left join ecomm_variant_spec d on d.variant_spec_id=v.variant_id where v.product_id = :productId and v.color_term_id = :colorId and v.size_term_id = :sizeId  ")
	EcomProductVariant findByProductIdAndColorTermIdAndSizeTermId(Long productId, Long colorId, Long sizeId);

	@Query(nativeQuery = true, value = "select * from ecomm_variant order by created_on desc limit 10")
	List<EcomProductVariant> findByOurProducts();
	
	@Query(nativeQuery = true, value = "select v.* from ecomm_variant v left join ecomm_product p on p.product_id = v.product_id where v.variant_id !=:variantId and p.category_id =:categoryId")
	List<EcomProductVariant> findSimilarProducts(Long variantId,Long categoryId);

}
