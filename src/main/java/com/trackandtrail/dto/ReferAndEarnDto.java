package com.trackandtrail.dto;

import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class ReferAndEarnDto {
	
	
	private Integer id;
	
	private String Code;
	
	private UserModel user;


}
