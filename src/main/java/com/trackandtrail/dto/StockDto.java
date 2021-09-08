package com.trackandtrail.dto;

import java.io.Serializable;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail;
import lombok.Data;


@Data
public class StockDto implements Serializable {
	
	private static final long serialVersionUID = 2134576513544568999L;

	
	private Long id;
	
	private BikeRentalProduct bikeRentalProduct;

	private StoreDetail storedetail;
	
	private Integer quantity;
	

	

}
