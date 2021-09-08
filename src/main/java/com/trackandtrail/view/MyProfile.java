package com.trackandtrail.view;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.trackandtrail.model.rideactivity.RideActivity;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
@Entity
@Immutable
public class MyProfile {

	@Id
	private Float distance;
	
	private Long userId;
	
	private String rideTime;
	
	private Float speed;
	
	
	
	

}
