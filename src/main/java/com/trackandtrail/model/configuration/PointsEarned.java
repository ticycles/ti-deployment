package com.trackandtrail.model.configuration;

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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Entity
@Data
@Table(name = "points_earned")
public class PointsEarned {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "points_earned_id")
	private Long id;

	private String earnedDescription;

	private String nickName;

	private Integer rangeFromPoints;

	private Integer rangeToPoints;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserModel user;

	@CreatedDate
	@Temporal(TIMESTAMP)
	@Column(name = "created_on", updatable = false, nullable = true)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	protected Date createdDate;

	private Integer earnedPoints;

}
