package com.trackandtrail.dto;

import java.io.Serializable;

import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class RideActivityDto implements Serializable {

	private static final long serialVersionUID = 2134576513544568999L;

	private Long rideId;

	private String name;

	// optional field
	private String image;

	private String rate;

	private String privacy;

	private String description;

	private String averageTime;

	private Float averageSpeed;

	private Float averageDistance;

	private String sourceLat;

	private String sourceLong;

	private String destinationLat;

	private String destinationLong;

	private UserModel user;



}
