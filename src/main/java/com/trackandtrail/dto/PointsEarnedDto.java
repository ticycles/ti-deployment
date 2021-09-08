package com.trackandtrail.dto;



import java.util.Date;


import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class PointsEarnedDto {

	private Long id;

	private String earnedDescription;


	private String title;

	private String nickName;

	private Integer rangeFromPoints;

	private Integer rangeToPoints;

	private UserModel user;

	
	protected Date createdDate;

	private Integer earnedPoints;


}
