package com.trackandtrail.dto;



import com.trackandtrail.model.user.UserModel;

import lombok.Data;



@Data
public class UserPreferenceDto {
	
	
    private Long id;
	
	
	private String data;
	
	
	private UserModel userModel;



	
}
