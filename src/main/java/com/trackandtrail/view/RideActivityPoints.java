package com.trackandtrail.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Data
@Entity
@Immutable
public class RideActivityPoints {
	
	@Id
	private Float averageDistance;

}
