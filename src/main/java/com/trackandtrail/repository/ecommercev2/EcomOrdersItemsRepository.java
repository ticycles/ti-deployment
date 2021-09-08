package com.trackandtrail.repository.ecommercev2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.ecommercev2.EcomOrderItems;

public interface EcomOrdersItemsRepository extends JpaRepository<EcomOrderItems, Long>{


	@Query(nativeQuery=true,value="select i.* from ecomm_order_items i left join ecomm_orders o on i.order_id = o.order_id where o.user_id=:userId "
			+ "order by i.placed_on DESC")
	List<EcomOrderItems> findByOrderItemsByUserId(Long userId);
	
	@Query(nativeQuery=true,value="select i.* from ecomm_order_items i left join ecomm_orders o on i.order_id = o.order_id "
			+ "order by i.placed_on DESC")
	List<EcomOrderItems> findByAllOrderItems();

	
	List<EcomOrderItems> findByOrdersId(Long orderId);

}
