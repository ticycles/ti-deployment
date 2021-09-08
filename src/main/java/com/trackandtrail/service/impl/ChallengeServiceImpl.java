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
import com.trackandtrail.dto.ChallengeDto;
import com.trackandtrail.dto.PointBadgeDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.ChallengeMapper;
import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.challenge.ChallengeUser;
import com.trackandtrail.model.configuration.BadgeConfiguration;
import com.trackandtrail.model.configuration.BadgeEarned;
import com.trackandtrail.model.configuration.BadgeRewardConf;
import com.trackandtrail.model.configuration.PointsEarnedHistory;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.BadgeConfigurationRepository;
import com.trackandtrail.repository.BadgeEarnedRepository;
import com.trackandtrail.repository.BadgeRewardConfRepository;
import com.trackandtrail.repository.ChallengePointsRepository;
import com.trackandtrail.repository.ChallengeRepository;
import com.trackandtrail.repository.ChallengeUserRepository;
import com.trackandtrail.repository.PointsEarnedHistoryRepository;
import com.trackandtrail.repository.RideActivityPointsRepository;
import com.trackandtrail.repository.RideTypeRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.AlertService;
import com.trackandtrail.service.ChallengeService;
import com.trackandtrail.util.Constants;
import com.trackandtrail.util.GenericSearchandPaginationUtil;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.MathUtil;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;
import com.trackandtrail.view.ChallengePoints;
import com.trackandtrail.view.RideActivityPoints;

@Service
public class ChallengeServiceImpl implements ChallengeService {

	@Autowired
	ChallengeRepository challengeRepository;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepository;

	@Autowired
	BadgeConfigurationRepository badgeConfigRepo;

	@Autowired
	RideTypeRepository rideTypeRepository;

	@Autowired
	ChallengePointsRepository challengePointsRepository;

	@Autowired
	ChallengeUserRepository challengeUserRepository;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepo;

	@Autowired
	PointsEarnedHistoryRepository PointsEarnedHistoryrepo;

	@Autowired
	BadgeRewardConfRepository badgeRepo;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	EntityManager entityManager;
	
	@Autowired 
	AlertService alertService;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public BaseResponseDTO save(ChallengeDto challengeDto) throws Exception {

		BaseResponseDTO resp = new BaseResponseDTO();

		challengeRepository.save(objectMapper.convertValue(challengeDto, Challenge.class));
		String module = "challenges";
		String moduleSlug = "challenges_created_per_day";

		
//		   Optional<Challenge> challenges=challengeRepository.findById(challengeDto.getUser().getCreatedBy());
//		   Optional<UserModel> user=userRepository.findById(challengeDto.getUser().getId());
	        
//	        if(challenges.isPresent() && user.isPresent())
//	        	alertService.sendChallengeAlert(challenges.get(),user.get());
	        
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
		String todayDate = formatter.format(date);

		ChallengePoints challenge = challengePointsRepository.getChallengeUserCount(challengeDto.getUser().getId(),
				todayDate);

		Optional<BadgeRewardConf> badge = badgeRepo.findBadgeRewardConfModule("challenges");
		if (badge.isPresent()) {
			if (challenge.getChallengeUserCount() > badge.get().getNoOfChallengesPerDay()) {

				Integer pt = MathUtil.calculateRewardPoints(badge.get().getNoOfChallengesPerDay(),
						challenge.getChallengeUserCount(), badge.get().getChallengePoint());

				Integer currentPoint = null;

				Optional<PointsEarnedHistory> ptHistory = PointsEarnedHistoryrepo
						.findMyEarnedPoints(challengeDto.getUser().getId(), module, moduleSlug, todayDate);
				if (ptHistory.isPresent()) {

					// remove
					PointsEarnedHistoryrepo.removeByUserIdAndModuleSlug(challengeDto.getUser().getId(), module,
							moduleSlug, todayDate);

					currentPoint = ((pt) - (ptHistory.get().getEarnPoint()));
				} else {
					currentPoint = pt - badge.get().getChallengePoint();
				}

				PointsEarnedHistory pointHistory = new PointsEarnedHistory();

				pointHistory.setUser(challengeDto.getUser());
				pointHistory.setModule(module);
				pointHistory.setModuleSlug(moduleSlug);
				pointHistory.setCreatedDate(new Date());
				pointHistory.setEarnPoint(pt);
				PointsEarnedHistoryrepo.save(pointHistory);

				PointBadgeDto pointBadge = new PointBadgeDto();
				
				BadgeEarned earnBatch = new BadgeEarned();


				Optional<ChallengePoints> challengePoint = challengePointsRepository
						.getOverallPoints(challengeDto.getUser().getId());

				Optional<BadgeConfiguration> userBadge = badgeConfigRepo
						.getUserBadge(challengePoint.get().getChallengeUserCount(), "challenges");
				if (userBadge.isPresent()) {

					Optional<BadgeEarned> badgeEarned = badgeEarnedRepo.findRange(userBadge.get().getRangeFrom(),
							userBadge.get().getRangeTo(), challengeDto.getUser().getId(),
							userBadge.get().getBatchName(), module);
					if (badgeEarned.isPresent()) {

						badgeEarnedRepo.deleteById(badgeEarned.get().getId());


						earnBatch.setBadgeName(userBadge.get().getBatchName());
						earnBatch.setDescription(userBadge.get().getDescription());
						earnBatch.setModule(module);
						earnBatch.setImage(userBadge.get().getImage());
						earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
						earnBatch.setRangeTo(userBadge.get().getRangeTo());
						earnBatch.setUser(challengeDto.getUser());
//				earnBatch.setEarnPoint(pt);
						badgeEarnedRepository.save(earnBatch);
					} else {

						earnBatch.setBadgeName(userBadge.get().getBatchName());
						earnBatch.setDescription(userBadge.get().getDescription());
						earnBatch.setModule(module);
						earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
						earnBatch.setImage(userBadge.get().getImage());
						earnBatch.setRangeTo(userBadge.get().getRangeTo());
						earnBatch.setUser(challengeDto.getUser());
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
//		   Optional<Challenge> challenges=challengeRepository.findById(challengeDto.getUser().getCreatedBy());
		   Optional<UserModel> user=userRepository.findById(challengeDto.getUser().getId());
//	        
//	        if(challenges.isPresent() && user.isPresent())
//       	alertService.sendChallengeAlert(challenges.get(),user.get());

		   if(user.isPresent())
	        	alertService.sendChallengeAlert(user.get());
	        
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());

	}

	@Override
	public BaseResponseDTO update(ChallengeDto challengeDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<Challenge> challenge = challengeRepository.findById(challengeDto.getId());
		if (challenge.isPresent()) {
			ChallengeMapper.toChallenge(challengeDto, challenge.get());
			challengeRepository.save(challenge.get());
//			userDTO.setPassword(user.get().getPassword());
//			UserModel userM = user.get();
//			UserMapper.toUserEntityUpdate(userDTO, userM);
//			userRepository.save(userM);
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
					RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No challenge found for given Id.");
		}
		return baseResponseDTO;
	}

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(challengeRepository.findAllChallenge());
	}

	@Override
	public BaseResponseDTO getById(Long id) throws Exception {
		return GenericUtils.wrapOrNotFound(challengeRepository.findById(id));
	}

	@Override
	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception {
		Optional<ChallengeUser> challenge = challengeUserRepository.findById(id);
		if (challenge.isPresent()) {
			if (isSoftDelete) {
				challenge.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				challengeUserRepository.save(challenge.get());
			} else {
				challengeUserRepository.deleteById(id);
			}
		} else {
			throw new ResourceNotFoundException("No challenge found for given userId.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception {
		Optional<Challenge> challenge = challengeRepository.findById(id);
		BaseResponseDTO responseDTO = null;
		if (challenge.isPresent()) {
			switch (statusId) {
			case 2:
				challenge.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
				break;
			case 0:
				challenge.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			case 1:
				challenge.get().setStatus(RecordStatusUtil.RECORD_ACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.ACTIVE.getErrorDescription(),
						RequestStatusUtil.ACTIVE.getErrorCode());
				break;
			default:
				challenge.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			}
			challengeRepository.save(challenge.get());
		} else {
			throw new ResourceNotFoundException("No Challenge found for given Id.");
		}
		return responseDTO;
	}

	@Override
	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<Challenge> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames = new HashMap<>();
		columnNames.put("user", "username");
		columnNames.put("rideType", "rideName");

		return GenericUtils.wrapOrEmptyPagination(
				util.searchBySpecificColumns(paginationDTO, columnNames, Challenge.class, entityManager));
	}

	@Override
	public BaseResponseDTO getAllRideType() throws Exception {
		return GenericUtils.wrapOrEmptyList(rideTypeRepository.findAll());
	}

	@Override
	public BaseResponseDTO getByUserId(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(challengeRepository.findByUserId(userId));
	}

	@Override
	public BaseResponseDTO doFilterAndPaginate(Long userid, String gender, String age, String privacy, String search,
			Integer pageNo, Integer pageSize) throws Exception {
		List<Challenge> challenges = new ArrayList<Challenge>();
		StringBuffer sqlQuery = new StringBuffer(Constants.CHALLENGE_FILTER_QUERY);

		if (null != userid && userid != 0) {
			sqlQuery.append("AND (ch.user_id=" + userid + " or chu.user_id=" + userid + ") ");
		}
		if (null != gender && !gender.equalsIgnoreCase("null")) {
			String genders = "'" + gender.replace(",", "','") + "'";
			sqlQuery.append("AND ch.gender_allowed IN(" + genders + ") ");
		}
		if (null != age && !age.equalsIgnoreCase("null")) {
			if (age.equalsIgnoreCase("above 18"))
				sqlQuery.append("AND ch.age_allowed >= 18 ");
			else if (age.equalsIgnoreCase("below 18"))
				sqlQuery.append("AND ch.age_allowed <= 18 ");
			else if (age.equalsIgnoreCase("above 60"))
				sqlQuery.append("AND ch.age_allowed >= 60 ");
		}
		if (null != privacy && !privacy.equalsIgnoreCase("null")) {
			String privacies = "'" + privacy.replace(",", "','") + "'";
			sqlQuery.append(" AND rt.ride_name IN(" + privacies + ") ");
		}
		if (null != search && !search.equalsIgnoreCase("null"))
			sqlQuery.append("AND ch.name like'%" + search + "%'");

		sqlQuery.append(" group by ch.challenge_id order by ch.updated_on desc");

		Query query = entityManager.createNativeQuery(sqlQuery.toString(), Challenge.class);
		query.setFirstResult(pageNo * pageSize).setMaxResults(pageSize);
		challenges = query.getResultList();
		String countquery = Constants.BIKERENTAL_FILTER_QUERY_COUNT.replace("BIKERENTAL_FILTER_QUERY",
				sqlQuery.toString());
		Long count = ((Number) entityManager.createNativeQuery(countquery).getSingleResult()).longValue();
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Challenge> page = new PageImpl<>(challenges, pageable, count);
		return GenericUtils.wrapOrEmptyPagination(page);
	}

}
