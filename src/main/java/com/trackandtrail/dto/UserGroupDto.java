package com.trackandtrail.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGroupDto {
    private Long userGroupId;

    private String image;
    
    private String extensionType;

    private String groupName;

    private String description;

    private String type;

    private String facebookUrl;
    
	private UserModel user;

}


