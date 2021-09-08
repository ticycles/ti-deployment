package com.trackandtrail.mapper;

import com.trackandtrail.dto.ContentRatingDto;
import com.trackandtrail.dto.EventDto;
import com.trackandtrail.model.content.ContentRating;
import com.trackandtrail.model.event.Event;

public class ContentRatingMapper {
	

	public static void toContentRating(ContentRatingDto s, ContentRating d) {
		if (s == null) {
			return;
		}

		if (s.getComment() != null)
			d.setComment(s.getComment());

		if (s.getContent() != null)
			d.setContent(s.getContent());
		
		if (s.getUser() != null)
			d.setUser(s.getUser());
		
		if(s.getRatingStar()!=null)
			d.setRatingStar(s.getRatingStar());
		
		if(!s.getIsLike())
			d.setIsLike(false);
		else
			if(s.getIsLike())
				d.setIsLike(true);
	}
	
}
