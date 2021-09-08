package com.trackandtrail.model.stock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Stock extends AuditableBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stock_id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "bike_rental_product_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BikeRentalProduct bikeRentalProduct;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "store_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private StoreDetail storedetail;

	private Integer quantity;


}
