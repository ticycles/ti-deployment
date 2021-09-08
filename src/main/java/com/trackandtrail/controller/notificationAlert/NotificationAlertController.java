package com.trackandtrail.controller.notificationAlert;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.NotificationAlertDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.service.notificationAlert.NotificationAlertService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("notification")
public class NotificationAlertController {
	
	@Autowired
	private NotificationAlertService notificationAlertService;
	
	
	
	@ApiOperation(value = "createNotification",response = BaseResponseDTO.class)
	@PostMapping(value = "save",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody  NotificationAlertDto notificationAlertDto)throws Exception{
		
		
		if(notificationAlertDto.getId()!=null)
			
			throw new BadRequestException("id cannot contain value");
		return new ResponseEntity<BaseResponseDTO>(notificationAlertService.save(notificationAlertDto),HttpStatus.OK);

		}
		
	
	@ApiOperation(value = "getByUserId",response = BaseResponseDTO.class)
	@GetMapping(value = "getByUserId",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getByUserId(@RequestParam  Long userId) throws Exception{
		return new ResponseEntity<BaseResponseDTO>(notificationAlertService.getByUserId(userId),HttpStatus.OK);
	}
		
		
	@ApiOperation(value = "changeMessageReadById", response = BaseResponseDTO.class)
	@GetMapping(value = "/changeMessageReadById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> changeMessageReadById(@RequestParam Long id, @RequestParam Integer MessageReadId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(notificationAlertService.changeMessageReadById(id, MessageReadId), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateMessageRead", response = BaseResponseDTO.class)
	@GetMapping(value = "/updateMessageRead", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> updateMessageRead(@RequestParam Long userId) throws Exception {
		notificationAlertService.updateMessageRead(userId);
		BaseResponseDTO resp = new BaseResponseDTO();
		resp.setMsg("updated successfully");
		return new ResponseEntity<BaseResponseDTO>(resp, HttpStatus.OK);
	}
	
	
	}

