package com.trackandtrail.mapper;

import com.trackandtrail.dto.RegisterBikeDto;
import com.trackandtrail.dto.StockDto;
import com.trackandtrail.model.registerbike.RegisterBike;
import com.trackandtrail.model.stock.Stock;

public class StockMapper {
	
	
	public static void toStock(StockDto s, Stock d) {
		if (s == null) {
			return;
		}

		if (s.getBikeRentalProduct() != null)
			d.setBikeRentalProduct(s.getBikeRentalProduct());
		
		if (s.getQuantity() != null)
			d.setQuantity(s.getQuantity());
		
		
		if (s.getStoredetail() != null)
			d.setStoredetail(s.getStoredetail());

}
}
