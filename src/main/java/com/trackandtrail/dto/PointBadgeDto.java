package com.trackandtrail.dto;

import com.trackandtrail.model.configuration.BadgeEarned;
import com.trackandtrail.model.configuration.PointsEarnedHistory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointBadgeDto {
	
	private PointsEarnedHistory point;
	
	private BadgeEarned badge;

}
