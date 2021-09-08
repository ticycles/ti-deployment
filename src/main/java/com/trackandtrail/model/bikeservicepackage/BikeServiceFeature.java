package com.trackandtrail.model.bikeservicepackage;

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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name ="bike_service_feature")
@Data
@EqualsAndHashCode(callSuper=false)
public class BikeServiceFeature {
	
	

	 @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="feature_id")
	 private Long id;
	
	 private String detail;
	
	 @ManyToOne(fetch = FetchType.EAGER, optional = true)
	 @JoinColumn(name = "package_id", nullable = true)
	 @OnDelete(action = OnDeleteAction.CASCADE)
	 private BikeServicePackage bikeServicePackage;
	
	

}
