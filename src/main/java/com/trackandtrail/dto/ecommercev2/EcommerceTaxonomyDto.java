package com.trackandtrail.dto.ecommercev2;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.model.common.StaticParamModel;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EcommerceTaxonomyDto {

	private Long id;

	private String taxonomyName;

	private StaticParamModel category;

//	@JsonIgnore
//	private List<EcomTaxonomyTerm> term = new ArrayList<>();
	
	private List<EcomTaxonomyTerm> term;
	
	
	private Long vid;

}
