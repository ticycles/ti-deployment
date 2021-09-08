package com.trackandtrail.dto;

import java.io.Serializable;
import java.util.Date;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail;
import com.trackandtrail.model.bikeservicepackage.BikeServicePackage;
import com.trackandtrail.model.registerbike.BikeBrand;
import com.trackandtrail.model.registerbike.BikeModel;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class BikeServiceDto implements Serializable {

	private static final long serialVersionUID = 2134576513544568999L;

	private Long id;

	private Long orderId;

	private String cycleName;

	private BikeBrand bikeBrand;

	private BikeModel bikeModel;

	private UserModel userModel;

	private StoreDetail storeDetail;

	private BikeServicePackage bikeServicePackage;

	private Double serviePrice;

	private Boolean pickUpDrop;

	private Double pickUpPrice;

	private String uuid;

	private Date serviceDate;

}
