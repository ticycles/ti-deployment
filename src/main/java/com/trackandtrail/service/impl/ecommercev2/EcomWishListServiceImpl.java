package com.trackandtrail.service.impl.ecommercev2;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.WishListDto;
import com.trackandtrail.dto.ecommercev2.EcomWishListDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.model.configuration.WishList;
import com.trackandtrail.model.ecommercev2.EcomWishList;
import com.trackandtrail.repository.WishListRepository;
import com.trackandtrail.repository.ecommercev2.EcomWishListRepository;
import com.trackandtrail.service.ecommercev2.EcomWishListService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class EcomWishListServiceImpl implements EcomWishListService {

	@Autowired
	private EcomWishListRepository wishListRepository;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public BaseResponseDTO save(EcomWishListDto ecomWishListDto) throws Exception {
		if (ecomWishListDto != null) {
			Optional<EcomWishList> wishList = wishListRepository.findByWishListId(ecomWishListDto.getUser().getId(),
					ecomWishListDto.getVariant().getId());
			if(!wishList.isPresent()) {
				wishListRepository.save(objectMapper.convertValue(ecomWishListDto, EcomWishList.class));
				
			}else {
				throw new ResourceNotFoundException("User already Wish Listed this product");

		}
		}
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(wishListRepository.findAll());
	}

	@Override
	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception {
		Optional<EcomWishList> wishList = wishListRepository.findById(id);
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

	@Override
	public BaseResponseDTO getByUserId(Long userId) throws Exception {

		return GenericUtils.wrapOrEmptyList(wishListRepository.findByUserId(userId));
	}

}
