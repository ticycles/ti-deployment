package com.trackandtrail.dto;


import java.util.Date;


import com.trackandtrail.model.rideactivity.RideActivity;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;
@Data
public class RideActivityCommentsDto {
	
	
	private Long id;

	
	private RideActivity rideActivity;
	
	
	private UserModel user;
	
	private boolean isLike;
	
	private String comment;
	
	
//	private Date createdDate;



}
