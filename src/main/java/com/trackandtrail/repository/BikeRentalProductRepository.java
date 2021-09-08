package com.trackandtrail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;


@Repository
public interface BikeRentalProductRepository extends JpaRepository<BikeRentalProduct, Long> {	
	BikeRentalProduct findByProductSkuIgnoreCase(String productSku);	

	
}
