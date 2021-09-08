package com.trackandtrail.dto;

import java.util.List;

import com.trackandtrail.dto.ecommercev2.OrderItemRequestDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadgeRewardConfDto {

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
	
	private List<BadgeConfigurationDto> badges;


}
