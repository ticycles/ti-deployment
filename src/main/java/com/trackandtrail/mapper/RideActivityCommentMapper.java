package com.trackandtrail.mapper;

import com.trackandtrail.dto.RegisterBikeDto;
import com.trackandtrail.dto.RideActivityCommentsDto;
import com.trackandtrail.model.registerbike.RegisterBike;
import com.trackandtrail.model.rideActivityComment.RideActivityComment;

public class RideActivityCommentMapper {
	
	public static void toRideActivityComment(RideActivityCommentsDto s, RideActivityComment d) {
		if (s == null) {
			return;
		}

		if (s.getComment() != null)
			d.setComment(s.getComment());
		
//		if (s.getCreatedDate() != null)
//			d.setCreatedDate(s.getCreatedDate());
		
		if (s.getRideActivity() != null)
			d.setRideActivity(s.getRideActivity());
		
		if (s.getUser() != null)
			d.setUser(s.getUser());
		
		if(!s.isLike())
			d.setLike(false);
		else
			if(s.isLike())
				d.setLike(true);
			
}
}
