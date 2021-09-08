package com.trackandtrail.mapper;

import com.trackandtrail.dto.ReferalCodeDto;
import com.trackandtrail.dto.UserDto;
import com.trackandtrail.model.ReferalCode.ReferalCode;
import com.trackandtrail.model.user.UserModel;

public class ReferalCodeMapper {

	public static void toReferalCode(ReferalCodeDto s, ReferalCode d) {
		if (s == null) {
			return;
		}

		if (s.getCode() != null)
			d.setCode(s.getCode());
		
		if (s.getUser() != null)
			d.setUser(s.getUser());
}
}
