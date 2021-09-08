package com.trackandtrail.mapper;

import com.trackandtrail.dto.UserGroupDto;
import com.trackandtrail.model.user.UserGroup;

public class UserGroupMapper {
	public static void toUserGroup(UserGroupDto s, UserGroup d) {
		if (s == null) {
			return;
		}

		if (s.getDescription() != null)
			d.setDescription(s.getDescription());

		if (s.getFacebookUrl() != null)
			d.setFacebookUrl(s.getFacebookUrl());

		if (s.getGroupName() != null)
			d.setGroupName(s.getGroupName());

		if (s.getImage() != null)
			d.setImage(s.getImage());

		if (s.getType() != null)
			d.setType(s.getType());

		if (s.getUserGroupId() != null)
			d.setUserGroupId(s.getUserGroupId());
		
		if(s.getUser() != null)
			d.setUser(s.getUser());

	}
}
