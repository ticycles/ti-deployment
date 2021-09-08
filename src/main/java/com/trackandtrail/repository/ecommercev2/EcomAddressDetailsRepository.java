package com.trackandtrail.repository.ecommercev2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackandtrail.model.ecommercev2.EcomAddressDetails;

public interface EcomAddressDetailsRepository extends JpaRepository<EcomAddressDetails, Long>{

	List<EcomAddressDetails> findByUserId(Long id);

}
