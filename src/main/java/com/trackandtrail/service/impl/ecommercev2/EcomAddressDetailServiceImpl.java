package com.trackandtrail.service.impl.ecommercev2;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomAddressDetailsDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.ecommercev2.EcomAddressDetailsMapper;
import com.trackandtrail.model.ecommercev2.EcomAddressDetails;
import com.trackandtrail.repository.ecommercev2.EcomAddressDetailsRepository;
import com.trackandtrail.service.ecommercev2.EcomAddressDetailService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class EcomAddressDetailServiceImpl implements EcomAddressDetailService {

	@Autowired
	private EcomAddressDetailsRepository addressDetailsRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public BaseResponseDTO save(EcomAddressDetailsDto ecomAddressDetailsDto) throws Exception {
		addressDetailsRepository.save(objectMapper.convertValue(ecomAddressDetailsDto, EcomAddressDetails.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO update(EcomAddressDetailsDto addressDetailsDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<EcomAddressDetails> addressDetails = addressDetailsRepository.findById(addressDetailsDto.getId());
		if (addressDetails.isPresent()) {

			EcomAddressDetailsMapper.toEcomAddressDetails(addressDetailsDto, addressDetails.get());

			addressDetailsRepository.save(addressDetails.get());

			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
					RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No AddressDetails found for given AddressDetailsId.");
		}
		return baseResponseDTO;
	}

	@Override
	public BaseResponseDTO getAllAddress() throws Exception {
		return GenericUtils.wrapOrEmptyList(addressDetailsRepository.findAll());

	}

	@Override
	public BaseResponseDTO getById(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(addressDetailsRepository.findByUserId(userId));

	}

}
