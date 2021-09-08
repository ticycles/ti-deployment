package com.trackandtrail.repository.ecommercev2;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.dto.ecommercev2.EcomTaxonomyDto;
import com.trackandtrail.model.ecommercev2.EcomTaxonomy;

public interface EcomTaxonomyRepository extends JpaRepository<EcomTaxonomy, Long> {

//	@Query(nativeQuery = true, value = "Select t.*,e.* from ecomm_taxonomy t left join ecomm_taxonomy_term e on e.taxonomy_id=t.taxonomy_id")
	List<EcomTaxonomy> findAll();
	
	@Query(nativeQuery = true,  value =  "Select e.taxonomy_id as id, e.taxonomy_name as taxonomyName from ecomm_taxonomy_relationship t left join ecomm_taxonomy e on e.taxonomy_id = t.taxonomy_id where t.variant_id = :variantId group by t.taxonomy_id")
	List<EcomTaxonomyDto> findTaxonomyByVariantId(Long variantId);

	List<EcomTaxonomy> findByCategoryId(Long categoryId);
	
	Optional<EcomTaxonomy> findByVid(Long vid);
	
	Optional<EcomTaxonomy> findByid(Long vid);
	
	@Transactional
	 @Modifying
		@Query(value = "Delete from ecomm_taxonomy t where t.vid = :vid ", nativeQuery = true)
		int findTaxonomyByVid(Long vid);
	

}
