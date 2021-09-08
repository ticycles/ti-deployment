package com.trackandtrail.service.impl.notificationAlert;

import java.util.Optional;

import javax.persistence.GeneratedValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.NotificationAlertDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.model.notificationAlert.NotificationAlert;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.notificationAlert.NotificationAlertRepository;
import com.trackandtrail.service.notificationAlert.NotificationAlertService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class NotificationAlertServiceImpl implements NotificationAlertService{

	
	@Autowired
	private NotificationAlertRepository notificationAlertRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Override
	public BaseResponseDTO save(NotificationAlertDto notificationDto) throws Exception {
		
		notificationAlertRepository.save(objectMapper.convertValue(notificationDto, NotificationAlert.class));
		
	
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}


	@Override
	public BaseResponseDTO getByUserId(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(notificationAlertRepository.findByUserId(userId));



	}


	@Override
	public BaseResponseDTO changeMessageReadById(Long id, Integer messageReadId) throws Exception {
		
		Optional<NotificationAlert> notificationAlert = notificationAlertRepository.findById(id);
		BaseResponseDTO responseDTO = null;
		if (notificationAlert.isPresent()) {
			switch (messageReadId) {
			case 2:
				notificationAlert.get().setMessageRead(RecordStatusUtil.RECORD_DELETED);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
				break;
			case 0:
				notificationAlert.get().setMessageRead(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			case 1:
				notificationAlert.get().setMessageRead(RecordStatusUtil.RECORD_ACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.ACTIVE.getErrorDescription(),
						RequestStatusUtil.ACTIVE.getErrorCode());
				break;
			default:
				notificationAlert.get().setMessageRead(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			}
			notificationAlertRepository.save(notificationAlert.get());
		} else {
			throw new ResourceNotFoundException("No notification found for given Id.");
		}
		return responseDTO;
	
	}


	@Override
	public int updateMessageRead(Long userId) throws Exception {
		return notificationAlertRepository.updateMessageRead(userId);
		}
}
		
		
		
		
		
		