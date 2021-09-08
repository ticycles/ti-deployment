package com.trackandtrail.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.trackandtrail.model.user.UserGroup;
import com.trackandtrail.model.user.UserGroupMember;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
	
	
	@Query(nativeQuery=true,value="select * from user_group where user_id =:userId order  by created_on desc")
	List<UserGroup> findMyGroup(Long userId);

	Optional<UserGroup> findByUserId(Long userId);

	@Query(nativeQuery=true,value="select * from user_group where user_group_id =:groupId")
	List<UserGroup> getGroupName(Long groupId);

	@Query(nativeQuery=true,value="select * from user_group  order  by created_on desc")
	List<UserGroup> findAllGroups();
	

}
