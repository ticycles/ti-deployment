package com.trackandtrail.mapper;

import com.trackandtrail.dto.ChallengeDto;
import com.trackandtrail.dto.ContentDto;
import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.content.Content;

public class ContentMapper {
	
	
	public static void toContent(ContentDto s, Content d) {
		if (s == null) {
			return;
		}
		if (s.getTitle() != null)
			d.setTitle(s.getTitle());
		
		if (s.getSlug() != null)
			d.setSlug(s.getSlug());

		
		if (s.getContent() != null)
			d.setContent(s.getContent());

		
		if (s.getExcerpt() != null)
			d.setExcerpt(s.getExcerpt());

		
		if (s.getUser() != null)
			d.setUser(s.getUser());

		
		if (s.getTags() != null)
			d.setTags(s.getTags());

		
		if (s.getImage() != null)
			d.setImage(s.getImage());
		
		if (s.getViews() != null)
			d.setViews(s.getViews());
		
		if (s.getLikeCount() != null)
			d.setLikeCount(s.getLikeCount());
		
		if (s.getCommentCount() != null)
			d.setCommentCount(s.getCommentCount());

		
	}


}
