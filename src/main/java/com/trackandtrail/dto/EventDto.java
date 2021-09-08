package com.trackandtrail.dto;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.event.EventType;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class EventDto extends AuditableBase {


	private Long id;

	private String genderAllowed;

	private String ageAllowed;

	private String description;

	private Float distanceInKms;

	private String eligibility;

	private String eventName;

	private EventType eventType;

	private UserModel user;

	private String imageUrl;

	private String location;

	private Integer noOfMembers;

	private String rewards;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date startDate;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date endDate;

//	private Time startTime;
//
//	private Time endTime;

	private String tag;

	private String reason;

	private String privacy;

	private String organizerName;

	private String organizerNumber;

	private String paymentMethod;

	private String upiId;

	private String accountHolderName;

	private String accountNumber;

	private String ifscCode;
	
	private String charges;
	
	private String rejectReason;

	


}
