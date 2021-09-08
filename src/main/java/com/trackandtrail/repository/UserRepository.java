package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.user.UserModel;


@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
	
	UserModel findByUsername(String username);
	
	Optional<UserModel> findByEmail(String emailid);

	Optional<UserModel> findByMobile(String mobile);
	
	List<UserModel> findByRoleNameNotContaining(String name);

	List<UserModel> findByRoleNameContaining(String name);
	
	
	@Query(nativeQuery=true,value="SELECT * FROM users u left join  user_summary_count_view s on u.user_id=s.user_id WHERE CONCAT( u.first_name,  ' ', u.last_name) LIKE  CONCAT('%', :search, '%') ")
	List<UserModel> getFriends(String search);	
	


	
}
