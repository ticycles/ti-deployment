package com.trackandtrail.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ChallengeUserDto;
import com.trackandtrail.dto.EventUserDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.model.challenge.ChallengeUser;
import com.trackandtrail.model.configuration.BadgeConfiguration;
import com.trackandtrail.model.configuration.BadgeEarned;
import com.trackandtrail.model.configuration.BadgeRewardConf;
import com.trackandtrail.model.configuration.PointsEarnedHistory;
import com.trackandtrail.model.event.EventUser;
import com.trackandtrail.model.user.UserGroupMember;
import com.trackandtrail.repository.BadgeConfigurationRepository;
import com.trackandtrail.repository.BadgeEarnedRepository;
import com.trackandtrail.repository.BadgeRewardConfRepository;
import com.trackandtrail.repository.ChallengeUserRepository;
import com.trackandtrail.repository.EventPointRepository;
import com.trackandtrail.repository.EventUserRepository;
import com.trackandtrail.repository.PointsEarnedHistoryRepository;
import com.trackandtrail.service.EventUserService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.MathUtil;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;
import com.trackandtrail.view.ChallengePoints;
import com.trackandtrail.view.EventPoint;

@Service
public class EventUserServiceImpl implements EventUserService {

	@Autowired
	EventUserRepository eventUserRepository;

	@Autowired
	PointsEarnedHistoryRepository PointsEarnedHistoryrepo;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepo;

	@Autowired
	EventPointRepository eventPointRepository;

	@Autowired
	BadgeRewardConfRepository badgeRepo;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepository;

	@Autowired
	BadgeConfigurationRepository badgeConfigRepo;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public BaseResponseDTO save(EventUserDto eventUserDto) throws Exception {
		eventUserRepository.save(objectMapper.convertValue(eventUserDto, EventUser.class));
		String module = "events";
		String moduleSlug = "events_joined_per_day";

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
		String todayDate = formatter.format(date);

		Optional<EventPoint> eventUser = eventPointRepository.findUserRole(eventUserDto.getUser().getId(), todayDate);
		if (eventUser.isPresent()) {
			Optional<BadgeRewardConf> badge = badgeRepo.findBadgeRewardConfModule("events");
			if (badge.isPresent()) {
				if (eventUser.get().getEventUserCount() > badge.get().getEventEnrolledPerDay()) {

					// remove
					PointsEarnedHistoryrepo.removeByUserIdAndModuleSlug(eventUserDto.getUser().getId(), module,
							moduleSlug, todayDate);

					Integer pt = MathUtil.calculateRewardPoints(badge.get().getEventEnrolledPerDay(),
							eventUser.get().getEventUserCount(), badge.get().getEventEnrolledPoints());

					PointsEarnedHistory pointHistory = new PointsEarnedHistory();

					pointHistory.setUser(eventUserDto.getUser());
					pointHistory.setModule(module);
					pointHistory.setModuleSlug(moduleSlug);
					pointHistory.setCreatedDate(new Date());
					pointHistory.setEarnPoint(pt);
					PointsEarnedHistoryrepo.save(pointHistory);

					EventPoint eventPoint = eventPointRepository.getOverallUserPoints(eventUserDto.getUser().getId());

					Optional<BadgeConfiguration> userBadge = badgeConfigRepo
							.getUserBadge(eventPoint.getEventUserCount(), "events");
					if (userBadge.isPresent()) {

						Optional<BadgeEarned> badgeEarned = badgeEarnedRepo.findRange(userBadge.get().getRangeFrom(),
								userBadge.get().getRangeTo(), eventUserDto.getUser().getId(),
								userBadge.get().getBatchName(), module);
						if (badgeEarned.isPresent()) {

							badgeEarnedRepo.deleteById(badgeEarned.get().getId());

							BadgeEarned earnBatch = new BadgeEarned();

							earnBatch.setBadgeName(userBadge.get().getBatchName());
							earnBatch.setDescription(userBadge.get().getDescription());
							earnBatch.setModule(module);
							earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
							earnBatch.setRangeTo(userBadge.get().getRangeTo());
//					earnBatch.setTitle(userBadge.getBadgeName());
							earnBatch.setUser(eventUserDto.getUser());
//					earnBatch.setEarnPoint(( activity.getAverageDistance());
							badgeEarnedRepository.save(earnBatch);
						} else {
							BadgeEarned earnBatch = new BadgeEarned();

							earnBatch.setBadgeName(userBadge.get().getBatchName());
							earnBatch.setDescription(userBadge.get().getDescription());
							earnBatch.setModule(module);
							earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
							earnBatch.setRangeTo(userBadge.get().getRangeTo());
//						earnBatch.setTitle(userBadge.getBadgeName());
							earnBatch.setUser(eventUserDto.getUser());
//						earnBatch.setEarnPoint(( activity.getAverageDistance());
							badgeEarnedRepository.save(earnBatch);
						}
					}
				}
			}

		} else {

			Optional<BadgeRewardConf> badge = badgeRepo.findBadgeRewardConfModule("events");
			if (badge.isPresent()) {
				if (eventUser.get().getEventUserCount() > badge.get().getEventEnrolledPerDay()) {

					// remove
					PointsEarnedHistoryrepo.removeByUserIdAndModuleSlug(eventUserDto.getUser().getId(), module,
							moduleSlug, todayDate);

					Integer pt = MathUtil.calculateRewardPoints(badge.get().getEventEnrolledPerDay(),
							eventUser.get().getEventUserCount(), badge.get().getEventEnrolledPoints());

					PointsEarnedHistory pointHistory = new PointsEarnedHistory();

					pointHistory.setUser(eventUserDto.getUser());
					pointHistory.setModule(module);
					pointHistory.setModuleSlug(moduleSlug);
					pointHistory.setCreatedDate(new Date());
					pointHistory.setEarnPoint(pt);
					PointsEarnedHistoryrepo.save(pointHistory);

					EventPoint eventPoint = eventPointRepository.getOverallUserPoints(eventUserDto.getUser().getId());

					Optional<BadgeConfiguration> userBadge = badgeConfigRepo
							.getUserBadge(eventPoint.getEventUserCount(), "events");
					if (userBadge.isPresent()) {
						Optional<BadgeEarned> badgeEarned = badgeEarnedRepo.findRange(userBadge.get().getRangeFrom(),
								userBadge.get().getRangeTo(), eventUserDto.getUser().getId(),
								userBadge.get().getBatchName(), module);
						if (badgeEarned.isPresent()) {

							badgeEarnedRepo.deleteById(badgeEarned.get().getId());

							BadgeEarned earnBatch = new BadgeEarned();

							earnBatch.setBadgeName(userBadge.get().getBatchName());
							earnBatch.setDescription(userBadge.get().getDescription());
							earnBatch.setModule(module);
							earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
							earnBatch.setRangeTo(userBadge.get().getRangeTo());
//					earnBatch.setTitle(userBadge.getBadgeName());
							earnBatch.setUser(eventUserDto.getUser());
//					earnBatch.setEarnPoint(( activity.getAverageDistance());
							badgeEarnedRepository.save(earnBatch);
						} else {
							BadgeEarned earnBatch = new BadgeEarned();

							earnBatch.setBadgeName(userBadge.get().getBatchName());
							earnBatch.setDescription(userBadge.get().getDescription());
							earnBatch.setModule(module);
							earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
							earnBatch.setRangeTo(userBadge.get().getRangeTo());
//						earnBatch.setTitle(userBadge.getBadgeName());
							earnBatch.setUser(eventUserDto.getUser());
//						earnBatch.setEarnPoint(( activity.getAverageDistance());
							badgeEarnedRepository.save(earnBatch);
						}
					}
				}
			}
		}
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO unjoin(Long eventId, Long userId) throws Exception {
		EventUser eventUser = eventUserRepository.findByEventIdAndUserId(eventId, userId);
		if (eventUser != null) {
			eventUserRepository.deleteById(eventUser.getId());
		} else {
			throw new ResourceNotFoundException("No user and event found for given  Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO getJoinInfo(Long id, Long eventId) throws Exception {
		return GenericUtils.wrapOrEmptyList(eventUserRepository.getJoinInfo(id, eventId));
	}

	@Override
	public BaseResponseDTO getOverAllParticipants(Long eventId) throws Exception {
		return GenericUtils.wrapOrEmptyList(eventUserRepository.getOverAllParticipants(eventId));
	}

	@Override
	public BaseResponseDTO getRegisteredFriends(Long userId, Long eventId) throws Exception {
		return GenericUtils.wrapOrEmptyList(eventUserRepository.getRegisteredFriends(userId, eventId));
	}

}
