package com.trackandtrail.dto;


import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;


@Data
public class BikeRentalBookingDto implements Serializable {
	
	private static final long serialVersionUID = 2134576513544568999L;
	
    private Long bookingId;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fromDate;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date toDate;

	private String totalCost;
	
	private Integer quantity;
	
	private String uuid;
	
	private UserModel userModel;
	
	private BikeRentalProduct bikeRentalProduct;
	
	private StoreDetail storeDetail;
	



}

