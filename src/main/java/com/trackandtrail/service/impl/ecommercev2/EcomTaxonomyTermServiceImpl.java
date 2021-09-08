package com.trackandtrail.service.impl.ecommercev2;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcommerceTaxonomyTermDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.ecommercev2.EcomTaxonomyMapper;
import com.trackandtrail.mapper.ecommercev2.EcomTaxonomyTermMapper;
import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.ecommercev2.EcomTaxonomy;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;
import com.trackandtrail.repository.ecommercev2.EcomTaxonomyTermRepository;
import com.trackandtrail.service.ecommercev2.EcomTaxonomyTermService;
import com.trackandtrail.util.RequestStatusUtil;


@Service
public class EcomTaxonomyTermServiceImpl implements EcomTaxonomyTermService {

	
	
	@Autowired
	private EcomTaxonomyTermRepository ecomTaxonomyTermRepository;
	
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Override
	public BaseResponseDTO save(EcommerceTaxonomyTermDto ecommerceTaxonomyTermDto) throws Exception {
		ecomTaxonomyTermRepository.save(objectMapper.convertValue(ecommerceTaxonomyTermDto, EcomTaxonomyTerm.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}


	@Override
	public BaseResponseDTO update(EcommerceTaxonomyTermDto ecommerceTaxonomyTermDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<EcomTaxonomyTerm> ecomTaxonomyTerm= ecomTaxonomyTermRepository.findByTid(ecommerceTaxonomyTermDto.getTid());
		if (ecomTaxonomyTerm.isPresent()) {
			EcomTaxonomyTermMapper.toEcomTaxonomyTerm(ecommerceTaxonomyTermDto, ecomTaxonomyTerm.get());
			ecomTaxonomyTermRepository.save(ecomTaxonomyTerm.get());
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No Taxonomy Term for given vid.");
		}
		return baseResponseDTO;
	}


	@Override
	public BaseResponseDTO deleteById(Long tid) throws Exception {
		ecomTaxonomyTermRepository.findByTaxonmomyTermByTid(tid);
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}


}
