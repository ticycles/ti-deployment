package com.trackandtrail.mapper;

import com.trackandtrail.dto.BadgeRewardConfDto;
import com.trackandtrail.model.configuration.BadgeRewardConf;

public class BadgeRewardConfMapper {
	
	public static void toBadgeRewardConf(BadgeRewardConfDto s,BadgeRewardConf d)
	{
		if(s==null)
		{
			return;
		}
		
		if(s.getActivityDistance()!=null)
			d.setActivityDistance(s.getActivityDistance());
		
		if(s.getActivityPoints()!=null)
			d.setActivityPoints(s.getActivityPoints());
		
		if(s.getModule()!=null)
			d.setModule(s.getModule());
		
		if(s.getNoOfBlogsPerDay()!=null)
			d.setNoOfBlogsPerDay(s.getNoOfBlogsPerDay());
		
		if(s.getBlogPoints()!=null)
			d.setBlogPoints(s.getBlogPoints());
		
		if(s.getShopItemPerDay()!=null)
			d.setShopItemPerDay(s.getShopItemPerDay());
		
		if(s.getShopPoints()!=null)
			d.setShopPoints(s.getShopPoints());
		
		if(s.getEventPerDay()!=null)
			d.setEventPerDay(s.getEventPerDay());
		
		if(s.getEventPoints()!=null)
			d.setEventPoints(s.getEventPoints());
		
		if(s.getEventEnrolledPerDay()!=null)
			d.setEventEnrolledPerDay(s.getEventEnrolledPerDay());
		
		if(s.getEventEnrolledPoints()!=null)
			d.setEventEnrolledPoints(s.getEventEnrolledPoints());
		
		if(s.getTntEventEnrolledPerDay()!=null)
			d.setTntEventEnrolledPerDay(s.getTntEventEnrolledPerDay());
		
		if(s.getTntEventEnrolledPoints()!=null)
			d.setTntEventEnrolledPoints(s.getTntEventEnrolledPoints());
		
		if(s.getNoOfChallengesPerDay()!=null)
			d.setNoOfChallengesPerDay(s.getNoOfChallengesPerDay());
		
		if(s.getChallengePoint()!=null)
			d.setChallengePoint(s.getChallengePoint());
		
		if(s.getChallengeEnrolledPerDay()!=null)
			d.setChallengeEnrolledPerDay(s.getChallengeEnrolledPerDay());
		
		if(s.getChallengeEnrolledPoint()!=null)
			d.setChallengeEnrolledPoint(s.getChallengeEnrolledPoint());
		
	
	}
}
