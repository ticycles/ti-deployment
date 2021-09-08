package com.trackandtrail.dto;

import java.io.Serializable;
import java.util.Date;

import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class UserFollowDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 6242922725458741192L;
	
    private Long id;
    private UserModel user;
    private UserModel following;
    private Date createdOn;

}
