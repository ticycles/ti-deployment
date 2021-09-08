package com.trackandtrail.mapper;

import com.trackandtrail.dto.ManageDealerDto;
import com.trackandtrail.model.managedealer.ManageDealer;

public class ManageDealerMapper {
	public static void toManageDealers(ManageDealerDto s, ManageDealer d) {
		if (s == null) {
			return;
		}

		if (s.getShopName() != null)
			d.setShopName(s.getShopName());

		if (s.getDealerName() != null)
			d.setDealerName(s.getDealerName());

		if (s.getEmail() != null)
			d.setEmail(s.getEmail());

		if (s.getPhoneNumber() != null)
			d.setPhoneNumber(s.getPhoneNumber());

		if (s.getAddressLine1() != null)
			d.setAddressLine1(s.getAddressLine1());

		if (s.getAddressLine2() != null)
			d.setAddressLine2(s.getAddressLine2());

		if (s.getLandMark() != null)
			d.setLandMark(s.getLandMark());

		if (s.getPickUpDrop() != null)
			d.setPickUpDrop(s.getPickUpDrop());

		if (s.getCharge() != null)
			d.setCharge(s.getCharge());

		if (s.getState() != null)
			d.setState(s.getState());

		if (s.getCity() != null)
			d.setCity(s.getCity());

		if (s.getPincode() != null)
			d.setPincode(s.getPincode());
	}

}
