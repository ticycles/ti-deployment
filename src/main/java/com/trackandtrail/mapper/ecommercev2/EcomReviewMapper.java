package com.trackandtrail.mapper.ecommercev2;

import com.trackandtrail.dto.ecommercev2.EcomReviewDto;

import com.trackandtrail.model.ecommercev2.EcomReview;

public class EcomReviewMapper {

	
	public static void toEcomReview(EcomReviewDto s, EcomReview d) {
		if (s == null) {
			return;
		}
		if (s.getRatingStar() != null)
			d.setRatingStar(s.getRatingStar());
		
		if (s.getUser() != null)
			d.setUser(s.getUser());
		
		
		if (s.getComment() != null)
			d.setComment(s.getComment());
		
		if (s.getVariant() != null)
			d.setVariant(s.getVariant());
		
		
		
	}

}
