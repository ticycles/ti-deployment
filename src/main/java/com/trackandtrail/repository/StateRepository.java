package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.registerbike.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	@Query(nativeQuery=true,value="select * from state order by name")
	List<State> findAllState();

}
