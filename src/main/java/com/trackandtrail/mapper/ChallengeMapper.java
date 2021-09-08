package com.trackandtrail.mapper;

import com.trackandtrail.dto.ChallengeDto;
import com.trackandtrail.dto.MyCycleDto;
import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.mycycle.MyCycle;

public class ChallengeMapper {
	
	
	public static void toChallenge(ChallengeDto s, Challenge d) {
		if (s == null) {
			return;
		}
		if (s.getAgeAllowed() != null)
			d.setAgeAllowed(s.getAgeAllowed());
		
		if (s.getDescription() != null)
			d.setDescription(s.getDescription());
		
		if (s.getName() != null)
			d.setName(s.getName());
		
		if (s.getRideType() != null)
			d.setRideType(s.getRideType());
		
		if (s.getImage() != null)
			d.setImage(s.getImage());
		
		if (s.getTotalMembers() != null)
			d.setTotalMembers(s.getTotalMembers());
		
		if (s.getDuration() != null)
			d.setDuration(s.getDuration());
		
		if (s.getFromDate() != null)
			d.setFromDate(s.getFromDate());
		
		if (s.getToDate() != null)
			d.setToDate(s.getToDate());
		
		if (s.getTotalKilometers() != null)
			d.setTotalKilometers(s.getTotalKilometers());
		
		if (s.getGenderAllowed() != null)
			d.setGenderAllowed(s.getGenderAllowed());
		
		
		
		
		if (s.getUser() != null)
			d.setUser(s.getUser());

}
}
