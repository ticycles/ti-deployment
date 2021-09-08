package com.trackandtrail.dto;



import java.util.List;

import com.trackandtrail.model.configuration.BadgeRewardConf;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadgeConfigurationDto {

	private Long id;

	private BadgeRewardConf badgeRewardConf;

	private String batchName;

	private String description;

	private String image;

	private Float rangeFrom;

	private Float rangeTo;

}
