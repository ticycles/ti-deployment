package com.trackandtrail.dto;



import java.io.Serializable;

import com.trackandtrail.model.registerbike.BikeBrand;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class MyCycleDto implements Serializable {

	private static final long serialVersionUID = 2134576513544568999L; 
	
	
    private Long id;
	
	
    private String image;

    
    private BikeBrand bikeBrand;
	
    private UserModel user;


}
