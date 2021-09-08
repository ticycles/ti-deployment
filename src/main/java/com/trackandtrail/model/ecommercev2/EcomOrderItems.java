package com.trackandtrail.model.ecommercev2;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.common.StaticParamModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ecomm_order_items")
@Getter @Setter
@EqualsAndHashCode(callSuper = false)
public class EcomOrderItems extends AuditableBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_items_id")
	private Long id;

//	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "order_id", nullable = true)
	private EcomOrders orders;

	private String productModelNumber;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "variant_id", nullable = true)
	private EcomProductVariant productVariant;

	private Integer quantity;

	private Double price;

	private Double subTotal;

	private Double itemCouponAmount;

	private Double itemMarginAmount;
	
	@Column(columnDefinition = "integer default 1", nullable = false)
	private Integer itemStatus;

	private Date statusDate;
	
	private Date confirmedOn;

	private Date placedOn;

	private Date porcessedOn;

	private Date shippedOn;

	private Date deliveredOn;
	
	private Date rejectedOn;
	
	private String rejectReason;
	
	private String courierCompany;
	
	private String trackingLink;
	
	private String trackingId;

}