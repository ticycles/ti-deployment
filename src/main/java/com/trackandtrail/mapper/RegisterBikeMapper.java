package com.trackandtrail.mapper;

import com.trackandtrail.dto.RegisterBikeDto;
import com.trackandtrail.model.registerbike.RegisterBike;

public class RegisterBikeMapper {
	
	public static void toRegisterBike(RegisterBikeDto s, RegisterBike d) {
		if (s == null) {
			return;
		}

		if (s.getCustomerName() != null)
			d.setCustomerName(s.getCustomerName());
		
		if (s.getContactNumber() != null)
			d.setContactNumber(s.getContactNumber());
		
		if (s.getEmail() != null)
			d.setEmail(s.getEmail());
		
		if (s.getGender() != null)
			d.setGender(s.getGender());
		
		if (s.getAge() != null)
			d.setAge(s.getAge());
		
		if (s.getAddress() != null)
			d.setAddress(s.getAddress());
		
		if (s.getUser() != null)
			d.setUser(s.getUser());
		
		if (s.getState() != null)
			d.setState(s.getState());
		
		if (s.getCity() != null)
			d.setCity(s.getCity());
		
		if (s.getPincode() != null)
			d.setPincode(s.getPincode());

}
}
