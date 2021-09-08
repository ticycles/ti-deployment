package com.trackandtrail.model.rideActivityComment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.rideactivity.RideActivity;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Entity
@Table(name = "ride_activity_comments")
@Data
public class RideActivityComment extends AuditableBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ride_activity_comments_id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "ride_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private RideActivity rideActivity;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserModel user;

	@Column(name = "is_like")
	private boolean isLike;

	private String comment;

//	@CreatedDate
//	@Temporal(TIMESTAMP)
//	@Column(name = "created_on", updatable = false, nullable = true)
//	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
//	private Date createdDate;



}