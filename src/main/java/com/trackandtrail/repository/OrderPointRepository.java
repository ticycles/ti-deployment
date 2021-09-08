package com.trackandtrail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.view.OrderPointCount;

public interface OrderPointRepository extends JpaRepository<OrderPointCount, Long>{

	@Query(nativeQuery = true,value = "select  count(o.user_id) as order_user_count from ecomm_order_items i left join ecomm_orders o on o.order_id=i.order_id where o.user_id=:id and DATE(i.created_on)=:todayDate ")
	OrderPointCount getOrderUserCount(Long id, String todayDate);

	@Query(nativeQuery = true,value = "select  count(o.user_id) as order_user_count from ecomm_order_items i left join ecomm_orders o on o.order_id=i.order_id where o.user_id=:id")
	OrderPointCount getOverallDistance(Long id);

}
