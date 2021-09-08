package com.trackandtrail.service.impl.ecommercev2;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomReviewDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.ecommercev2.EcomReviewMapper;
import com.trackandtrail.model.ecommercev2.EcomReview;
import com.trackandtrail.repository.ecommercev2.EcomReviewRepository;
import com.trackandtrail.service.ecommercev2.EcomReviewService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class EcomReviewServiceImpl implements EcomReviewService {

	@Autowired
	private EcomReviewRepository ecomReviewRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public BaseResponseDTO save(EcomReviewDto ecomReviewDto) throws Exception {
		if (ecomReviewDto != null) {
			Optional<EcomReview> review = ecomReviewRepository.findByUserIdAndVariantId(ecomReviewDto.getUser().getId(),ecomReviewDto.getVariant().getId());
			if (!review.isPresent()) {
				ecomReviewRepository.save(objectMapper.convertValue(ecomReviewDto, EcomReview.class));
			} else {
				throw new ResourceNotFoundException("User already reviewed this product");
			}
		}
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO update(EcomReviewDto ecomReviewDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<EcomReview> ecomReview = ecomReviewRepository.findByUserIdAndVariantId(ecomReviewDto.getId(),ecomReviewDto.getVariant().getId());
		if (ecomReview.isPresent()) {

			EcomReviewMapper.toEcomReview(ecomReviewDto, ecomReview.get());
			ecomReviewRepository.save(ecomReview.get());
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
					RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No ratings found for given Id.");
		}
		return baseResponseDTO;
	}

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomReviewRepository.findAll());
	}

	@Override
	public BaseResponseDTO getRewiewAndComment(Long userId, Long variantId) throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomReviewRepository.getReviewAndComment(userId, variantId));
	}

	@Override
	public BaseResponseDTO getById(Long variantId) throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomReviewRepository.findByVariantId(variantId));
	}

}
