package com.trackandtrail.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.ContentDto;
import com.trackandtrail.dto.ContentRatingDto;
import com.trackandtrail.dto.PointBadgeDto;
import com.trackandtrail.dto.RideActivityCommentsDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.ContentMapper;
import com.trackandtrail.mapper.ContentRatingMapper;
import com.trackandtrail.mapper.RideActivityCommentMapper;
import com.trackandtrail.model.configuration.BadgeConfiguration;
import com.trackandtrail.model.configuration.BadgeEarned;
import com.trackandtrail.model.configuration.BadgeRewardConf;
import com.trackandtrail.model.configuration.PointsEarnedHistory;
import com.trackandtrail.model.content.Content;
import com.trackandtrail.model.content.ContentRating;
import com.trackandtrail.model.rideActivityComment.RideActivityComment;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.BadgeConfigurationRepository;
import com.trackandtrail.repository.BadgeEarnedRepository;
import com.trackandtrail.repository.BadgeRewardConfRepository;
import com.trackandtrail.repository.ContentPointRepository;
import com.trackandtrail.repository.ContentRatingRepository;
import com.trackandtrail.repository.ContentRepository;
import com.trackandtrail.repository.PointsEarnedHistoryRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.AlertService;
import com.trackandtrail.service.ContentService;
import com.trackandtrail.util.Constants;
import com.trackandtrail.util.GenericSearchandPaginationUtil;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.MathUtil;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;
import com.trackandtrail.view.ContentPoint;
import com.trackandtrail.view.RideActivityPoints;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	ContentRepository contentRepository;

	@Autowired
	ContentPointRepository contentPointRepo;

	@Autowired
	PointsEarnedHistoryRepository PointsEarnedHistoryrepo;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepo;

	@Autowired
	ContentRatingRepository contentRatingRepository;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepository;

	@Autowired
	BadgeRewardConfRepository badgeRepo;

	@Autowired
	BadgeConfigurationRepository badgeConfigRepo;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	EntityManager entityManager;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AlertService alertService;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public BaseResponseDTO createContent(ContentDto contentDto) throws Exception {

		BaseResponseDTO resp = new BaseResponseDTO();

		contentRepository.save(objectMapper.convertValue(contentDto, Content.class));

		String module = "blogs";
		String moduleSlug = "blogs_created_per_day";

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
		String todayDate = formatter.format(date);

		ContentPoint content = contentPointRepo.getContentUserCount(contentDto.getUser().getId(), todayDate);
		Optional<BadgeRewardConf> badge = badgeRepo.findBadgeRewardConfModule("blogs");
		if (badge.isPresent()) {
			if (content.getUserCountPerDay() > badge.get().getNoOfBlogsPerDay()) {

				Integer pt = MathUtil.calculateRewardPoints(badge.get().getNoOfBlogsPerDay(),
						content.getUserCountPerDay(), badge.get().getBlogPoints());

				Integer currentPoint = null;

				Optional<PointsEarnedHistory> ptHistory = PointsEarnedHistoryrepo
						.findMyEarnedPoints(contentDto.getUser().getId(), module, moduleSlug, todayDate);
				if (ptHistory.isPresent()) {

					PointsEarnedHistoryrepo.removeByUserIdAndModuleSlug(contentDto.getUser().getId(), module,
							moduleSlug, todayDate);
					currentPoint = ((pt) - (ptHistory.get().getEarnPoint()));
				} else {
					currentPoint = pt - badge.get().getBlogPoints();
				}

				PointsEarnedHistory pointHistory = new PointsEarnedHistory();

				pointHistory.setUser(contentDto.getUser());
				pointHistory.setModule(module);
				pointHistory.setModuleSlug(moduleSlug);
				pointHistory.setCreatedDate(new Date());
				pointHistory.setEarnPoint(pt);
				PointsEarnedHistoryrepo.save(pointHistory);

				PointBadgeDto pointBadge = new PointBadgeDto();

				BadgeEarned earnBatch = new BadgeEarned();

				Optional<ContentPoint> contentOverAllPoint = contentPointRepo
						.getOverallPoint(contentDto.getUser().getId());

				Optional<BadgeConfiguration> userBadge = badgeConfigRepo
						.getUserBadge(contentOverAllPoint.get().getUserCountPerDay(), "blogs");
				if (userBadge.isPresent()) {

					Optional<BadgeEarned> badgeEarned = badgeEarnedRepo.findRange(userBadge.get().getRangeFrom(),
							userBadge.get().getRangeTo(), contentDto.getUser().getId(), userBadge.get().getBatchName(),
							module);
					if (badgeEarned.isPresent()) {

						badgeEarnedRepo.deleteById(badgeEarned.get().getId());

						earnBatch.setBadgeName(userBadge.get().getBatchName());
						earnBatch.setDescription(userBadge.get().getDescription());
						earnBatch.setModule(module);
						earnBatch.setImage(userBadge.get().getImage());
						earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
						earnBatch.setRangeTo(userBadge.get().getRangeTo());
						earnBatch.setUser(contentDto.getUser());
//				earnBatch.setEarnPoint(( activity.getAverageDistance());
						badgeEarnedRepository.save(earnBatch);

					} else {

						earnBatch.setBadgeName(userBadge.get().getBatchName());
						earnBatch.setDescription(userBadge.get().getDescription());
						earnBatch.setModule(module);
						earnBatch.setImage(userBadge.get().getImage());
						earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
						earnBatch.setRangeTo(userBadge.get().getRangeTo());
						earnBatch.setUser(contentDto.getUser());
//				earnBatch.setEarnPoint(( activity.getAverageDistance());
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
	public BaseResponseDTO updateContent(ContentDto contentDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<Content> content = contentRepository.findById(contentDto.getId());
		if (content.isPresent()) {

			ContentMapper.toContent(contentDto, content.get());
			contentRepository.save(content.get());

			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
					RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {

			throw new ResourceNotFoundException("No content found for given Id.");
		}
		return baseResponseDTO;
	}

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(contentRepository.findAllContent());
	}

	@Override
	public BaseResponseDTO getByContentId(Long contentId) throws Exception {
		return GenericUtils.wrapOrNotFound(contentRepository.findById(contentId));
	}

	@Override
	public BaseResponseDTO deleteById(Long contentId, boolean isSoftDelete) throws Exception {
		Optional<Content> content = contentRepository.findById(contentId);
		if (content.isPresent()) {
			if (isSoftDelete) {
				content.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				contentRepository.save(content.get());
			} else {
				contentRepository.deleteById(contentId);
			}
		} else {
			throw new ResourceNotFoundException("No content found for given Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO changeStatusById(Long contentId, Integer statusId) throws Exception {
		Optional<Content> content = contentRepository.findById(contentId);
		BaseResponseDTO responseDTO = null;
		if (content.isPresent()) {
			switch (statusId) {
			case 2:
				content.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
				break;
			case 0:
				content.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			case 1:
				content.get().setStatus(RecordStatusUtil.RECORD_ACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.ACTIVE.getErrorDescription(),
						RequestStatusUtil.ACTIVE.getErrorCode());
				break;
			default:
				content.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			}
			contentRepository.save(content.get());
		} else {
			throw new ResourceNotFoundException("No Content found for given Id.");
		}
		return responseDTO;
	}

	@Override
	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<Content> util = new GenericSearchandPaginationUtil<>();
		return GenericUtils.wrapOrEmptyPagination(util.searchBySpecificColumns(paginationDTO,
				new HashMap<String, String>(), Content.class, entityManager));
	}

	@Override
	public BaseResponseDTO getRatingAndComment(Long userId, Long contentId) throws Exception {
		return GenericUtils.wrapOrEmptyList(contentRatingRepository.getRatingAndComment(userId, contentId));
	}

	@Override
	public BaseResponseDTO getRatingAndCommentByContentId(Long contentId) throws Exception {
		return GenericUtils.wrapOrEmptyList(contentRatingRepository.getRatingAndCommentByContentId(contentId));
	}

	@Override
	public int updateViewsCount(Long contentId) throws Exception {
		return contentRepository.updateViewsCount(contentId);
	}

//	@Override
//	public BaseResponseDTO addRatingAndComment(ContentRatingDto contentRatingDto) throws Exception {
//		if (contentRatingDto != null) {
//
//			Optional<ContentRating> contentRating = contentRatingRepository
//					.findBycontentrating(contentRatingDto.getUser().getId(), contentRatingDto.getContent().getId());
//			if (!contentRating.isPresent()) {
//				contentRatingRepository.save(objectMapper.convertValue(contentRatingDto, ContentRating.class));
//
//			} else {
//
//				BaseResponseDTO resp = new BaseResponseDTO();
//				resp.setMsg("User already added rating for this content");
//				return resp;
//			}
//
//		}
//
//		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
//				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
//	}

	@Override
	public BaseResponseDTO update(ContentRatingDto contentRatingDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		if (contentRatingDto.getUser().getId() != null) {

			Optional<ContentRating> contentRating = contentRatingRepository
					.findBycontentrating(contentRatingDto.getUser().getId(),
							(contentRatingDto.getContent().getId()));
			if (contentRating.isPresent()) {
				ContentRatingMapper.toContentRating(contentRatingDto, contentRating.get());
				contentRatingRepository.save(contentRating.get());
				baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
				return baseResponseDTO;
			}

			else {
				contentRatingRepository
						.save(objectMapper.convertValue(contentRatingDto, ContentRating.class));
				Optional<Content> contents=contentRepository.findById(contentRatingDto.getContent().getId());
				Optional<UserModel> user=userRepository.findById(contentRatingDto.getUser().getId());

				if(contents.isPresent() && user.isPresent())
					alertService.sendContentAlert(contents.get(),user.get());
				
				
				baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.CREATED_RESPONSE.getErrorCode());

			}

		}
		return baseResponseDTO;

	}

	
	@Override
	public BaseResponseDTO getContentByUserId(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(contentRepository.findByUserId(userId));
	}

	@Override
	public BaseResponseDTO doFilterAndPaginate(Long userid, String month, String title, Integer pageNo,
			Integer pageSize) throws Exception {
		List<Content> contents = new ArrayList<Content>();
		StringBuffer sqlQuery = new StringBuffer(Constants.CONTENT_FILTER_QUERY);

		if (null != userid && userid != 0) {
			sqlQuery.append("AND c.user_id =" + userid);
		}
		if (null != month && !month.equalsIgnoreCase("null")) {
			LocalDate date = LocalDate.now();
			if (month.equalsIgnoreCase("this month"))
				sqlQuery.append(" AND month(c.created_on)= " + date.getMonthValue());
			else if (month.equalsIgnoreCase("last month")) {
				int lastMonth = date.getMonthValue() - 1;
				sqlQuery.append(" AND month(c.created_on)= " + lastMonth);
			}
		}

		if (null != title && !title.equalsIgnoreCase("null"))
			sqlQuery.append(" AND (c.title like '%" + title + "%' or c.tags like '%" + title + "%' or c.content like '%"
					+ title + "%')");

		sqlQuery.append(" group by c.content_id order by c.updated_on desc");

		Query query = entityManager.createNativeQuery(sqlQuery.toString(), Content.class);
		query.setFirstResult(pageNo * pageSize).setMaxResults(pageSize);
		contents = query.getResultList();
		String countquery = Constants.BIKERENTAL_FILTER_QUERY_COUNT.replace("BIKERENTAL_FILTER_QUERY",
				sqlQuery.toString());
		Long count = ((Number) entityManager.createNativeQuery(countquery).getSingleResult()).longValue();
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Content> page = new PageImpl<>(contents, pageable, count);
		return GenericUtils.wrapOrEmptyPagination(page);
	}
	
	@Override
	public BaseResponseDTO getAllByIsLike(Long contentId) throws Exception {
		return GenericUtils.wrapOrEmptyList(contentRatingRepository.findByContentIdByIsLike(contentId));
	}

	@Override
	public BaseResponseDTO getAllByRatingStar(Long contentId) throws Exception {
		return GenericUtils.wrapOrEmptyList(contentRatingRepository.findByContentIdByRatingStar(contentId));
	}
	
	@Override
	public BaseResponseDTO getAllByComment(Long contentId) throws Exception {
		return GenericUtils.wrapOrEmptyList(contentRatingRepository.findByContentIdByComment(contentId));
	}




}
