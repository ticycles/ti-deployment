package com.trackandtrail.mapper.ecommercev2;

import com.trackandtrail.dto.ecommercev2.EcomAddressDetailsDto;
import com.trackandtrail.model.ecommercev2.EcomAddressDetails;

public class EcomAddressDetailsMapper {
	
	
	public static void toEcomAddressDetails(EcomAddressDetailsDto s, EcomAddressDetails d) {
		if (s == null) {
			return;
		}
		if (s.getFirstName() != null)
			d.setFirstName(s.getFirstName());
		
		if (s.getUser() != null)
			d.setUser(s.getUser());

		
		if (s.getLastName() != null)
			d.setLastName(s.getLastName());
		
		if (s.getMobile() != null)
			d.setMobile(s.getMobile());
		
		if (s.getPinCode() != null)
			d.setPinCode(s.getPinCode());
		
		if (s.getDoorNo() != null)
			d.setDoorNo(s.getDoorNo());
		
		if (s.getStreet() != null)
			d.setStreet(s.getStreet());
		
		if (s.getLandMark() != null)
			d.setLandMark(s.getLandMark());

		if (s.getCity() != null)
			d.setCity(s.getCity());

		if (s.getState() != null)
			d.setState(s.getState());
		
		if (s.getAddressType() != null)
			d.setAddressType(s.getAddressType());


		
	}
}
