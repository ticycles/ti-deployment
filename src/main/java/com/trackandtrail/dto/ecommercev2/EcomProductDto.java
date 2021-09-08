package com.trackandtrail.dto.ecommercev2;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.model.common.StaticParamModel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class EcomProductDto {
	
	private Long id;

	
	private StaticParamModel category;

	private String productName;
	
	@JsonIgnore
	private List<EcomProductVariantSpecificationDto> variantSpec  = new ArrayList<>();
	
	private Long vid;
	
//	private Long id;



}
