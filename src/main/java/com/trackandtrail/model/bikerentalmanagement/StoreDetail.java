package com.trackandtrail.model.bikerentalmanagement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trackandtrail.common.AuditableBase;

import lombok.Data;



@Entity
@Table(name="store_detail")
@Data
public class StoreDetail extends AuditableBase {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="store_id")
	private Long id;
	
	private String name;
	 
	private String address;
	
	private String sourceLat;
	
	private String sourceLong;
	
	private String contact;
	

}



