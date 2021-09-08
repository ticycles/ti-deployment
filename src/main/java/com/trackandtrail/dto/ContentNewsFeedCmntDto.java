package com.trackandtrail.dto;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.trackandtrail.model.content.Content;
import com.trackandtrail.view.NewsFeed.ContentNewsFeed;

import lombok.Data;

@Data
public class ContentNewsFeedCmntDto {

	@Id
	private Long id;

	private Content contentNewsFeed;

	private String comment;

	private Boolean like;

}
