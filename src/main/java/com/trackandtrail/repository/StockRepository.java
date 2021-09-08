package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.rideactivity.RideActivity;
import com.trackandtrail.model.stock.Stock;
import com.trackandtrail.model.userpreference.UserPreference;
import com.trackandtrail.view.StockView;


@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
	
	
	Optional<Stock> findByBikeRentalProductIdAndStoredetailId(Long id,Long StoreId);
	
	



}
