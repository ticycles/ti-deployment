package com.trackandtrail.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.RideActivityCommentsDto;
import com.trackandtrail.dto.StockDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.RideActivityCommentMapper;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalBooking;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;
import com.trackandtrail.model.content.Content;
import com.trackandtrail.model.managedealer.ManageDealer;
import com.trackandtrail.model.rideActivityComment.RideActivityComment;
import com.trackandtrail.model.rideactivity.RideActivity;
import com.trackandtrail.model.stock.Stock;
import com.trackandtrail.model.user.UserGroupMember;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.RideActivityCommentsRepository;
import com.trackandtrail.repository.RideActivityRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.AlertService;
import com.trackandtrail.service.RideActivityCommentService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class RideActivityCommentServiceImpl implements RideActivityCommentService {

	@Autowired
	private RideActivityCommentsRepository rideActivityCommentsRepository;
	
	@Autowired
	RideActivityRepository rideActivityRepository;


	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	AlertService alertService;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public BaseResponseDTO save(RideActivityCommentsDto rideActivityCommentsDto) throws Exception {
		rideActivityCommentsRepository
				.save(objectMapper.convertValue(rideActivityCommentsDto, RideActivityComment.class));
	
		
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO update(RideActivityCommentsDto rideActivityCommentsDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		if (rideActivityCommentsDto.getUser().getId() != null) {

			Optional<RideActivityComment> rideActivityComments = rideActivityCommentsRepository
					.findByRideActivityIdAndUserId(rideActivityCommentsDto.getUser().getId(),
							(rideActivityCommentsDto.getRideActivity().getRideId()));
			if (rideActivityComments.isPresent()) {
				RideActivityCommentMapper.toRideActivityComment(rideActivityCommentsDto, rideActivityComments.get());
				rideActivityCommentsRepository.save(rideActivityComments.get());
				baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
				return baseResponseDTO;
			}

			else {
				rideActivityCommentsRepository
						.save(objectMapper.convertValue(rideActivityCommentsDto, RideActivityComment.class));
				
				
				Optional<RideActivity> rideActivity=rideActivityRepository.findById(rideActivityCommentsDto.getRideActivity().getRideId());
				Optional<UserModel> user=userRepository.findById(rideActivityCommentsDto.getUser().getId());

				if(rideActivity.isPresent() && user.isPresent())
					alertService.sendRideActivityAlert(rideActivity.get(),user.get());
				
				baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.CREATED_RESPONSE.getErrorCode());

			}

		}
		return baseResponseDTO;

	}

	@Override
	public BaseResponseDTO findByRideActivityRideIdAndUserId(Long id, Long rideId) throws Exception {
		return GenericUtils
				.wrapOrNotFound(rideActivityCommentsRepository.findAllByRideActivityRideIdAndUserId(id, rideId));
	}

	@Override
	public BaseResponseDTO getallCommentsByRideId(Long id) throws Exception {
		return GenericUtils.wrapOrNotFound(rideActivityCommentsRepository.findById(id));
	}

	@Override
	public BaseResponseDTO removeRideActivityByRideIdAndUserId(Long userId, Long rideId) throws Exception {
		RideActivityComment rideActivityComments = rideActivityCommentsRepository
				.findByRideActivityRideIdAndUserId(userId, rideId);
		if (rideActivityComments != null) {
			rideActivityCommentsRepository.deleteById(rideActivityComments.getId());
		} else {
			throw new ResourceNotFoundException("No id found for given Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO updateComment(RideActivityCommentsDto rideActivityCommentsDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		if (rideActivityCommentsDto.getUser().getId() != null) {

			Optional<RideActivityComment> rideActivityComments = rideActivityCommentsRepository
					.findByRideActivityIdAndUserId(rideActivityCommentsDto.getUser().getId(),
							(rideActivityCommentsDto.getRideActivity().getRideId()));
			if (rideActivityComments.isPresent()) {
				RideActivityCommentMapper.toRideActivityComment(rideActivityCommentsDto, rideActivityComments.get());
				rideActivityCommentsRepository.save(rideActivityComments.get());
				baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
				return baseResponseDTO;
			}

			else {
				rideActivityCommentsRepository
						.save(objectMapper.convertValue(rideActivityCommentsDto, RideActivityComment.class));
				
				Optional<RideActivity> rideActivity=rideActivityRepository.findById(rideActivityCommentsDto.getRideActivity().getRideId());
				Optional<UserModel> user=userRepository.findById(rideActivityCommentsDto.getUser().getId());

				if(rideActivity.isPresent() && user.isPresent())
					alertService.sendRideActivityAlert(rideActivity.get(),user.get());
				
				baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.CREATED_RESPONSE.getErrorCode());

			}

		}
		return baseResponseDTO;

	}
	
//	@Override
//	public BaseResponseDTO getAllByIsLike(Boolean isLike, Long rideId) throws Exception {
//		return GenericUtils.wrapOrNotFound(rideActivityCommentsRepository.getByIsLike(isLike, rideId));
//	}

	@Override
	public BaseResponseDTO getAllByIsLike(Long rideId) throws Exception {
		return GenericUtils.wrapOrEmptyList(rideActivityCommentsRepository.findByRideActivityRideIdByIsLike(rideId));
	}



	
	@Override
	public BaseResponseDTO getAllByComment(Long rideId) throws Exception {
		return GenericUtils.wrapOrEmptyList(rideActivityCommentsRepository.findByRideActivityRideIdByComment(rideId));
	}


	

}


//	@Override
//	public BaseResponseDTO unLikeByUserId(Long id, Long rideId) throws Exception {
//		RideActivityComments rideActivityComments = rideActivityCommentsRepository.deleteByUserIdAndRideActivityId(id, rideId);
//		if (rideActivityComments!=null) {
//			rideActivityCommentsRepository.deleteById(rideActivityComments.getUser().getId());
//		} else {
//			throw new ResourceNotFoundException("No Such unlike found for given Id.");
//		}
//		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
//				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
//	}
//	

//	@Override
//	public BaseResponseDTO deleteById(RideActivityCommentsDto rideActivityCommentsDto) throws Exception {
//		BaseResponseDTO baseResponseDTO = null;
//		if (rideActivityCommentsDto.getUser().getId()!= null) {
//
//			Optional<RideActivityComments> rideActivityComments = rideActivityCommentsRepository.deleteByRideActivityIdAndUserId(rideActivityCommentsDto.getUser().getId(), (rideActivityCommentsDto.getRideActivity().getRideId()));
//			if (rideActivityComments.isPresent()) {
//				RideActivityCommentMapper.toRideActivityComment(rideActivityCommentsDto, rideActivityComments.get());
//				rideActivityCommentsRepository.delete(rideActivityComments.get());
//				baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
//						RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
//				return baseResponseDTO;
//			}
//
//			else {
//				rideActivityCommentsRepository.delete(objectMapper.convertValue(rideActivityCommentsDto, RideActivityComments.class));
//				baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
//						RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
//
//			}
//
//		}
//		return baseResponseDTO;
//
//	}
