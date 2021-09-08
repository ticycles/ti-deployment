package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.rideActivityComment.RideActivityComment;

@Repository
public interface RideActivityCommentsRepository extends JpaRepository<RideActivityComment, Long> {

//	@Query(nativeQuery = true, value="SELECT * FROM ride_activity_comments r   where r.rideId = :id and r.userId=:id")

	@Query(nativeQuery = true, value = "SELECT * FROM ride_activity_comments p where p.user_id=:id AND p.ride_id=:rideId")
	Optional<RideActivityComment> findByRideActivityIdAndUserId(Long id, Long rideId);

	@Query(nativeQuery = true, value = "SELECT * FROM ride_activity_comments p where p.user_id=:id AND p.ride_id=:rideId")
	Optional<RideActivityComment> findAllByRideActivityRideIdAndUserId(Long id, Long rideId);

	@Query(nativeQuery = true, value = "SELECT * FROM ride_activity_comments p where p.user_id=:userId AND p.ride_id=:rideId")
	RideActivityComment findByRideActivityRideIdAndUserId(Long userId, Long rideId);

	@Query(nativeQuery = true, value = "SELECT * FROM ride_activity_comments p where p.ride_id=:rideId and p.comment is not null")
	List<RideActivityComment> findByRideActivityRideIdByComment(Long rideId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM ride_activity_comments p where p.ride_id=:rideId and p.is_like=1")
	List<RideActivityComment> findByRideActivityRideIdByIsLike(Long rideId);
	


	
//	@Query(nativeQuery = true, value = "DELETE FROM ride_activity_comments p  where p.user_id =:id and p.ride_id  = :rideId")
//     RideActivityComments deleteByUserIdAndRideActivityId(@Param("id") Long id, @Param("rideId") Long rideId);
	
	
//	@Query(nativeQuery = true, value = "DELETE  FROM ride_activity_comments p where p.user_id=:id AND p.ride_id=:rideId")
//	RideActivityComments findByUserIdAndRideActivityId(Long id,Long rideId);


//
	

//	Optional<RideActivityComments> findAllByRideActivityIdAndUserId(Long rideId,Long userId);

}
