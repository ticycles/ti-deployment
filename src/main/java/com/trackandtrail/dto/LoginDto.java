package com.trackandtrail.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginDto implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String username;
	private String password;
	private Integer otp;
	private String Channel;
}