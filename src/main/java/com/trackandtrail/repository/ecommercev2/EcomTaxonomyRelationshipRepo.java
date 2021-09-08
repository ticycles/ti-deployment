package com.trackandtrail.repository.ecommercev2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.ecommercev2.EcomTaxonomyRelationship;

public interface EcomTaxonomyRelationshipRepo extends JpaRepository<EcomTaxonomyRelationship, Long> {

	List<EcomTaxonomyRelationship> findByCategoryId(Long categoryId);

	@Query(nativeQuery = true, value = "Select * from ecomm_taxonomy_relationship where taxonomy_relationship_id=:id and category_id=:categoryId")
	List<EcomTaxonomyRelationship> findByIdAndcategoryId(Long id, Long categoryId);

	

}
