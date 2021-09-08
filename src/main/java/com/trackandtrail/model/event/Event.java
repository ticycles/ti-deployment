package com.trackandtrail.model.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.user.UserModel;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.sql.Time;
import java.time.LocalTime;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

enum EventStatus {
	PENDING, APPROVED, REJECTED
}

@Entity
@Table(name = "event")
@Data
public class Event extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String genderAllowed;

	private String ageAllowed;

	private String description;

	private Float distanceInKms;

	private String eligibility;

	private String eventName;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "event_type_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private EventType eventType;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserModel user;

	private String imageUrl;

	private String location;

	private Integer noOfMembers;

	private String rewards;

	@Temporal(TIMESTAMP)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date startDate;

	@Temporal(TIMESTAMP)
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

//	@Transient
//	private Integer membersJoined;

}
