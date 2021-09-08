package com.trackandtrail.model.challenge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "challenge_user")
@Data
public class ChallengeUser extends AuditableBase  {
	@Column(name = "challenge_user_id")
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserModel user;

	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "challenge_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Challenge challenge;

    private Date dateOfJoining;
	
	private Float rideKm;
	
	private Float achievedKm;
}
