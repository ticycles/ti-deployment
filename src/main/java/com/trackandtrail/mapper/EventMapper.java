package com.trackandtrail.mapper;

import com.trackandtrail.dto.ChallengeDto;
import com.trackandtrail.dto.EventDto;
import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.event.Event;

public class EventMapper {

	public static void toEvent(EventDto s, Event d) {
		if (s == null) {
			return;
		}

		if (s.getGenderAllowed() != null)
			d.setGenderAllowed(s.getGenderAllowed());

		if (s.getAgeAllowed() != null)
			d.setAgeAllowed(s.getAgeAllowed());

		if (s.getDescription() != null)
			d.setDescription(s.getDescription());

		if (s.getDistanceInKms() != null)
			d.setDistanceInKms(s.getDistanceInKms());

		if (s.getEligibility() != null)
			d.setEligibility(s.getEligibility());

		if (s.getEndDate() != null)
			d.setEndDate(s.getEndDate());

		

		if (s.getEventName() != null)
			d.setEventName(s.getEventName());

		if (s.getEventType() != null)
			d.setEventType(s.getEventType());

		if (s.getUser() != null)
			d.setUser(s.getUser());

		if (s.getImageUrl() != null)
			d.setImageUrl(s.getImageUrl());

		if (s.getLocation() != null)
			d.setLocation(s.getLocation());

		if (s.getNoOfMembers() != null)
			d.setNoOfMembers(s.getNoOfMembers());

		if (s.getRewards() != null)
			d.setRewards(s.getRewards());

		if (s.getStartDate() != null)
			d.setStartDate(s.getStartDate());

		

		if (s.getTag() != null)
			d.setTag(s.getTag());

		if (s.getReason() != null)
			d.setReason(s.getReason());
	}
}
