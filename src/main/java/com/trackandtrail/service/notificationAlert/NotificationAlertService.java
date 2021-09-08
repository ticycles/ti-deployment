package com.trackandtrail.service.notificationAlert;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.NotificationAlertDto;

public interface NotificationAlertService {
	
	public BaseResponseDTO save(NotificationAlertDto notificationAlertDto)throws Exception;
	
	public BaseResponseDTO getByUserId(Long userId)throws Exception;
	
	public BaseResponseDTO changeMessageReadById(Long id, Integer messageReadId) throws Exception;
	
	public int updateMessageRead(Long userId) throws Exception;

	


}
