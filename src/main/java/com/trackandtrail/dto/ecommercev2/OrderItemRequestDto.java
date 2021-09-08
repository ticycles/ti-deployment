package com.trackandtrail.dto.ecommercev2;


import lombok.Data;

@Data
public class OrderItemRequestDto {

	private Long variantId;

	private Integer quantity;

	private Double price;

	private Double subTotal;

}
