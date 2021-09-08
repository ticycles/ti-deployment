package com.trackandtrail.notification;

public interface NotificationService {

	public Object notification(NotificationDTO notificationDTO) throws Exception;

	public String sendSMS(NotificationDTO dto) throws Exception;
}
