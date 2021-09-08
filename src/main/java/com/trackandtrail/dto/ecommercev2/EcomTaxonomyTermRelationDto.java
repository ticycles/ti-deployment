package com.trackandtrail.dto.ecommercev2;

import java.util.ArrayList;
import java.util.List;



import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EcomTaxonomyTermRelationDto {	
	private Long id;
	private String taxonomyName;
	private List<EcomTaxonomyTermDto> term=new ArrayList<>();
	
}
