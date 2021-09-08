package com.trackandtrail.mapper.ecommercev2;

import com.trackandtrail.dto.ecommercev2.EcommerceTaxonomyDto;
import com.trackandtrail.model.ecommercev2.EcomTaxonomy;

public class EcomTaxonomyMapper {
	
	
	public static void toEcomTaxonomy(EcommerceTaxonomyDto s, EcomTaxonomy d) {
		if (s == null) {
			return;
		}
		if (s.getCategory() != null)
			d.setCategory(s.getCategory());
		
		if (s.getTaxonomyName() != null)
			d.setTaxonomyName(s.getTaxonomyName());
		
		if(s.getVid()!=null)
			d.setVid(s.getVid());
		
		if(s.getTerm()!=null)
			d.setTerm(s.getTerm());
	}

}
