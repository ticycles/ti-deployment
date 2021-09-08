package com.trackandtrail.dto;

import java.util.Date;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trackandtrail.model.event.EventType;
import com.trackandtrail.model.user.UserModel;

public interface EventProjectionDto {

	Long getId();

	String getGenderAllowed();

	String getAgeAllowed();

	String getDescription();

	Float getDistanceInKms();

	String getEligibility();

	String getEventName();

	Long getEventType();

	Long getUser();

	String getImageUrl();

	String getLocation();

	Integer getNoOfMembers();

	String getRewards();

	Date getStartDate();

	Date getEndDate();

	String getTag();

	String getReason();

	String getPrivacy();

	String getOrganizerName();

	String getOrganizerNumber();

	String getPaymentMethod();

	String getUpiId();

	String getAccountHolderName();

	String getAccountNumber();

	String getIfscCode();

	String getCharges();

	String getRejectReason();

	Integer getMembersJoined();

}
