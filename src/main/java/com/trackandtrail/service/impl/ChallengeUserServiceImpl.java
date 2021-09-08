package com.trackandtrail.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ChallengeUserDto;
import com.trackandtrail.dto.PointBadgeDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.challenge.ChallengeUser;
import com.trackandtrail.model.configuration.BadgeConfiguration;
import com.trackandtrail.model.configuration.BadgeEarned;
import com.trackandtrail.model.configuration.BadgeRewardConf;
import com.trackandtrail.model.configuration.PointsEarnedHistory;
import com.trackandtrail.model.event.EventUser;
import com.trackandtrail.repository.BadgeConfigurationRepository;
import com.trackandtrail.repository.BadgeEarnedRepository;
import com.trackandtrail.repository.BadgeRewardConfRepository;
import com.trackandtrail.repository.ChallengeCountRepository;
import com.trackandtrail.repository.ChallengePointsRepository;
import com.trackandtrail.repository.ChallengeUserRepository;
import com.trackandtrail.repository.PointsEarnedHistoryRepository;
import com.trackandtrail.service.ChallengeUserService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.MathUtil;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;
import com.trackandtrail.view.ChallengePoints;

@Service
public class ChallengeUserServiceImpl implements ChallengeUserService {

	@Autowired
	ChallengeUserRepository challengeUserRepository;

	@Autowired
	ChallengeCountRepository challengeCountRepository;

	@Autowired
	ChallengePointsRepository challengePointsRepository;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepo;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepository;

	@Autowired
	BadgeConfigurationRepository badgeConfigRepo;

	@Autowired
	PointsEarnedHistoryRepository PointsEarnedHistoryrepo;

	@Autowired
	BadgeRewardConfRepository badgeRepo;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public BaseResponseDTO save(ChallengeUserDto challengeuserDto) throws Exception {

		BaseResponseDTO resp = new BaseResponseDTO();

		challengeUserRepository.save(objectMapper.convertValue(challengeuserDto, ChallengeUser.class));

		String module = "challenges";
		String moduleSlug = "challenges_joined_per_day";

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
		String todayDate = formatter.format(date);

		ChallengePoints challenge = challengePointsRepository
				.getJoinedChallengeUserCount(challengeuserDto.getUser().getId(), todayDate);

		Optional<BadgeRewardConf> badge = badgeRepo.findBadgeRewardConfModule("challenges");
		if (badge.isPresent()) {
			if (challenge.getChallengeUserCount() > badge.get().getChallengeEnrolledPerDay()) {

				Integer pt = MathUtil.calculateRewardPoints(badge.get().getChallengeEnrolledPerDay(),
						challenge.getChallengeUserCount(), badge.get().getChallengeEnrolledPoint());

				Integer currentPoint = null;

				Optional<PointsEarnedHistory> ptHistory = PointsEarnedHistoryrepo
						.findMyEarnedPoints(challengeuserDto.getUser().getId(), module, moduleSlug, todayDate);

				if (ptHistory.isPresent()) {

					// remove
					PointsEarnedHistoryrepo.removeByUserIdAndModuleSlug(challengeuserDto.getUser().getId(), module,
							moduleSlug, todayDate);

					currentPoint = ((pt) - (ptHistory.get().getEarnPoint()));
				} else {
					currentPoint = pt -  badge.get().getChallengeEnrolledPoint();
				}

				PointsEarnedHistory pointHistory = new PointsEarnedHistory();

				pointHistory.setUser(challengeuserDto.getUser());
				pointHistory.setModule(module);
				pointHistory.setModuleSlug(moduleSlug);
				pointHistory.setCreatedDate(new Date());
				pointHistory.setEarnPoint(pt);
				PointsEarnedHistoryrepo.save(pointHistory);

				PointBadgeDto pointBadge = new PointBadgeDto();

				BadgeEarned earnBatch = new BadgeEarned();

				Optional<ChallengePoints> challengePoint = challengePointsRepository
						.getOverallUserPoints(challengeuserDto.getUser().getId());

				Optional<BadgeConfiguration> userBadge = badgeConfigRepo
						.getUserBadge(challengePoint.get().getChallengeUserCount(), "challenges");
				if (userBadge.isPresent()) {

					Optional<BadgeEarned> badgeEarned = badgeEarnedRepo.findRange(userBadge.get().getRangeFrom(),
							userBadge.get().getRangeTo(), challengeuserDto.getUser().getId(),
							userBadge.get().getBatchName(), module);
					if (badgeEarned.isPresent()) {

						badgeEarnedRepo.deleteById(badgeEarned.get().getId());


						earnBatch.setBadgeName(userBadge.get().getBatchName());
						earnBatch.setDescription(userBadge.get().getDescription());
						earnBatch.setModule(module);
						earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
						earnBatch.setImage(userBadge.get().getImage());
						earnBatch.setRangeTo(userBadge.get().getRangeTo());
//				earnBatch.setTitle(userBadge.getBadgeName());
						earnBatch.setUser(challengeuserDto.getUser());
//				earnBatch.setEarnPoint(( activity.getAverageDistance());
						badgeEarnedRepository.save(earnBatch);
					} else {

						earnBatch.setBadgeName(userBadge.get().getBatchName());
						earnBatch.setDescription(userBadge.get().getDescription());
						earnBatch.setModule(module);
						earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
						earnBatch.setImage(userBadge.get().getImage());
						earnBatch.setRangeTo(userBadge.get().getRangeTo());
//						earnBatch.setTitle(userBadge.getBadgeName());
						earnBatch.setUser(challengeuserDto.getUser());
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
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO getJoinInfo(Long id, Long challengeId) throws Exception {
		return GenericUtils.wrapOrEmptyList(challengeUserRepository.getJoinInfo(id, challengeId));
	}

	@Override
	public BaseResponseDTO getOverAllParticipants(Long challengeId) throws Exception {
		return GenericUtils.wrapOrEmptyList(challengeUserRepository.getOverAllParticipants(challengeId));
	}

	@Override
	public BaseResponseDTO getRegisteredFriends(Long userId, Long challengeId) throws Exception {
		return GenericUtils.wrapOrEmptyList(challengeUserRepository.getRegisteredFriends(userId, challengeId));
	}

	@Override
	public BaseResponseDTO unjoin(Long challengeId, Long userId) throws Exception {
		ChallengeUser challengeUser = challengeUserRepository.findByChallengeIdAndUserId(challengeId, userId);
		if (challengeUser != null) {
			challengeUserRepository.deleteById(challengeUser.getId());
		} else {
			throw new ResourceNotFoundException("No challenge and user found for given  Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO getChallengeMemberCount() throws Exception {
		return GenericUtils.wrapOrEmptyList(challengeCountRepository.getChallengeMemberCount());
	}

	@Override
	public BaseResponseDTO getByUserId(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(challengeUserRepository.findByUserId(userId));
	}

}
