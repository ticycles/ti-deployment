package com.trackandtrail.dto;


import java.util.Date;

import org.hibernate.type.TextType;

import com.trackandtrail.model.user.UserModel;

import lombok.Data;
import springfox.documentation.spring.web.json.Json;

@Data
public class NotificationAlertDto {

	private Long id;

	private String title;

	private String body;

	private String userType;

	private Long sender;

	private Long receiver;

	private String moduleName;

	private String page;

	private String data;

	private Integer messageRead=0;

	protected Date createdDate;
	
	private UserModel user;


}
