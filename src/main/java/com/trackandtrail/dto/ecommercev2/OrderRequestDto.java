package com.trackandtrail.dto.ecommercev2;

import java.util.List;

import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class OrderRequestDto {

	private String paymentMethod;
	
	private String orderNo;

	private String paymentTransactionId;

	private String paymentStatus;

	private Double paymentDeductedAmount;

	private UserModel user;

	private Double totalAmount;

	private String taxType;

	private Double taxPercentage;

	private Double taxAmount;

	private Double discount;

	private Double discountAmount;

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
	
	private List<OrderItemRequestDto> items;

}
