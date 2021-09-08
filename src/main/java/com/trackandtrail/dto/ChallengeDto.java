package com.trackandtrail.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.model.challenge.RideType;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class ChallengeDto implements Serializable {

	private static final long serialVersionUID = 2134576513544568999L;

	private Long id;

	private UserModel user;

	private String name;

	private String description;

	private RideType rideType;

	private String image;

	private Integer totalMembers;

	private String duration;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date fromDate;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date toDate;

	private Float totalKilometers;

	private String genderAllowed;

	private String ageAllowed;

}
