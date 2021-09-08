package com.trackandtrail.dto;

import java.io.Serializable;

import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class WishListDto implements Serializable {
	
	private static final long serialVersionUID = 2134576513544568999L; 

	
	    private Long wishListId;
		
		private BikeRentalProduct bikeRentalProduct;
		
		private UserModel user;

}
