package com.trackandtrail.model.configuration;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Entity
@Table(name = "wish_list")
@Data
public class WishList extends AuditableBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long wishListId;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "bike_rental_product_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BikeRentalProduct bikeRentalProduct;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserModel user;

}
