package com.trackandtrail.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.WishListDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.model.configuration.WishList;
import com.trackandtrail.repository.WishListRepository;
import com.trackandtrail.service.WishListService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;


@Service
public class WishListServiceImpl implements WishListService  {
	
	@Autowired
	private WishListRepository wishListRepository;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public BaseResponseDTO save(WishListDto wishListDto) throws Exception {
		wishListRepository.save(objectMapper.convertValue(wishListDto, WishList.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}
	
	

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(wishListRepository.findAll());
	}

	

	@Override
	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception {
		Optional<WishList> wishList = wishListRepository.findById(id);
		if (wishList.isPresent()) {
			if (isSoftDelete) {
				wishList.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				wishListRepository.save(wishList.get());
			} else {
				wishListRepository.deleteById(id);
			}
		} else {
			throw new ResourceNotFoundException("No WishList found for given Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}
	
}
