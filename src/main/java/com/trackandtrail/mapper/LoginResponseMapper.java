package com.trackandtrail.mapper;

import com.trackandtrail.dto.request.LoginResponseDTO;
import com.trackandtrail.model.user.UserModel;

public class LoginResponseMapper {

	public static void toUserModel(UserModel s, LoginResponseDTO d) {
		if (s == null) {
			return;
		}

		d.setLastLogin(s.getLastLogin());
		if (s.getId() != null)
			d.setId(s.getId());
		if (s.getUsername() != null)
			d.setUsername(s.getUsername());

		if (s.getFirstName() != null)
			d.setFirstName(s.getFirstName());

		if (s.getLastName() != null)
			d.setLastName(s.getLastName());

		if (s.getEmail() != null)
			d.setEmail(s.getEmail());

		if (s.getMobile() != null)
			d.setMobile(s.getMobile());

		if (s.getGender() != null)
			d.setGender(s.getGender());

		if (s.getBirthDate() != null)
			d.setBirthDate(s.getBirthDate());

		if (s.getBio() != null)
			d.setBio(s.getBio());

		if (s.getToken() != null)
			d.setToken(s.getToken());

		if (s.getPhotoUrl() != null)
			d.setPhotoUrl(s.getPhotoUrl());

		if (s.getLastLogin() != null)
			d.setLastLogin(s.getLastLogin());

		if (String.valueOf(s.getHeight()) != null)
			d.setHeight(s.getHeight());

		if (s.getHeightUnit() != null)
			d.setHeightUnit(s.getHeightUnit());

		if (String.valueOf(s.getWeight()) != null)
			d.setWeight(s.getWeight());

		if (s.getWeightUnit() != null)
			d.setWeightUnit(s.getWeightUnit());

		if (s.getRole() != null) {
			d.setRoleId(s.getRole().getRoleId());
			d.setRoleName(s.getRole().getName());
		}
		
		if(s.getFollowersCount() !=null)
			d.setFollowersCount(s.getFollowersCount());
		if(s.getFollowings() !=null)
			d.setFollowings(s.getFollowings());
		if(s.getChallengeCount() != null)
			d.setChallengeCount(s.getChallengeCount());
		if(s.getContentCount() != null)
			d.setContentCount(s.getContentCount());
		if(s.getOrderCount() != null)
			d.setOrderCount(s.getOrderCount());
		if(s.getRentalCount() != null)
			d.setRentalCount(s.getRentalCount());
		if(s.getEventCount() != null)
			d.setEventCount(s.getEventCount());
		
	}
}
