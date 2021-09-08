package com.trackandtrail.dto.ecommercev2;

import com.trackandtrail.model.ecommercev2.EcomTaxonomy;

import lombok.Data;

@Data
public class EcommerceTaxonomyTermDto {

	private Long id;

	private String termName;

	private EcomTaxonomy taxonomy;

	private String imageUrl;
	
	private Long tid;

}
