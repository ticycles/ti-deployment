package com.trackandtrail.dto.request;

import java.util.Date;

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
public class UserRequestDto {
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String mobile;
	private String gender;
	private Date birthDate;
	private String bio;
	private String token;
	private String photoUrl;
	private Date lastLogin;
	private float height;
	private String heightUnit;
	private float weight;
	private String weightUnit;
	private long roleId;
	
	

}
