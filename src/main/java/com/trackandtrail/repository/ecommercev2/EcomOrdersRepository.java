package com.trackandtrail.repository.ecommercev2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackandtrail.model.ecommercev2.EcomOrders;

public interface EcomOrdersRepository extends JpaRepository<EcomOrders, Long>{
	
	List<EcomOrders> findByUserId(Long userId);

}
