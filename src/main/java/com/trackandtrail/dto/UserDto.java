package com.trackandtrail.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.trackandtrail.model.role.Role;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2134576513544568999L;

	private Long id;

	private String username;

	private String firstName;

	private String lastName;

	private String password;

	private String email;

	private String mobile;

	private String gender;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date birthDate;

	private String bio;

	private String token;

	private String photoUrl;

	private Date lastLogin;

	private Float height;

	private String heightUnit;

	private Float weight;

	private String weightUnit;

	private Role role;

	private String fCMToken;

	private Float calories;

	private String caloriesUnit;

	private String heartBeat;

	private String Steps;

	private String emergencyContact1;

	private String emergencyContact2;

}
