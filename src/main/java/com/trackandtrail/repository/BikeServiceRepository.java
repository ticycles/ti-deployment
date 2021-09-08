package com.trackandtrail.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.trackandtrail.model.bikeservicepackage.BikeService;
import java.util.List;



@Repository
public interface BikeServiceRepository extends JpaRepository<BikeService, Long>{
	
	List<BikeService> findByUserModelId(Long id);


}
