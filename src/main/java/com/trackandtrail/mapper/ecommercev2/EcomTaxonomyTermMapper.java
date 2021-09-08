package com.trackandtrail.mapper.ecommercev2;

import com.trackandtrail.dto.ecommercev2.EcommerceTaxonomyTermDto;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;

public class EcomTaxonomyTermMapper {

	
	public static void toEcomTaxonomyTerm(EcommerceTaxonomyTermDto s, EcomTaxonomyTerm d) {
		if (s == null) {
			return;
		}
		if (s.getImageUrl() != null)
			d.setImageUrl(s.getImageUrl());
		
		if (s.getTaxonomy() != null)
			d.setTaxonomy(s.getTaxonomy());
		
		if(s.getTid()!=null)
			d.setTid(s.getTid());
		
		if(s.getTermName()!=null)
			d.setTermName(s.getTermName());
	}
}
