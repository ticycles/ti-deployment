package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.managedealer.ManageDealer;

@Repository
public interface ManageDealerRepository extends JpaRepository<ManageDealer,Long> {
	
	
	@Query(nativeQuery=true ,value="select * from manage_dealer where status = 1")
	List <ManageDealer> findActiveDealers();

}
