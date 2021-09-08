package com.trackandtrail.view;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Data
@Entity
@Immutable
public class ChallengeCount {
	
	@Id
	private Long challengeId;
	
	private Long joined;

}
