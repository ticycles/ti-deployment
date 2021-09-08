package com.trackandtrail.dto.ecommercev2;

import javax.persistence.Transient;

import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class EcomReviewDto {

	private Long id;

	private EcomProductVariant variant;

	private UserModel user;

	private Integer ratingStar;

	private String comment;
	
//	private Integer avgRating;
}
