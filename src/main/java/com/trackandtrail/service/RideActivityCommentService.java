package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.RideActivityCommentsDto;
import com.trackandtrail.dto.StockDto;
import com.trackandtrail.model.userpreference.UserPreference;
import com.trackandtrail.util.GenericUtils;

public interface RideActivityCommentService {

	public BaseResponseDTO save(RideActivityCommentsDto rideActivityCommentsDto) throws Exception;

	public BaseResponseDTO update(RideActivityCommentsDto rideActivityCommentsDto) throws Exception;

	public BaseResponseDTO findByRideActivityRideIdAndUserId(Long userId, Long rideId) throws Exception;

	public BaseResponseDTO getallCommentsByRideId(Long id) throws Exception;

	public BaseResponseDTO removeRideActivityByRideIdAndUserId(Long userId, Long rideId) throws Exception;

	public BaseResponseDTO updateComment(RideActivityCommentsDto rideActivityCommentsDto) throws Exception;

	
	public BaseResponseDTO getAllByIsLike(Long rideId) throws Exception;

	public BaseResponseDTO getAllByComment(Long rideId) throws Exception;
	



	
//	public BaseResponseDTO deleteById(RideActivityCommentsDto rideActivityCommentsDto) throws Exception;

//	public BaseResponseDTO findAllByRideActivityIdAndUserId(Long rideId,Long userId) throws Exception;

}
