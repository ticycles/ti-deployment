package com.trackandtrail.model.configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="badge_configuration")
public class BadgeConfiguration {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Column(name="badge_configuration_id")
	private Long id;
	

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "badge_reward_conf_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BadgeRewardConf badgeRewardConf;
	
	
	private String batchName;
	
	private String description;
	
	private String image;
	
	private Float rangeFrom;
	
	private Float rangeTo;




}
