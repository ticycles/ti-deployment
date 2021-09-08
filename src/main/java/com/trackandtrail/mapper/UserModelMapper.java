package com.trackandtrail.mapper;
import com.trackandtrail.dto.UserDto;
import com.trackandtrail.model.user.UserModel;

public class UserModelMapper {
	public static void toUserModel(UserDto s, UserModel d) {
		if (s == null) {
			return;
		}

		if (s.getUsername() != null)
			d.setUsername(s.getUsername());

		if (s.getFirstName() != null)
			d.setFirstName(s.getFirstName());

		if (s.getLastName() != null)
			d.setLastName(s.getLastName());

		if (s.getPassword() != null)
			d.setPassword(s.getPassword());

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

		if (s.getRole() != null)
			d.setRole(s.getRole());
		
		if (s.getEmergencyContact1() != null)
			d.setEmergencyContact1(s.getEmergencyContact1());
		
		if (s.getEmergencyContact2() != null)
			d.setEmergencyContact2(s.getEmergencyContact2());
		
		if (s.getFCMToken() != null)
			d.setFCMToken(s.getFCMToken());
		
		if (s.getCalories() != null)
			d.setCalories(s.getCalories());
		
		if (s.getCaloriesUnit() != null)
			d.setCaloriesUnit(s.getCaloriesUnit());
		
		if (s.getHeartBeat() != null)
			d.setHeartBeat(s.getHeartBeat());
		
		if (s.getSteps() != null)
			d.setSteps(s.getSteps());


		}
}
