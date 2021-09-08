package com.trackandtrail.service.impl.ecommercev2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.PointsConfigurationDto;
import com.trackandtrail.dto.ecommercev2.EcomProductDto;
import com.trackandtrail.dto.ecommercev2.EcomTaxonomyDto;
import com.trackandtrail.dto.ecommercev2.EcommerceTaxonomyDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.ecommercev2.EcomProductMapper;
import com.trackandtrail.mapper.ecommercev2.EcomTaxonomyMapper;
import com.trackandtrail.model.configuration.PointsConfiguration;
import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.ecommercev2.EcomTaxonomy;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;
import com.trackandtrail.repository.ecommercev2.EcomTaxonomyRepository;
import com.trackandtrail.service.ecommercev2.EcomTaxonomyService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class EcomTaxonomyServiceImpl implements EcomTaxonomyService {
	
	
	@Autowired
	private EcomTaxonomyRepository ecomTaxonomyRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	@Override
	public BaseResponseDTO save(EcommerceTaxonomyDto ecommerceTaxonomyDto) throws Exception {
		ecomTaxonomyRepository.save(objectMapper.convertValue(ecommerceTaxonomyDto, EcomTaxonomy.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	
	
	@Override
	public BaseResponseDTO update(EcommerceTaxonomyDto ecommerceTaxonomyDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<EcomTaxonomy> ecomTaxonomy= ecomTaxonomyRepository.findByVid(ecommerceTaxonomyDto.getVid());
		if (ecomTaxonomy.isPresent()) {
			EcomTaxonomyMapper.toEcomTaxonomy(ecommerceTaxonomyDto, ecomTaxonomy.get());
			ecomTaxonomyRepository.save(ecomTaxonomy.get());
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No Taxonomy for given vid.");
		}
		return baseResponseDTO;
	}
	
	@Override
	public BaseResponseDTO deleteById(Long vid) throws Exception {
	      ecomTaxonomyRepository.findTaxonomyByVid(vid);
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}
	
	
//	@Override
//	public BaseResponseDTO deleteById(Long vid) throws Exception {
//		EcomTaxonomy ecomTaxonomy = ecomTaxonomyRepository.findByid(vid);
//		if (ecomTaxonomy != null) {
//			ecomTaxonomyRepository.deleteById(ecomTaxonomy.getId());
//		} else {
//			throw new ResourceNotFoundException("No Product found for given  Id.");
//		}
//		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
//				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
//	}


	
//	@Override
//	@Transactional
//	public BaseResponseDTO save(EcommerceTaxonomyDto ecommerceTaxonomyDto) throws Exception {
//
//
//		BaseResponseDTO resp = new BaseResponseDTO();
//
//		List<EcomTaxonomy> ecomTaxonomy = new ArrayList<EcomTaxonomy>();
//		ecommerceTaxonomyDto.getTerm().forEach(ecom -> {
//			EcomTaxonomy ecomTax = new EcomTaxonomy();
//
//		ecomTax.setTaxonomyName(ecom.getTermName());	
//		ecomTax.setTerm((List<EcomTaxonomyTerm>) ecom.getTaxonomy());
//		
//		
//		ecomTaxonomy.add(ecomTax);
//		});
//
//	
//		
//		ecomTaxonomyRepository.deleteAll();
//		ecomTaxonomyRepository.saveAll(ecomTaxonomy);
//		resp.setMsg("Ecom Taxonomy created successfully");
//		resp.setErrorCode(200);
//		return resp;
//	}
	@Override
	public BaseResponseDTO getAllTaxonomy() throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomTaxonomyRepository.findAll());
	}

	@Override
	public BaseResponseDTO getTaxonomyByCategoryId(Long categoryId) throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomTaxonomyRepository.findByCategoryId(categoryId));
	}

}
