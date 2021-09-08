package com.trackandtrail.view;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Data
@Entity
@Immutable
public class OrderPointCount {
	
	@Id
	private Long orderUserCount;

}
