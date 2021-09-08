package com.trackandtrail.model.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.model.ecommercev2.EcomOrderItems;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "badge_reward_conf")
public class BadgeRewardConf {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "badge_reward_conf_id")
	private Long id;

	private Float activityDistance;

	private Integer activityPoints;

	private String module;

	private Integer noOfBlogsPerDay;

	private Integer blogPoints;

	private Integer shopItemPerDay;

	private Integer shopPoints;

	private Integer eventPerDay;

	private Integer eventPoints;

	private Integer eventEnrolledPerDay;

	private Integer eventEnrolledPoints;

	private Integer tntEventEnrolledPerDay;

	private Integer tntEventEnrolledPoints;

	private Integer noOfChallengesPerDay;

	private Integer challengePoint;
	
	private Integer challengeEnrolledPerDay;

	private Integer challengeEnrolledPoint;
	
	@JsonIgnore
	@OneToMany(mappedBy = "badgeRewardConf")
	private List<BadgeConfiguration> badges = new ArrayList<BadgeConfiguration>();

}
