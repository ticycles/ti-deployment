package com.trackandtrail.dto;

import java.util.Date;

import com.trackandtrail.model.configuration.BadgeConfiguration;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class BadgeEarnedDto {

	private UserModel user;

	protected Date createdDate;

	private String badgeName;

	private Float rangeFrom;

	private Float rangeTo;

	private String module;

	private Float earnPoint;

	private String image;

	private String description;
}
