package com.trackandtrail.util;

public class MathUtil {
	
	
	public static Integer calculateRewardPoint(Float configNoItemsCount, Float currentSubmitPointByUser, Integer configPoints){
		Float quotient = currentSubmitPointByUser / configNoItemsCount ;	
		Long quotientRoundDown =   (long) Math.floor(quotient);	
		return  (int) (quotientRoundDown * configPoints) ;
	}

	public static Integer calculateRewardPoints(Integer configNoItemsCount, Long currentSubmitPointByUser, Integer configPoints) {
		
		Long quotient = currentSubmitPointByUser / configNoItemsCount ;	
		Long quotientRoundDown =   (long) Math.floor(quotient);	
		return  (int) (quotientRoundDown * configPoints) ;
	}

	
}
