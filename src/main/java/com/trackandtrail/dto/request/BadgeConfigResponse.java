package com.trackandtrail.dto.request;

import java.util.Date;

import com.trackandtrail.model.user.UserModel;

import lombok.Getter;

@Getter
public class BadgeConfigResponse {


	private UserModel user;

	protected Date createdDate;

	private String badgeName;

	private Float rangeFrom;

	private Float rangeTo;

	private String module;

	private Float earnPoint;

	private String title;

	private String description;

}
