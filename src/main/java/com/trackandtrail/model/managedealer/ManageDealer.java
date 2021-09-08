package com.trackandtrail.model.managedealer;

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

import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.registerbike.City;
import com.trackandtrail.model.registerbike.Pincode;
import com.trackandtrail.model.registerbike.State;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity

@Data
@Table(name = "manage_dealer")
@EqualsAndHashCode(callSuper = false)
public class ManageDealer extends AuditableBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shop_id")
	private Long id;

	@Column(name = "shop_name", unique = true)
	private String shopName;

	@Column(name = "dealer_name")
	private String dealerName;

	private String email;

	@Column(name = "phone_number")
	private Long phoneNumber;

	@Column(name = "address_line1")
	private String addressLine1;

	@Column(name = "address_line2")
	private String addressLine2;

	@Column(name = "land_mark")

	private String landMark;

	@Column(name = "pickup_drop")
	private Boolean pickUpDrop;

	private Integer charge;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "state_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private State state;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "city_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private City city;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "pincode_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Pincode pincode;

}
