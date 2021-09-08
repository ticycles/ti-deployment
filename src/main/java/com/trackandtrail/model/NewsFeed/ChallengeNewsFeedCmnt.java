package com.trackandtrail.model.NewsFeed;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.view.NewsFeed.ChallengeNewsFeed;
import com.trackandtrail.view.NewsFeed.ContentNewsFeed;

import lombok.Data;


@Entity
@Table(name="challenge_news_feed_cmnt")
@Data
public class ChallengeNewsFeedCmnt {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "challenge_id", nullable = true)
	private Challenge challengeNewsFeed;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	private UserModel user;
	
	private String comment;
	
	private Boolean like;

}
