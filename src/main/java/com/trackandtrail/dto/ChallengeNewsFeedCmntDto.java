package com.trackandtrail.dto;

import javax.persistence.Id;

import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.view.NewsFeed.ChallengeNewsFeed;
import com.trackandtrail.view.NewsFeed.ContentNewsFeed;

import lombok.Data;


@Data
public class ChallengeNewsFeedCmntDto {
	
	
	@Id
	private Long id;

	private Challenge challengeNewsFeed;

	private String comment;

	private Boolean like;


}
