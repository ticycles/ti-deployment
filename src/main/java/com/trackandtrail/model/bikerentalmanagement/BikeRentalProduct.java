package com.trackandtrail.model.bikerentalmanagement;


	
	
	

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.registerbike.BikeBrand;
import com.trackandtrail.model.registerbike.BikeModel;

import lombok.Data;



@Entity
@Table(name ="bike_rental_management", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_sku"})})
@Data
	public class BikeRentalProduct  extends AuditableBase {
	    
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="bike_rental_product_id")
	    private Long id;	   
	    
	    @Column(name="product_sku", unique = true)
		private String productSku;	
	    
		private String name;
   
	    private String thumbnail;

	    
	    private String type;

	   
	    private Float price;
	    
	    private Float rating;
	    
	    private Integer offer;


	    @Column(name = "website_link")
	    private String websiteLink;
	    
	    private String color;

	    
	    private Float size;

	   
		private String about;

		
		private String gears;

		@Column(name="brakes_front")
		private String brakesFront;

	    @Column(name="handle_bar")
		private String handleBar;

	    @Column(name="road_type")
	    private String roadType;

	    @Column(name="brake_rear")
	    private String brakeRear;

	    
	    private String stem;

	    
	    private String frame;

	    @Column(name="field_stand")
	    private String fieldStand;

	    @Column(name="head_set")
	    private String headSet;

	    
	    private String crank;

	    @Column(name="seat_clamps")
	    private String seatClamps;

	    private String grips;

	    @Column(name="bottom_bracket")
	    private String bottomBracket;


	    private String spokes;

	    
	    private String saddle;

	    @Column(name="rear_derailleur")
	    private String rearDerailleur;


	    private String tires;

	    @Column(name="seat_post")
	    private String seatPost;

	   
	    private String rims;
	    
	    private String pedals;

	   
	    private String fork;

	    @Column(name="chain_cover")
	    private String chainCover;

	    @Column(name="brake_levers")
	    private String brakeLevers;

	    @ManyToOne(fetch = FetchType.EAGER, optional = true)
	    @JoinColumn(name = "brand_id", nullable = true)
	    @OnDelete(action = OnDeleteAction.CASCADE)
		private BikeBrand bikeBrand;
		
	    
	    
	    @ManyToOne(fetch = FetchType.EAGER, optional = true)
	    @JoinColumn(name = "model_id", nullable = true)
	    @OnDelete(action = OnDeleteAction.CASCADE)
		private BikeModel bikeModel;
	  
	   
	}







