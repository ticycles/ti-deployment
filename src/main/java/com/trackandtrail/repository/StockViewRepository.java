package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.stock.Stock;
import com.trackandtrail.view.StockView;

public interface StockViewRepository extends JpaRepository<StockView, Integer>{
	
	
	@Query(nativeQuery = true,value = "SELECT IFNULL(b.booked_quantity, 0) as booked_quantity,  s.*, IFNULL(s.quantity -b.booked_quantity, 0) as available_quantity FROM stock s "
			+ "Left join bike_rental_booked_qty_view b on (b.store_id =  s.store_id and b.bike_rental_product_id = s.bike_rental_product_id)")
	List<StockView> findAllStock();


}
