package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackandtrail.model.role.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	List<Role> findByNameNotContaining(String name);
	
}
