package com.trackandtrail.model.user;

import java.util.Date;

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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.role.Role;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_name", "email", "mobile" }) })
@Data
@EqualsAndHashCode(callSuper = false)
@SecondaryTable(name = "user_summary_count_view", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "user_id"))
public class UserModel extends AuditableBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "user_name", unique = true, nullable = false)
	private String username;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "mobile", unique = true)
	private String mobile;

	private String gender;

	@Column(name = "birth_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date birthDate;

	private String bio;

	private String token;

	@Column(name = "photo_url")
	private String photoUrl;

	@Column(name = "last_login")
	private Date lastLogin;

	private Float height;

	@Column(name = "height_unit")
	private String heightUnit;

	private Float weight;

	private Float calories;

	private String caloriesUnit;

	@Column(name = "weight_unit")
	private String weightUnit;
	
	private String heartBeat;
	
	private String Steps;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "role_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Role role;

	private String fCMToken;

	@Column(name = "followers", table = "user_summary_count_view", insertable = false, updatable = false)
	private Long followersCount;

	@Column(name = "followings", table = "user_summary_count_view", insertable = false, updatable = false)
	private Long followings;

	@Column(name = "order_count", table = "user_summary_count_view", insertable = false, updatable = false)
	private Long orderCount;

	@Column(name = "rental_count", table = "user_summary_count_view", insertable = false, updatable = false)
	private Long rentalCount;

	@Column(name = "content_count", table = "user_summary_count_view", insertable = false, updatable = false)
	private Long contentCount;

	@Column(name = "challenge_count", table = "user_summary_count_view", insertable = false, updatable = false)
	private Long challengeCount;

	@Column(name = "event_count", table = "user_summary_count_view", insertable = false, updatable = false)
	private Long eventCount;

	private String emergencyContact1;

	private String emergencyContact2;

	@Transient
	private Boolean following;

	public UserModel(long id) {
		super();
		this.id = id;
	}

	public UserModel() {
		super();
	}

}
