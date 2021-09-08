package com.trackandtrail.dto;

import java.util.Date;

import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class PointsEarnedHistoryDto {

	private Long id;

	private UserModel user;

	private Date createdDate;

	private String module;

	private String moduleSlug;

	private Integer earnPoint;

	private Date updatedDate;

}
