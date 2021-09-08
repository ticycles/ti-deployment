package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.bikerentalmanagement.BikeRentalBooking;
import com.trackandtrail.model.content.ContentRating;
@Repository
public interface BikeRentalBookingRepository extends JpaRepository<BikeRentalBooking, Long> {
	
	

List<BikeRentalBooking> findByUserModelId(Long userId);

List<BikeRentalBooking> findByBookingId(Long id);


@Query(nativeQuery=true,value="select * from bike_booking b where  b.bike_rental_product_id=:bikeRentalProductId and b.user_id=:userId")
Optional<BikeRentalBooking> findByBooking(Long userId,Long bikeRentalProductId);



	


}
