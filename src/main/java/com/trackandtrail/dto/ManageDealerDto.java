package com.trackandtrail.dto;

import com.trackandtrail.model.registerbike.City;
import com.trackandtrail.model.registerbike.Pincode;
import com.trackandtrail.model.registerbike.State;

import lombok.Data;

@Data
public class ManageDealerDto {
	
	private Long id;

	private String shopName;

	private String dealerName;

	private String email;

	private Long phoneNumber;

	private String addressLine1;

	private String addressLine2;

	private String landMark;

	private Boolean pickUpDrop;

	private Integer charge;
	
	private State state;

	private City city;

	private Pincode pincode;

}
