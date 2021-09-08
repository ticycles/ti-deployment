package com.trackandtrail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.trackandtrail.model.registerbike.RegisterBike;


@Repository
public interface RegisterBikeRepository extends JpaRepository<RegisterBike, Long> {
	
	List<RegisterBike> findByUserId(Long id);


}
