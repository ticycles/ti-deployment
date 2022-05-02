package com.trackandtrail.repository.ecommercev2;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.dto.ecommercev2.EcomTaxonomyTermDto;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;

public interface EcomTaxonomyTermRepository extends JpaRepository<EcomTaxonomyTerm, Long>{

	@Query(nativeQuery = true, value = "Select tt.taxonomy_id as taxonomyId, tt.term_id as id, tt.term_name as termName from ecomm_taxonomy_relationship r left join ecomm_taxonomy_term tt on tt.term_id = r.taxonomy_term_id left join ecomm_taxonomy e on e.taxonomy_id = r.taxonomy_id where r.variant_id = :variantId group by r.taxonomy_id")
	List<EcomTaxonomyTermDto> findTaxonomyTermByVariantId(Long variantId);
	
	
	Optional<EcomTaxonomyTerm> findByTid(Long tid);
	
	

	@Query(value = "select * from ecomm_taxonomy_term u where u.tid = :tid", nativeQuery = true)
	Optional<EcomTaxonomyTerm> findByEcomTaxonmyTid(Long tid);
	
//	@Query(nativeQuery=true,value="delete from ecomm_taxonomy_term b where b.tid=:tid")
//	EcomTaxonomyTerm findByTaxonmomyTermByTid(Long tid);
	
	@Transactional
	@Modifying
	@Query(nativeQuery=true,value="delete from ecomm_taxonomy_term b where b.tid=:tid")
	int findByTaxonmomyTermByTid(Long tid);


	
}
