package com.trackandtrail.dto.request;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import lombok.Data;

@Data
public class LoginResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String firstName;

	private String lastName;

	private String mobile;

	private String email;

	private String username;

	private String gender;

	private Integer status;

	private Date birthDate;

	private String bio;

	private String token;

	private String photoUrl;

	private Date lastLogin;

	private Float height;

	private String heightUnit;

	private Float weight;

	private String weightUnit;

	private Float calories;

	private String caloriesUnit;

	private String heartBeat;

	private String Steps;

	private Long roleId;

	private String roleName;

	private Long followersCount;

	private Long followings;

	private Long orderCount;

	private Long rentalCount;

	private Long contentCount;

	private Long challengeCount;

	private Long eventCount;

	private String fCMToken;

}
