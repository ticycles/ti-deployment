package com.trackandtrail.dto;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.model.content.Content;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;


@Data
public class ContentRatingDto {

	private Long id;

	private UserModel user;

	private Content content;


	private Float ratingStar;

	private String comment;

	private Boolean isLike;

}
