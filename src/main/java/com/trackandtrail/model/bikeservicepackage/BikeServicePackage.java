package com.trackandtrail.model.bikeservicepackage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.trackandtrail.common.AuditableBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name ="bike_service_package")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(Include.NON_NULL)
public class BikeServicePackage extends AuditableBase {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="package_id")
	private Long id;
	
	@Column(name="package_name")
	private String name;
	
	private String image;
	
	private Double price;

}
