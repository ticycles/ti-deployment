package com.trackandtrail.mapper;

import com.trackandtrail.dto.StoreDetailDto;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail;

public class StoreDetailMapper {
public static void toBadgeReward(StoreDetailDto s, StoreDetail d) {
             if (s == null) {
                    return;
                        }             
             
             if (s.getName() != null)
     			d.setName(s.getName());
             
             if (s.getAddress() != null)
     			d.setAddress(s.getAddress());
             
             if (s.getSourceLat() != null)
      			d.setSourceLat(s.getSourceLat());
             
             if (s.getSourceLong() != null)
      			d.setSourceLong(s.getSourceLong());
             
             if (s.getContact() != null)
      			d.setContact(s.getContact());
	
}	

}
