package com.trackandtrail.dto.ecommercev2;

import java.io.Serializable;
import java.util.Date;

import com.trackandtrail.model.common.StaticParamModel;
import com.trackandtrail.model.ecommercev2.EcomAddressDetails;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class EcomOrderDto implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -8154573021617950833L;

	private Long id;

	private String orderNo;

	private StaticParamModel paymentMethod;

	private UserModel user;

	private EcomAddressDetails addressDetails;

	private StaticParamModel orderStatus;

	private Double totalAmount;

	private StaticParamModel taxType;

	private Double taxPercentage;

	private Double taxAmount;

	private Double discount;

	private Double discountAmount;

	private Double margin;

	private Double totalMargin;

	private String couponCode;

	private String couponType;

	private Double couponTypeValue;

	private Double couponAmount;

	private Integer couponApplied;

	private String firstName;

	private String lastName;

	private String mobile;

	private String pinCode;

	private String doorNo;

	private String street;

	private String landMark;

	private String city;

	private String addressType;

	private String state;

	

}
