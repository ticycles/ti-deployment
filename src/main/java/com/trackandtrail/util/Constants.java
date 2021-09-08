package com.trackandtrail.util;

public interface Constants {

	public static final String BIKERENTAL_FILTER_QUERY = "select * from bike_rental_management where 1=1 ";
	
	public static final String BIKEBOOKING_QUERY = "select b.* from bike_booking b left join bike_rental_management r on r.bike_rental_product_id=b.bike_rental_product_id  where 1=1 ";

	public static final String BIKERENTAL_FILTER_QUERY_COUNT = "select count(*) from ( BIKERENTAL_FILTER_QUERY )as count";

	public static final String CHALLENGE_FILTER_QUERY = " select ch.* from challenge ch left join challenge_user chu on ch.challenge_id=chu.challenge_id left join ride_type rt on ch.ride_type_id= rt.ride_type_id WHERE 1=1 ";
	
	public static final String EVENT_FILTER_QUERY = " select e.* from event e left join event_user eu on e.id=eu.event_id left join event_type et on e.event_type_id=et.event_type_id where 1=1 ";
	
	public static final String BLOG_FILTER_QUERY = " select c.* from content c left join users u on c.user_id=u.user_id where 1=1 ";
	
	public static final String CONTENT_FILTER_QUERY = " select c.* from content c where 1=1 ";
	
//	public static final String ECOM_PROD_FILTER_QUERY = " select ev.* from ecomm_taxonomy_relationship etr \r\n" + 
//			"left join ecomm_taxonomy et on etr.taxonomy_id=et.taxonomy_id\r\n" + 
//			"left join static_param stp on  etr.category_id=stp.static_param_id\r\n" + 
//			"left join ecomm_taxonomy_term ett on etr.taxonomy_term_id=ett.term_id\r\n" + 
//			"left join ecomm_variant ev on etr.variant_id=ev.variant_id\r\n" + 
//			"where 1=1 ";

	public static final String ECOM_PRICE_RANGE_QUERY = "SELECT MIN(price) AS min_price, MAX(price) AS max_price from ecomm_variant v LEFT JOIN ecomm_product p ON p.product_id = v.product_id where 1=1 and v.category_id";
	public static final String ECOM_PROD_FILTER_QUERY = "SELECT "
			+ "IFNULL((SELECT ROUND(AVG(r.rating_star))  FROM ecomm_review r  WHERE r.variant_id = v.variant_id), 0)  AS averge_rating , "
			+ "v.*  FROM ecomm_variant v "
			+ " LEFT JOIN  ecomm_taxonomy_relationship r ON r.variant_id = v.variant_id "
			+ " LEFT JOIN ecomm_taxonomy_term e ON r.taxonomy_term_id = e.term_id "
			+ " LEFT JOIN ecomm_taxonomy t ON t.taxonomy_id = r.taxonomy_id "
            + " LEFT JOIN ecomm_product p ON p.product_id = v.product_id "
			+ " WHERE 1=1 "; 


	public static final String OTP_SMS= "Dear {name}, Your OTP is has {OTP},valid for 30 seconds only. Please do not share your OTP with anyone! - Track & Trail";
	
	public static final String NEW_ORDER="Dear {dealer_name},  there is a new order up for grabs. Order no. {order_number}";
	
	public static final String NEW_ORDER_PLACED="Dear Team, a new order from track & trail mobile app has been placed on {base_url}, Order no {order_number}, Visit the dashboard to view this order";
	
	public static final String NEW_ORDER_RECEVIED="Hi {customer_name}, your order for  {product_name} has been received. Order no. {order_number},Track your order at {order_url}";
			
	public static final String BIKE_RENTAL_ORDER_PLACED_DEALER="Dear ${dealer_name}, there is an order for bike rental  placed by the customer.  Order no. {order_number}";	
	
	public static final String BIKE_RENTAL_ORDER_PLACED_CUSTOMER="Dear {customer_name}, your order for bike rental  has been placed successfully.  Order no. {order_number}";
	
	
	public static final String BIKE_SERVICE_ORDER_PLACED_DEALER="Dear ${dealer_name}, there is an order for bike service placed by the customer.  Order no. {order_number}";
	
	public static final String BIKE_SERVICE_ORDER_PLACED_CUSTOMER="Dear {customer_name}, your order for bike service has been placed successfully.  Order no. {order_number}";

	public static final String SET_SMS_CONTENT="Dear {customer_name}, your order for bike service has been placed successfully.  Order no. {order_number}";

	public static final String USER_FOLLOW =" ${user_name} started following you.";
	
	public static final String JOIN_EVENT="${user_name},Congratulations!! You have successfully joined the {Event name}";
	
	
	public static final String JOIN_CHALLENGE="${user_name},Congratulations!! You have successfully joined the {challenges name}";
	
	public static final String PUBLISH_CONTENT="${user_name},Blog rating successfully submited";
	
	public static final String RIDE_ACTIVITY="${user_name},RideActivity like and comment  successfully submited";
	
}

