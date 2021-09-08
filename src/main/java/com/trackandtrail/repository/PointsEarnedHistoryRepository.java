package com.trackandtrail.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.trackandtrail.model.configuration.PointsEarnedHistory;

public interface PointsEarnedHistoryRepository extends JpaRepository<PointsEarnedHistory, Long> {

	@Modifying
	@Transactional
	@Query(nativeQuery=true,value="delete  from points_earned_history where user_id=:id and module=:module and module_slug=:moduleSlug and date(earn_date)=:earnDate")
	void removeByUserIdAndModuleSlug(Long id, String module,  String moduleSlug, String earnDate);

	@Query(nativeQuery=true,value="select *  from points_earned_history where user_id=:id and module=:module and module_slug=:moduleSlug and date(earn_date)=:todayDate")
	Optional<PointsEarnedHistory> findMyEarnedPoints(Long id, String module, String moduleSlug, String todayDate);


}
