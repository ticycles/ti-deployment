package com.trackandtrail.dto;



import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class ReferalCodeDto {

	private Long id;

	private UserModel user;

	private String code;
	
	private Integer points;

}
