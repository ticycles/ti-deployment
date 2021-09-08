package com.trackandtrail.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.user.UserGroup;
import com.trackandtrail.model.user.UserGroupMember;

public interface UserGroupMemberRepository extends JpaRepository<UserGroupMember, Long>{

	Optional<UserGroupMember> findByUserGroupUserGroupIdAndUserId(Long groupId, Long userId);
	
	
	@Query(nativeQuery=true,value="select * from user_group_member m  left join user_group g on g.user_id = m.user_id where m.user_id =:userId")
	List<UserGroupMember> findMyGroup(Long userId);


	@Query(nativeQuery=true,value = "Select user_group_id from user_group_member where user_id =:id")
	List<Long> getJoinenMembers(Long id);




	@Query(nativeQuery=true,value ="select user_group_id from user_group_member where user_id=:id")
	List<Long> findGroupMember(Long id);


	List<UserGroupMember> findByUserId(Long userId);


	List<UserGroupMember> findByUserGroupUserGroupId(Long groupId);
	
	
	
}
