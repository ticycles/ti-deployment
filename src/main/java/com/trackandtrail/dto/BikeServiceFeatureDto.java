package com.trackandtrail.dto;


import java.io.Serializable;

import com.trackandtrail.model.bikeservicepackage.BikeServicePackage;

import lombok.Data;

@Data
public class BikeServiceFeatureDto implements Serializable {
	
	private static final long serialVersionUID = 2134576513544568999L; 
	
	
	 private Long id;
	
	 private String detail;
	
	
	 private BikeServicePackage bikeServicePackage;
	


}
