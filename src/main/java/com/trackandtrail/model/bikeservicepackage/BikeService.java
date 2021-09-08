package com.trackandtrail.model.bikeservicepackage;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail;
import com.trackandtrail.model.registerbike.BikeBrand;
import com.trackandtrail.model.registerbike.BikeModel;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "bike_service")
@Data
@EqualsAndHashCode(callSuper = false)
public class BikeService extends AuditableBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_id")
	private Long id;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "cycle_name")
	private String cycleName;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "brand_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BikeBrand bikeBrand;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "model_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BikeModel bikeModel;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserModel userModel;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "store_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private StoreDetail storeDetail;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "package_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BikeServicePackage bikeServicePackage;

	@Column(name = "service_price")
	private Double serviePrice;

	@Column(name = "pickup_drop")
	private Boolean pickUpDrop;

	@Column(name = "pickup_price")
	private Double pickUpPrice;

	@Column(name = "service_date")
	private Date serviceDate;

	private String uuid;

}
