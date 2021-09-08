package com.trackandtrail.view.NewsFeed;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.content.Content;

import lombok.Data;

@Data
@Entity
@Immutable
@Table(name="/content_news_feed")
public class ContentNewsFeed {
	
	
	@Id
	private Long userId;

	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "content_id", nullable = true)
	private Content content;

}
