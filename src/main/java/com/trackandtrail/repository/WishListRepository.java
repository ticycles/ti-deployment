package com.trackandtrail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.configuration.WishList;





@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

}
