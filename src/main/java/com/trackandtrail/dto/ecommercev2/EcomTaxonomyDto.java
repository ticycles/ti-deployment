package com.trackandtrail.dto.ecommercev2;


import java.util.List;

import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class EcomTaxonomyDto {
//private Long id;
//private String taxonomyName;
//private List<EcomTaxonomyTerm> term=new ArrayList<>();
//}



public interface EcomTaxonomyDto {
 Long getId();
 String getTaxonomyName();
}