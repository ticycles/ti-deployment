package com.trackandtrail.dto.ecommercev2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.ecommercev2.EcomProductSpecification;
import com.trackandtrail.model.ecommercev2.EcomTaxonomy;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EcomProductVariantDto extends AuditableBase{


	private Long id;

	private String variantName;

	private String variantCode;

	private EcomProduct product;

	private String price;

	private String productUrl;

	private Integer quantity;

	private Double discountPercentage;

	private Boolean discountAmount;

	private String image1;

	private String image2;

	private String image3;

	private String image4;

	private String image5;

	private String videoUrl;

	private String description;

	private Integer averageRating;
	
	private EcomTaxonomyTerm productType;

	private EcomTaxonomyTerm colorTermId;

	private EcomTaxonomyTerm sizeTermId;

	private List<EcomTaxonomyTermRelationDto> taxonomies;	

	private List<EcomTaxonomyTerm> colors;	

	private List<EcomTaxonomyTerm> size;

	private List<EcomProductVariantSpecificationDto> variantSpec = new ArrayList<EcomProductVariantSpecificationDto>();

}
