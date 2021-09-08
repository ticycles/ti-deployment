package com.trackandtrail.dto;

import java.io.Serializable;

import com.trackandtrail.model.user.UserGroup;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class UserGroupMemberDto implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -4856770870947941094L;

	private Long id;
	private UserModel user;
	private UserGroup userGroup;
}
