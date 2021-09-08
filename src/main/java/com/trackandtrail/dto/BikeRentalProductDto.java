package com.trackandtrail.dto;

import javax.persistence.Column;

import com.trackandtrail.model.bikerentalmanagement.StoreDetail;
import com.trackandtrail.model.registerbike.BikeBrand;
import com.trackandtrail.model.registerbike.BikeModel;

import lombok.Data;

@Data
public class BikeRentalProductDto {

	private Long id;

	private String productSku;

	private String name;

	private String thumbnail;

	private String type;

	private Float price;

	private Float rating;

	private Integer offer;

	private String websiteLink;

	private String color;

	private Float size;

	private String about;

	private String gears;

	private String brakesFront;

	private String handleBar;

	private String roadType;

	private String brakeRear;

	private String stem;

	private String frame;

	private String fieldStand;

	private String headSet;

	private String crank;

	private String seatClamps;

	private String grips;

	private String bottomBracket;

	private String spokes;

	private String saddle;

	private String rearDerailleur;

	private String tires;

	private String seatPost;

	private String rims;

	private String pedals;

	private String fork;

	private String chainCover;

	private String brakeLevers;

	private BikeBrand bikeBrand;

	private BikeModel bikeModel;

}
