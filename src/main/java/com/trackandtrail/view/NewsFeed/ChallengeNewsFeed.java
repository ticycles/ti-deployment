package com.trackandtrail.view.NewsFeed;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
@Entity
@Immutable
@Table(name="/challenge_news_feed")
public class ChallengeNewsFeed {
	
	
	
	
	@Id
	private Long userId;

	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "challenge_id", nullable = true)
	private Challenge challenge;
	
	
	

}
