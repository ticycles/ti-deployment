package com.trackandtrail.service.impl.ecommercev2;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomCartDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.model.ecommercev2.EcomCart;
import com.trackandtrail.repository.ecommercev2.EcomCartRepository;
import com.trackandtrail.service.ecommercev2.EcomCartService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class EcomAddToCartServiceImpl implements EcomCartService {
	@Autowired
	private EcomCartRepository addToCartRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public BaseResponseDTO save(EcomCartDto ecomCartDto) throws Exception {
		if (ecomCartDto != null) {
			Optional<EcomCart> cart = addToCartRepository.findByCard(ecomCartDto.getUser().getId(),
					ecomCartDto.getVariant().getId());
			if (!cart.isPresent()) {
				addToCartRepository.save(objectMapper.convertValue(ecomCartDto, EcomCart.class));

			} else {

				throw new ResourceNotFoundException("User already added this product to the cart");
			}
		}
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(addToCartRepository.findAll());
	}

	@Override
	public BaseResponseDTO getById(Long id) throws Exception {
		return GenericUtils.wrapOrEmptyList(addToCartRepository.findByUserId(id));
	}

	@Override
	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception {
		Optional<EcomCart> addToCart = addToCartRepository.findById(id);
		if (addToCart.isPresent()) {
			if (isSoftDelete) {
				addToCart.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				addToCartRepository.save(addToCart.get());
			} else {
				addToCartRepository.deleteById(id);
			}
		} else {
			throw new ResourceNotFoundException("No cart found for given Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

}
