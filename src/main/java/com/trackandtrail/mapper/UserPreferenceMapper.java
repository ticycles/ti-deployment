package com.trackandtrail.mapper;

import com.trackandtrail.dto.UserPreferenceDto;
import com.trackandtrail.model.userpreference.UserPreference;

public class UserPreferenceMapper {
	
	
	public static void toUserPreference(UserPreferenceDto s, UserPreference d) {
		if (s == null) {
			return;
		}

		if (s.getData() != null)
			d.setData(s.getData());
		
		
		 
	
		if (s.getUserModel() != null)
			d.setUserModel(s.getUserModel());

}
}
