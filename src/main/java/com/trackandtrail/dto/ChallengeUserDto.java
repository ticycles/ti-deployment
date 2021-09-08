package com.trackandtrail.dto;

import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
public class ChallengeUserDto {

	private Long id;

	private UserModel user;

	private Challenge challenge;

	private Date dateOfJoining;

	private Float rideKm;

	private Float achievedKm;
}
