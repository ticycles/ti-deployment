package com.trackandtrail.dto.ecommercev2;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class EcomCartDto {
	
	private Long id;


	private UserModel user;

	
	private EcomProductVariant variant;
	
	private Integer quantity;

}
