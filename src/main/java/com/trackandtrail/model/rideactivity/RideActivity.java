package com.trackandtrail.model.rideactivity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ride_activity")
@Data
@EqualsAndHashCode(callSuper = false)
@SecondaryTable(name = "ride_acitivity_count_view", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ride_id", referencedColumnName = "ride_id"))
public class RideActivity extends AuditableBase {

	@Id
	@Column(name = "ride_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rideId;

	private String name;

	// optional field
	private String image;
	private String rate;

	private String privacy;

	private String description;

	@Column(name = "avg_time")
	private String averageTime;

	@Column(name = "avg_speed")
	private Float averageSpeed;

	@Column(name = "avg_distance")
	private Float averageDistance;

	@Column(name = "source_lat")
	private String sourceLat;

	@Column(name = "source_long")
	private String sourceLong;

	@Column(name = "destination_lat")
	private String destinationLat;

	@Column(name = "destination_long")
	private String destinationLong;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	private UserModel user;


	
	

	@Column(name = "like_count", table = "ride_acitivity_count_view", insertable = false, updatable = false)
	private Long likeCount;

	@Column(name = "comment_count", table = "ride_acitivity_count_view", insertable = false, updatable = false)
	private Long commentCount;

}
