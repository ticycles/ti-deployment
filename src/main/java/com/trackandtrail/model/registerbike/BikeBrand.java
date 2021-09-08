package com.trackandtrail.model.registerbike;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;



@Entity
@Table(name="bike_brand")
@Data
public class BikeBrand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id", nullable = false)
	private Long id;
	
	private String name;
	
	


}
