package com.trackandtrail.repository.notificationAlert;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trackandtrail.model.notificationAlert.NotificationAlert;

@Repository
public interface NotificationAlertRepository extends JpaRepository<NotificationAlert, Long>{
	
	List<NotificationAlert> findByUserId(Long userId);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "update notification_alert set msg_read=1 where user_id=:userId")
	int updateMessageRead(Long userId);


}
