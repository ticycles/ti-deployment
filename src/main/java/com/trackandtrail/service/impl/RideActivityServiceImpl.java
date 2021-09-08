package com.trackandtrail.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.type.LocalDateTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.EventDto;
import com.trackandtrail.dto.PointBadgeDto;
import com.trackandtrail.dto.RideActivityDto;
import com.trackandtrail.dto.UserDto;
import com.trackandtrail.dto.request.BadgeConfigResponse;
import com.trackandtrail.model.configuration.BadgeConfiguration;
import com.trackandtrail.model.configuration.BadgeEarned;
import com.trackandtrail.model.configuration.BadgeRewardConf;
import com.trackandtrail.model.configuration.PointsEarnedHistory;
import com.trackandtrail.model.event.Event;
import com.trackandtrail.model.rideactivity.RideActivity;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.BadgeConfigurationRepository;
import com.trackandtrail.repository.BadgeEarnedRepository;
import com.trackandtrail.repository.BadgeRewardConfRepository;
import com.trackandtrail.repository.PointsEarnedHistoryRepository;
import com.trackandtrail.repository.RideActivityCommentsRepository;
import com.trackandtrail.repository.RideActivityRepository;
import com.trackandtrail.repository.RideActivityPointsRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.RideActivityService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.MathUtil;
import com.trackandtrail.util.RequestStatusUtil;
import com.trackandtrail.view.RideActivityPoints;

@Service
public class RideActivityServiceImpl implements RideActivityService {

	@Autowired
	RideActivityRepository rideActivityRepository;

	@Autowired
	RideActivityCommentsRepository rideActivityCommentsRepository;

	@Autowired
	RideActivityPointsRepository RideActivityPointsRepository;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepo;

	@Autowired
	BadgeRewardConfRepository badgeRepo;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepository;

	@Autowired
	BadgeConfigurationRepository badgeConfigRepo;

	@Autowired
	PointsEarnedHistoryRepository PointsEarnedHistoryrepo;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	UserRepository userRepo;

	@Override
	public BaseResponseDTO save(RideActivityDto rideActivityDto) throws Exception {
		BaseResponseDTO resp = new BaseResponseDTO();

		rideActivityRepository.save(objectMapper.convertValue(rideActivityDto, RideActivity.class));

		String module = "activity";
		String moduleSlug = "no_actvity_kms_per_day";

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
		String todayDate = formatter.format(date);

		RideActivityPoints ride = RideActivityPointsRepository
				.getTodayOverallDistance(rideActivityDto.getUser().getId(), todayDate);
		Optional<BadgeRewardConf> badge = badgeRepo.findBadgeRewardConfModule("activity");
		if (badge.isPresent()) {
			if (ride.getAverageDistance() > badge.get().getActivityDistance()) {
				Integer pt = MathUtil.calculateRewardPoint(badge.get().getActivityDistance(), ride.getAverageDistance(),
						badge.get().getActivityPoints());
				Integer currentPoint = null;
				Optional<PointsEarnedHistory> ptHistory = PointsEarnedHistoryrepo
						.findMyEarnedPoints(rideActivityDto.getUser().getId(), module, moduleSlug, todayDate);
				if (ptHistory.isPresent()) {
					// remove
					PointsEarnedHistoryrepo.removeByUserIdAndModuleSlug(rideActivityDto.getUser().getId(), module,
							moduleSlug, todayDate);
					currentPoint = ((pt) - (ptHistory.get().getEarnPoint()));
				} else {
					currentPoint = pt;
				}

				PointsEarnedHistory pointHistory = new PointsEarnedHistory();

				pointHistory.setUser(rideActivityDto.getUser());
				pointHistory.setModule(module);
				pointHistory.setModuleSlug(moduleSlug);
				pointHistory.setCreatedDate(new Date());
				pointHistory.setEarnPoint(pt);
				PointsEarnedHistoryrepo.save(pointHistory);

				PointBadgeDto pointBadge = new PointBadgeDto();

				BadgeEarned earnBatch = new BadgeEarned();

				Optional<RideActivityPoints> activity = RideActivityPointsRepository
						.getOverallDistance(rideActivityDto.getUser().getId());

				Optional<BadgeConfiguration> userBadge = badgeConfigRepo
						.getUserBadge(activity.get().getAverageDistance(), "activity");
				if (userBadge.isPresent()) {

					Optional<BadgeEarned> badgeEarned = badgeEarnedRepo.findRange(userBadge.get().getRangeFrom(),
							userBadge.get().getRangeTo(), rideActivityDto.getUser().getId(),
							userBadge.get().getBatchName(), module);
					if (badgeEarned.isPresent()) {

						badgeEarnedRepo.deleteById(badgeEarned.get().getId());

						earnBatch.setBadgeName(userBadge.get().getBatchName());
						earnBatch.setDescription(userBadge.get().getDescription());
						earnBatch.setModule(module);
						earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
						earnBatch.setRangeTo(userBadge.get().getRangeTo());
						earnBatch.setUser(rideActivityDto.getUser());
						earnBatch.setImage(userBadge.get().getImage());
//					earnBatch.setEarnPoint(( activity.getAverageDistance());
						badgeEarnedRepository.save(earnBatch);

					} else {

						earnBatch.setBadgeName(userBadge.get().getBatchName());
						earnBatch.setDescription(userBadge.get().getDescription());
						earnBatch.setModule(module);
						earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
						earnBatch.setRangeTo(userBadge.get().getRangeTo());
						earnBatch.setImage(userBadge.get().getImage());
						earnBatch.setUser(rideActivityDto.getUser());
//					    earnBatch.setEarnPoint((pt);
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
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO getByUserId(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(rideActivityRepository.findByUserId(userId));
	}

	@Override
	public BaseResponseDTO getByCurrentUserId() throws Exception {
		return GenericUtils.wrapOrEmptyList(rideActivityRepository.findByUserId(
				userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));
	}

	@Override
	public BaseResponseDTO getByRideId(Long rideId) throws Exception {
		return GenericUtils.wrapOrNotFound(rideActivityRepository.findById(rideId));
	}

//	@Override
//	public BaseResponseDTO getByRideId(Long rideId) throws Exception {
//		return GenericUtils.wrapOrEmptyList(rideActivityCommentsRepository.findByRideActivityRideId(rideId));
//	}

	@Override
	public BaseResponseDTO getByRideActivityUserId(Long followingUserId) throws Exception {
		return GenericUtils.wrapOrEmptyList(rideActivityRepository.findByFollowingUserId(followingUserId));
	}
	
	

}
