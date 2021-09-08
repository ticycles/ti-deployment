package com.trackandtrail.view;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.trackandtrail.model.registerbike.BikeBrand;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
@Entity
@Immutable
public class BikeFilter {

	@Id
	private Integer size;

	private String color;
	
	private Integer price;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "brand_id", nullable = true)
	private BikeBrand bikeBrand;

}
