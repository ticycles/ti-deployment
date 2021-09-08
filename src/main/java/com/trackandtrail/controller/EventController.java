package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.ChallengeUserDto;
import com.trackandtrail.dto.EventDto;
import com.trackandtrail.dto.EventUserDto;
import com.trackandtrail.dto.RejectReasonDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.model.event.EventUser;
import com.trackandtrail.service.EventService;
import com.trackandtrail.service.EventUserService;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/event")
public class EventController {

	private static final Logger logger = LoggerFactory.getLogger(ChallengeController.class);

	@Autowired
	EventService eventService;

	@Autowired
	EventUserService eventUserService;

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createEvent", response = BaseResponseDTO.class)
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> createEvent(@RequestBody EventDto eventDto) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			logger.info("EventController:: createEvent :: contentModel :: ", eventDto.toString());
			if (eventDto.getId() != null)
				throw new BadRequestException("Id must be null");
			responseModel = eventService.save(eventDto);
			
			
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {

			logger.error("EventController:: createEvent:: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while create the event");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("EventController:: createEvent :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateEvent", response = BaseResponseDTO.class)
	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> updateEvent(@RequestBody EventDto eventDto) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			logger.info("EventController:: update :: contentModel :: ", eventDto.toString());
			if (eventDto.getId() == null)
				throw new BadRequestException("Id cannot be null");
			responseModel = eventService.update(eventDto);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {

			logger.error("EventController:: update :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while update the event");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("EventController:: update :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getall", response = BaseResponseDTO.class)
	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getall() {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = eventService.getAll();
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("EventController:: getall :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while getall");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("EventController:: getall :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getbyid", response = BaseResponseDTO.class)
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getbyid(@PathVariable("id") Long id) {
		System.out.println(id + "dfdfdf");
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {

			logger.error("EventController:: getbyid :: id :: " + id);

			responseModel = eventService.getById(id);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("EventController:: getbyid :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while getbyid");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("EventController:: getbyid :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deletebyid", response = BaseResponseDTO.class)
	@DeleteMapping(value = "/deletebyid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> deletebyid(@RequestParam Long id, @RequestParam Boolean flag) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = eventService.deleteById(id, flag);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("EventController:: delete :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while delete the event");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("EventController:: delete :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "changeStatusById", response = BaseResponseDTO.class)
	@GetMapping(value = "/changeStatusById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> changeStatusById(@RequestParam Long id, @RequestParam Integer statusId) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = eventService.changeStatusById(id, statusId);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("EventController:: changeStatusById :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while changeStatusById the event");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("EventController:: changeStatusById :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "searchAndPaginate", response = BaseResponseDTO.class)
	@PostMapping(value = "/searchAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> searchAndPaginate(@RequestBody PaginationDTO paginationDTO) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = eventService.searchAndPaginate(paginationDTO);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("EventController:: searchAndPaginate :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while searchAndPaginate the challenge");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("EventController:: searchAndPaginate :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_MOBILE')")
	@ApiOperation(value = "createEventUser", response = BaseResponseDTO.class)
	@PostMapping(value = "/joinEvent", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> createChallengeUser(@RequestBody EventUserDto eventUserDto) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			logger.info("ChallengeUserController:: createChallengeUser :: contentModel :: ", eventUserDto.toString());
			if (eventUserDto.getId() != null)
				throw new BadRequestException("Id must be null");
			responseModel = eventUserService.save(eventUserDto);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {

			logger.error("EventUserController:: createEventUser :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while create the eventuser");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("EventUserController:: createEventUser :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deleteEventUser", response = BaseResponseDTO.class)
	@DeleteMapping("/unJoinEvent")
	public ResponseEntity<BaseResponseDTO> removeUserByuserIdAndEventId(@RequestParam Long eventId,
			@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(eventUserService.unjoin(eventId, userId), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getJoinInfo", response = BaseResponseDTO.class)
	@GetMapping(value = "/getJoinInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getJoinInfo(@RequestParam Long id, @RequestParam Long eventId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(eventUserService.getJoinInfo(id, eventId), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getOverAllParticipants", response = BaseResponseDTO.class)
	@GetMapping(value = "/getOverAllParticipants", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getOverAllParticipants(@RequestParam Long eventId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(eventUserService.getOverAllParticipants(eventId), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getRegisteredFriends", response = BaseResponseDTO.class)
	@GetMapping(value = "/getRegisteredFriends", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getRegisteredFriends(@RequestParam Long userId, @RequestParam Long eventId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(eventUserService.getRegisteredFriends(userId, eventId),
				HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/getEventByUserId")
	public ResponseEntity<BaseResponseDTO> getByUserId(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(eventService.getByUserId(userId), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/getEventMemberCount")
	public ResponseEntity<BaseResponseDTO> getEventMemberCount() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(eventService.getEventMemberCount(), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAllEventType", response = BaseResponseDTO.class)
	@GetMapping("eventType/getall")
	public ResponseEntity<BaseResponseDTO> getAll() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(eventService.getAllEventType(), HttpStatus.OK);
	}

	@ApiOperation(value = "doFilerAndPaginate", response = BaseResponseDTO.class)
	@GetMapping(value = "/doFilerAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> doFilerAndPaginate(@RequestParam Long userid, @RequestParam String gender,
			@RequestParam String age, @RequestParam String plans, @RequestParam String title,
			@RequestParam String eventType, @RequestParam Integer pageNo, @RequestParam Integer pageSize)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(
				eventService.doFilterAndPaginate(userid, gender, age, plans, title, eventType, pageNo, pageSize),
				HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "eventStatus", response = BaseResponseDTO.class)
	@PostMapping(value = "/eventStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> changeStatusById(@RequestBody EventDto eventDto) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(eventService.changeEventStatusById(eventDto), HttpStatus.OK);
	}

	
}
