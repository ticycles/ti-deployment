package com.trackandtrail.dto;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.model.content.ContentRating;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class ContentDto {

	private Long id;

	private UserModel user;

	private String content;

	private String title;

	private String image;

	private String slug;

	private String excerpt;

	private String tags;

	private Integer views;

	private Integer likeCount;

	private Integer commentCount;
	
	
}
