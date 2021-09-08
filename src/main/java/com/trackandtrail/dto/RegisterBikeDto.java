package com.trackandtrail.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trackandtrail.model.registerbike.BikeBrand;
import com.trackandtrail.model.registerbike.BikeModel;
import com.trackandtrail.model.registerbike.City;
import com.trackandtrail.model.registerbike.Pincode;
import com.trackandtrail.model.registerbike.State;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data

public class RegisterBikeDto   {	


	
	private Long id;

	private String customerName;

	private String contactNumber;

	private String email;

	private String gender;

	private String age;

	private String address;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateOfPurchase;
	
	private String frameSerialNumber;
	
	private BikeBrand bikeBrand;
	
	private BikeModel bikeModel;
    
    private String invoiceImage;
 
    private String frameSerialNoImage;

    private UserModel user;

	private State state;

	private City city;

	private String pincode;
	
	private String image;





}
