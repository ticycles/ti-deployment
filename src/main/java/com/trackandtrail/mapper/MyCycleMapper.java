package com.trackandtrail.mapper;

import com.trackandtrail.dto.MyCycleDto;
import com.trackandtrail.model.mycycle.MyCycle;

public class MyCycleMapper {
	
	public static void toMyCycle(MyCycleDto s, MyCycle d) {
		if (s == null) {
			return;
		}
		if (s.getBikeBrand() != null)
			d.setBikeBrand(s.getBikeBrand());
		
		if (s.getImage() != null)
			d.setImage(s.getImage());
		
		
	
	
	if (s.getUser() != null)
		d.setUser(s.getUser());
	

}
}
