package com.trackandtrail.mapper.ecommercev2;

import com.trackandtrail.dto.ecommercev2.EcomOrderDto;
import com.trackandtrail.model.ecommercev2.EcomOrders;

public class EcomOrderMapper {
	
	public static void toEcomOrder(EcomOrderDto s, EcomOrders d) {
		if (s == null) {
			return;
		}
		if (s.getAddressType() != null)
			d.setAddressType(s.getAddressType());
		
		if (s.getCouponAmount() != null)
			 d.setCouponAmount(s.getCouponAmount());
		
		if (s.getCouponApplied() != null)
			 d.setCouponApplied(s.getCouponApplied());
		
		if (s.getCouponCode() != null)
			 d.setCouponCode(s.getCouponCode());
		
		if (s.getCouponType() != null)
			 d.setCouponType(s.getCouponType());
		
		if (s.getCouponTypeValue() != null)
			 d.setCouponTypeValue(s.getCouponTypeValue());
		
		if (s.getDiscount() != null)
			 d.setDiscount(s.getDiscount());
		
		if (s.getDiscountAmount() != null)
			 d.setDiscountAmount(s.getDiscountAmount());
		
		if (s.getDoorNo() != null)
			 d.setDoorNo(s.getDoorNo());
		
		if (s.getFirstName() != null)
			 d.setFirstName(s.getFirstName());
		
		if (s.getLandMark() != null)
			 d.setLandMark(s.getLandMark());
		
		if (s.getLastName() != null)
			 d.setLastName(s.getLastName());
		
		if (s.getMargin() != null)
			 d.setMargin(s.getMargin());
		
		if (s.getOrderNo() != null)
			 d.setOrderNo(s.getOrderNo());
		

		if (s.getPinCode() != null)
			 d.setPinCode(s.getPinCode());
		
		if (s.getState() != null)
			 d.setState(s.getState());
		
		if (s.getStreet() != null)
			 d.setStreet(s.getStreet());
		
		if (s.getTaxAmount() != null)
			 d.setTaxAmount(s.getTaxAmount());
		
		if (s.getTaxPercentage() != null)
			 d.setTaxPercentage(s.getTaxPercentage());
		
		if (s.getTotalAmount() != null)
			 d.setTotalAmount(s.getTotalAmount());
		
		if (s.getTotalMargin() != null)
			 d.setTotalMargin(s.getTotalMargin());
		
		if (s.getUser() != null)
			 d.setUser(s.getUser());
		

	}

}
