package com.trackandtrail.dto.ecommercev2;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class EcomWishListDto {

	private Long id;

	private EcomProductVariant variant;

	private UserModel user;

}
