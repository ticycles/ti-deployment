package com.trackandtrail.model.challenge;

import static javax.persistence.TemporalType.TIMESTAMP;

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
import javax.persistence.Temporal;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Entity
@Table(name = "challenge")
@Data
public class Challenge extends AuditableBase {
	@Column(name = "challenge_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserModel user;

	private String name;

	private String description;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "ride_type_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private RideType rideType;

	private String image;

	private Integer totalMembers;

	private String duration;

	@Temporal(TIMESTAMP)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date fromDate;

	@Temporal(TIMESTAMP)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date toDate;

	private Float totalKilometers;

	private String genderAllowed;

	private String ageAllowed;

//    private String eligibility;
//
//    private String reward;
//
//    private String location;
//    
//    private String privacy;
//    

}
