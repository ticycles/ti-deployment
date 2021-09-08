package com.trackandtrail.view.NewsFeed;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Immutable;

import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.content.Content;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
@Entity
@Immutable
public class SuggestedForYou {
	
	@Id
	private Long who;
	
	private Long whom;
	

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	private UserModel user;

}
