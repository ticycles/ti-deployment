package com.trackandtrail.repository;

import java.util.Optional;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail; 
import com.trackandtrail.model.registerbike.RegisterBike;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.model.userpreference.UserPreference;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
	
	
	@Query(nativeQuery = true, value = "SELECT * from user_preference p left join users u  on u.user_id = p.user_id where p.user_id = :id")
	Optional<UserPreference> findByUserModelId(Long id);
	
	@Query(nativeQuery = true, value = "SELECT * from user_preference p left join users u  on u.user_id = p.user_id where p.user_id = :id")
	UserPreference findByUserPrefernceByUserId(Long id);	
	


}
