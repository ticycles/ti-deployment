package com.trackandtrail.model.ecommercev2;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.common.StaticParamModel;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "ecomm_orders")
//@ToString(includeFieldNames = true)

public class EcomOrders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;

	private String orderNo;

	private String paymentMethod;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = false)
	private UserModel user;

	private Double totalAmount;

	private String taxType;

	private Double taxPercentage;

	private Double taxAmount;

	private Double discount;

	private Double discountAmount;

	private Double margin;

	private Double totalMargin;

	private String couponCode;

	private String couponType;

	private Double couponTypeValue;

	private Double couponAmount;

	private Integer couponApplied;

	private String firstName;

	private String lastName;

	private String mobile;

	private String pinCode;

	private String doorNo;

	private String street;

	private String landMark;

	private String city;

	private String addressType;

	private String state;

	private String paymentTransactionId;

	private String paymentStatus;

	private Double paymentDeductedAmount;

	

	@Column(columnDefinition = "integer default 1", nullable = false)
	private Integer orderStatus;

	@JsonIgnore
	@OneToMany(mappedBy = "orders")
	private List<EcomOrderItems> items = new ArrayList<EcomOrderItems>();
}
