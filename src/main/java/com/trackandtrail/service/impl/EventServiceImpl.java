package com.trackandtrail.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.EventDto;
import com.trackandtrail.dto.PointBadgeDto;
import com.trackandtrail.dto.RejectReasonDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.EventMapper;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalBooking;
import com.trackandtrail.model.configuration.BadgeConfiguration;
import com.trackandtrail.model.configuration.BadgeEarned;
import com.trackandtrail.model.configuration.BadgeRewardConf;
import com.trackandtrail.model.configuration.PointsEarnedHistory;
import com.trackandtrail.model.event.Event;
import com.trackandtrail.model.event.EventType;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.BadgeConfigurationRepository;
import com.trackandtrail.repository.BadgeEarnedRepository;
import com.trackandtrail.repository.BadgeRewardConfRepository;
import com.trackandtrail.repository.EventCountRepository;
import com.trackandtrail.repository.EventPointRepository;
import com.trackandtrail.repository.EventRepository;
import com.trackandtrail.repository.EventTypeRepository;
import com.trackandtrail.repository.EventUserRepository;
import com.trackandtrail.repository.PointsEarnedHistoryRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.AlertService;
import com.trackandtrail.service.EventService;
import com.trackandtrail.util.Constants;
import com.trackandtrail.util.GenericSearchandPaginationUtil;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.MathUtil;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;
import com.trackandtrail.view.EventPoint;
import com.trackandtrail.view.RideActivityPoints;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;

	@Autowired
	EventPointRepository eventPointRepository;

	@Autowired
	EventUserRepository eventUserRepository;

	@Autowired
	EventTypeRepository eventTypeRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	BadgeRewardConfRepository badgeRepo;

	@Autowired
	BadgeConfigurationRepository badgeConfigRepo;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepository;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepo;

	@Autowired
	PointsEarnedHistoryRepository PointsEarnedHistoryrepo;

	@Autowired
	EntityManager entityManager;

	@Autowired
	EventCountRepository eventCountRepository;
	
	@Autowired
	 AlertService alertService;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public BaseResponseDTO save(EventDto eventDto) throws Exception {

		BaseResponseDTO resp = new BaseResponseDTO();

		eventRepository.save(objectMapper.convertValue(eventDto, Event.class));
		
		Optional<EventType> eventType=eventTypeRepository.findById(eventDto.getEventType().getId());
//		Optional<Event> events=eventRepository.findById(eventDto.getUser().getCreatedBy());
		 Optional<UserModel> user=userRepository.findById(eventDto.getUser().getId()); 
//		 
//		 if(eventType.isPresent() && events.isPresent() && user.isPresent())
		 if(eventType.isPresent() && user.isPresent())
			  System.out.println("====================================s");
//			 alertService.sendEventAlert(eventType.get(),events.get(),user.get());
		 alertService.sendEventAlert(eventType.get(),user.get());

			 
			 
		String module = "events";
		String moduleSlug = "events_created_per_day";

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
		String todayDate = formatter.format(date);
        
		EventPoint event = eventPointRepository.getEventUserCount(eventDto.getUser().getId(), todayDate);

		Optional<BadgeRewardConf> badge = badgeRepo.findBadgeRewardConfModule("events");
		if (badge.isPresent()) {
			if (event.getEventUserCount() > badge.get().getEventPerDay()) {

				Integer pt = MathUtil.calculateRewardPoints(badge.get().getEventPerDay(), event.getEventUserCount(),
						badge.get().getEventPoints());

				Integer currentPoint = null;

				Optional<PointsEarnedHistory> ptHistory = PointsEarnedHistoryrepo
						.findMyEarnedPoints(eventDto.getUser().getId(), module, moduleSlug, todayDate);
				if (ptHistory.isPresent()) {

					// remove
					PointsEarnedHistoryrepo.removeByUserIdAndModuleSlug(eventDto.getUser().getId(), module, moduleSlug,
							todayDate);

					currentPoint = ((pt) - (ptHistory.get().getEarnPoint()));
				} else {
					currentPoint = (pt - badge.get().getEventPoints());
				}

				PointsEarnedHistory pointHistory = new PointsEarnedHistory();

				pointHistory.setUser(eventDto.getUser());
				pointHistory.setModule(module);
				pointHistory.setModuleSlug(moduleSlug);
				pointHistory.setCreatedDate(new Date());
				pointHistory.setEarnPoint(pt);
				PointsEarnedHistoryrepo.save(pointHistory);

				PointBadgeDto pointBadge = new PointBadgeDto();
				
				BadgeEarned earnBatch = new BadgeEarned();


				Optional<EventPoint> eventPoint = eventPointRepository.getOverallPoints(eventDto.getUser().getId());

				Optional<BadgeConfiguration> userBadge = badgeConfigRepo
						.getUserBadge(eventPoint.get().getEventUserCount(), "activity");
				if (userBadge.isPresent()) {

					Optional<BadgeEarned> badgeEarned = badgeEarnedRepo.findRange(userBadge.get().getRangeFrom(),
							userBadge.get().getRangeTo(), eventDto.getUser().getId(), userBadge.get().getBatchName(),
							module);
					if (badgeEarned.isPresent()) {

						badgeEarnedRepo.deleteById(badgeEarned.get().getId());


						earnBatch.setBadgeName(userBadge.get().getBatchName());
						earnBatch.setDescription(userBadge.get().getDescription());
						earnBatch.setModule(module);
						earnBatch.setImage(userBadge.get().getImage());
						earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
						earnBatch.setRangeTo(userBadge.get().getRangeTo());
						earnBatch.setUser(eventDto.getUser());
//				earnBatch.setEarnPoint(( activity.getAverageDistance());
						badgeEarnedRepository.save(earnBatch);
					} else {


						earnBatch.setBadgeName(userBadge.get().getBatchName());
						earnBatch.setDescription(userBadge.get().getDescription());
						earnBatch.setImage(userBadge.get().getImage());
						earnBatch.setModule(module);
						earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
						earnBatch.setRangeTo(userBadge.get().getRangeTo());
						earnBatch.setUser(eventDto.getUser());
//						earnBatch.setEarnPoint(( activity.getAverageDistance());
						badgeEarnedRepository.save(earnBatch);
						
						


					}
				}
				pointHistory.setEarnPoint(currentPoint);
				pointBadge.setPoint(pointHistory);
				pointBadge.setBadge(earnBatch);
				resp.setExtras(pointBadge);
				resp.setErrorCode(RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
				resp.setMsg(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription());
				return resp;
			}
		}
		
//		Optional<EventType> eventType=eventTypeRepository.findById(eventDto.getEventType().getId());
//		Optional<Event> events=eventRepository.findById(eventDto.getCreatedBy());
//		 Optional<UserModel> user=userRepository.findById(eventDto.getUser().getId()); 
//		 
//		 if(eventType.isPresent() && events.isPresent() && user.isPresent())	
//			 alertService.sendEventAlert(eventType.get(),events.get(),user.get());
//		  System.out.println("====================================s");
//			 
		 
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO update(EventDto eventDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<Event> event = eventRepository.findById(eventDto.getId());
		if (event.isPresent()) {

			EventMapper.toEvent(eventDto, event.get());
			eventRepository.save(event.get());
//			
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
					RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No event found for given Id.");
		}
		return baseResponseDTO;
	}

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(eventRepository.getEventList());
	}

	@Override
	public BaseResponseDTO getById(Long id) throws Exception {
		return GenericUtils.wrapOrNotFound(eventRepository.getEvent(id));
	}

	@Override
	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception {
		Optional<Event> event = eventRepository.findById(id);
		if (event.isPresent()) {
			if (isSoftDelete) {
				event.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				eventRepository.save(event.get());
			} else {
				eventRepository.deleteById(id);
			}
		} else {
			throw new ResourceNotFoundException("No event found for given Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception {
		Optional<Event> event = eventRepository.findById(id);
		BaseResponseDTO responseDTO = null;
		if (event.isPresent()) {
			switch (statusId) {
			case 2:
				event.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
				break;
			case 0:
				event.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			case 1:
				event.get().setStatus(RecordStatusUtil.RECORD_ACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.ACTIVE.getErrorDescription(),
						RequestStatusUtil.ACTIVE.getErrorCode());
				break;
			default:
				event.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			}
			eventRepository.save(event.get());
		} else {
			throw new ResourceNotFoundException("No event found for given Id.");
		}
		return responseDTO;
	}

	@Override
	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<Event> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames = new HashMap<>();
		columnNames.put("user", "id");
		columnNames.put("eventType", "eventTypeName");

		return GenericUtils
				.wrapOrEmptyPagination(util.searchByColumns(paginationDTO, columnNames, Event.class, entityManager));
	}

	@Override
	public BaseResponseDTO getByUserId(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(eventRepository.findByUserId(userId));
	}

	@Override
	public BaseResponseDTO getEventMemberCount() throws Exception {
		return GenericUtils.wrapOrEmptyList(eventCountRepository.getEventMemberCount());
	}

	@Override
	public BaseResponseDTO getAllEventType() throws Exception {
		return GenericUtils.wrapOrEmptyList(eventTypeRepository.findAll());
	}

	@Override
	public BaseResponseDTO doFilterAndPaginate(Long userid, String gender, String age, String plans, String title,
			String eventType, Integer pageNo, Integer pageSize) throws Exception {
		List<Event> events = new ArrayList<Event>();
		StringBuffer sqlQuery = new StringBuffer(Constants.EVENT_FILTER_QUERY);

		if (null != userid && userid != 0) {
			sqlQuery.append("AND (e.user_id =" + userid + " or eu.user_id=" + userid + ") ");
		}
		if (null != gender && !gender.equalsIgnoreCase("null")) {
			String genders = "'" + gender.replace(",", "','") + "'";
			sqlQuery.append("AND e.gender_allowed IN(" + genders + ") ");
		}
		if (null != age && !age.equalsIgnoreCase("null")) {
			if (age.equalsIgnoreCase("above 18"))
				sqlQuery.append("AND e.age_allowed >= 18 ");
			else if (age.equalsIgnoreCase("below 18"))
				sqlQuery.append("AND e.age_allowed <= 18 ");
			else if (age.equalsIgnoreCase("above 60"))
				sqlQuery.append("AND e.age_allowed >= 60 ");
		}
		if (null != plans && !plans.equalsIgnoreCase("null")) {
			String plans1 = "'" + plans.replace(",", "','") + "'";
			sqlQuery.append("AND e.payment_method IN(" + plans1 + ") ");
		}
		if (null != eventType && !eventType.equalsIgnoreCase("null")) {
			String eventTypes = "'" + eventType.replace(",", "','") + "'";
			sqlQuery.append("AND et.event_type_name IN(" + eventTypes + ") ");
		}
		if (null != title && !title.equalsIgnoreCase("null"))
			sqlQuery.append("AND e.event_name like'%" + title + "%'");

		sqlQuery.append(" group by e.id order by e.updated_on desc");

		Query query = entityManager.createNativeQuery(sqlQuery.toString(), Event.class);

		if (pageSize != 0)
			query.setFirstResult(pageNo * pageSize).setMaxResults(pageSize);
		events = query.getResultList();
		if (pageSize != 0) {
			String countquery = Constants.BIKERENTAL_FILTER_QUERY_COUNT.replace("BIKERENTAL_FILTER_QUERY",
					sqlQuery.toString());
			Long count = ((Number) entityManager.createNativeQuery(countquery).getSingleResult()).longValue();
			Pageable pageable = PageRequest.of(pageNo, pageSize);
			Page<Event> page = new PageImpl<>(events, pageable, count);
			return GenericUtils.wrapOrEmptyPagination(page);
		} else {
			return GenericUtils.wrapOrEmptyList(events);
		}
	}

	@Override
	public BaseResponseDTO changeEventStatusById(EventDto eventDto) throws Exception {
		Optional<Event> event = eventRepository.findById(eventDto.getId());
		BaseResponseDTO responseDTO = null;
		if (event.isPresent()) {
			switch (eventDto.getStatus()) {
			case 2:
				event.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				event.get().setRejectReason(eventDto.getRejectReason());
				responseDTO = new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
				break;
			case 0:
				event.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			case 1:
				event.get().setStatus(RecordStatusUtil.RECORD_ACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.ACTIVE.getErrorDescription(),
						RequestStatusUtil.ACTIVE.getErrorCode());
				break;
			default:
				event.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			}
			eventRepository.save(event.get());

		} else {
			throw new ResourceNotFoundException("No event found for given Id.");
		}
		return responseDTO;
	}

}
