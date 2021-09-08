package com.trackandtrail.service.impl.ecommercev2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomProductDto;
import com.trackandtrail.dto.ecommercev2.EcomProductVariantDto;
import com.trackandtrail.dto.ecommercev2.EcomProductVariantSpecificationDto;
import com.trackandtrail.dto.ecommercev2.EcomTaxonomyDto;
import com.trackandtrail.dto.ecommercev2.EcomTaxonomyTermDto;
import com.trackandtrail.dto.ecommercev2.EcomTaxonomyTermRelationDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.ecommercev2.EcomProductMapper;
import com.trackandtrail.mapper.ecommercev2.EcomProductVariantMapper;
import com.trackandtrail.mapper.ecommercev2.EcomVariantSpecMapper;
import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;
import com.trackandtrail.model.event.EventUser;
import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.ecommercev2.EcomProductSpecification;
import com.trackandtrail.repository.ecommercev2.EcomProductRepository;
import com.trackandtrail.repository.ecommercev2.EcomProductVariantRepository;
import com.trackandtrail.repository.ecommercev2.EcomProductVariantSpecRepo;
import com.trackandtrail.repository.ecommercev2.EcomTaxonomyRelationshipRepo;
import com.trackandtrail.repository.ecommercev2.EcomTaxonomyRepository;
import com.trackandtrail.repository.ecommercev2.EcomTaxonomyTermRepository;
import com.trackandtrail.service.ecommercev2.EcomProductService;
import com.trackandtrail.util.Constants;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class EcomProductServiceImpl implements EcomProductService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private EcomProductVariantRepository ecomProductVariantRepo;

	@Autowired
	EcomProductVariantRepository evRepo;
	
	@Autowired
	EcomProductRepository ecomProductRepository;

	@Autowired
	EcomProductVariantSpecRepo specRepo;

	@Autowired
	EcomTaxonomyRelationshipRepo etrRpo;

	@Autowired
	EcomTaxonomyRepository ecomTaxonomyRepository;

	@Autowired
	EcomTaxonomyTermRepository ecomTaxonomyTermRepository;
	
	@Autowired
	ObjectMapper objectMapper;

	@Override
	public BaseResponseDTO listFilterAndPaginate(
			@RequestParam Long categoryId,
			@RequestParam Long productType,
			@RequestParam Double priceRangeMin,
			@RequestParam Double priceRangeMax,
			@RequestParam List<String> termIds, 
			@RequestParam List<String> colorIds,
			@RequestParam List<String> sizesIds,  
			@RequestParam Integer pageNo,
			@RequestParam Integer pageSize, 
			@RequestParam String sortBy
			
			) throws Exception {

		List<EcomProductVariant> bikeRentalProducts = new ArrayList<EcomProductVariant>();
	
		// Price Range 
		StringBuffer priceRangeQuery = new StringBuffer(Constants.ECOM_PRICE_RANGE_QUERY);
		
		if(null != categoryId && categoryId > 0) {
			priceRangeQuery.append(" AND p.category_id = " +  categoryId);
			}
				
		
		if(null != categoryId &&   categoryId == 1 && null != productType && productType > 0) {
		priceRangeQuery.append(" AND v.product_type_term_id = " +  productType);
		}
		
		
		// Filter Query
		
		StringBuffer sqlQuery = new StringBuffer(Constants.ECOM_PROD_FILTER_QUERY);
		
		
		
		if(null != categoryId && categoryId > 0) {
			sqlQuery.append(" AND p.category_id = " +  categoryId);
			}
		
		
		if (null != productType && productType > 0) {			
			sqlQuery.append(" AND v.product_type_term_id = " + productType + " ");
		}
		
		
		if (null != sizesIds && ! sizesIds.isEmpty() ) {
			String sizes = sizesIds.stream().collect(Collectors.joining(","));
			sqlQuery.append(" AND v.size_term_id IN (" + sizes + ") ");
		}
				
		if (null != colorIds && ! colorIds.isEmpty() ) {
			String colors = colorIds.stream().collect(Collectors.joining(","));
			sqlQuery.append(" AND v.color_term_id IN (" + colors + ") ");
		}
		
		
		if (null != termIds && ! termIds.isEmpty() ) {
			String terms = termIds.stream().collect(Collectors.joining(","));
			sqlQuery.append(" AND r.taxonomy_term_id IN (" + terms + ") ");
		}
		

		if (null != priceRangeMin && priceRangeMin > -1 && null != priceRangeMax && priceRangeMax > -1 ) {
			sqlQuery.append(
					"AND v.price between " + priceRangeMin + " AND " + priceRangeMax + " ");
	}

		
		sqlQuery.append(" GROUP BY v.variant_id ");
		
		if (null != sortBy && !sortBy.equalsIgnoreCase("null")) {
			if (sortBy.equalsIgnoreCase("lowtohigh"))
				sqlQuery.append(" ORDER BY v.price ASC ");
			if (sortBy.equalsIgnoreCase("hightolow"))
				sqlQuery.append(" ORDER BY v.price DESC ");
			if (sortBy.contains("newestfirst"))
				sqlQuery.append(" ORDER BY v.created_on ASC ");
		} else {
			sqlQuery.append(" ORDER BY v.updated_on DESC ");
		}

		
		
		Query query = entityManager.createNativeQuery(sqlQuery.toString(), EcomProductVariant.class);
		query.setFirstResult(pageNo * pageSize).setMaxResults(pageSize);
		bikeRentalProducts = query.getResultList();
		
		
		
		
				
//		 Query pRs = entityManager.createNativeQuery(priceRangeQuery.toString(), PriceRangeDto.class);
//				 
//		 PriceRangeDto pr = (PriceRangeDto) pRs.getSingleResult();
		 
		 
		String countquery = Constants.BIKERENTAL_FILTER_QUERY_COUNT.replace("BIKERENTAL_FILTER_QUERY",
				sqlQuery.toString());
		
		Long count = ((Number) entityManager.createNativeQuery(countquery).getSingleResult()).longValue();
				
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<EcomProductVariant> page = new PageImpl<>(bikeRentalProducts, pageable, count);
		return GenericUtils.wrapOrEmptyPagination(page);
	}

	@Override
	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception {
		Optional<EcomProductVariant> productVariant = ecomProductVariantRepo.findById(id);
		BaseResponseDTO responseDTO = null;
		if (productVariant.isPresent()) {
			switch (statusId) {
			case 2:
				productVariant.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				responseDTO = new BaseResponseDTO();
				break;
			case 0:
				productVariant.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			case 1:
				productVariant.get().setStatus(RecordStatusUtil.RECORD_ACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.ACTIVE.getErrorDescription(),
						RequestStatusUtil.ACTIVE.getErrorCode());
				break;
			default:
				productVariant.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			}
			ecomProductVariantRepo.save(productVariant.get());
		} else {
			throw new ResourceNotFoundException("No product found for given Id.");
		}
		return responseDTO;
	}

	@Override
	public BaseResponseDTO getVariantById(Long variantId) throws Exception {
		EcomProductVariantDto dto = new EcomProductVariantDto();

		Optional<EcomProductVariant> v = evRepo.findById(variantId);
		EcomProductVariantMapper.toDto(v.get(), dto);

		List<EcomProductSpecification> prdSpecList = specRepo.findByProduct(v.get().getProduct());
		List<EcomProductVariantSpecificationDto> ls = new ArrayList<>();

		prdSpecList.stream().forEach(spec -> {

			EcomProductVariantSpecificationDto specDto = new EcomProductVariantSpecificationDto();
			EcomVariantSpecMapper.toDto(spec, specDto);
			ls.add(specDto);

		});

		dto.setVariantSpec(ls);

		Set<EcomTaxonomyTerm> colorTerms = new HashSet<EcomTaxonomyTerm>();
		Set<EcomTaxonomyTerm> sizeTerms = new HashSet<EcomTaxonomyTerm>();

		List<EcomProductVariant> variants = evRepo.findByProductId(v.get().getProduct().getId());

		variants.forEach(variantItem -> {
			sizeTerms.add(variantItem.getSizeTermId());
			colorTerms.add(variantItem.getColorTermId());
		});

		dto.setColors(colorTerms.stream().collect(Collectors.toList()));
		dto.setSize(sizeTerms.stream().collect(Collectors.toList()));

		List<EcomTaxonomyDto> taxonomies = ecomTaxonomyRepository.findTaxonomyByVariantId(variantId);
		List<EcomTaxonomyTermDto> terms = ecomTaxonomyTermRepository.findTaxonomyTermByVariantId(variantId);

		List<EcomTaxonomyTermRelationDto> taxTermRelDto = new ArrayList<EcomTaxonomyTermRelationDto>();

		taxonomies.stream().forEach(taxn -> {
			EcomTaxonomyTermRelationDto rel = new EcomTaxonomyTermRelationDto();
			rel.setId(taxn.getId());
			rel.setTaxonomyName(taxn.getTaxonomyName());

			List<EcomTaxonomyTermDto> newTerms = new ArrayList<EcomTaxonomyTermDto>();

			terms.stream().forEach(term -> {
				if (term.getTaxonomyId() == taxn.getId()) {

					newTerms.add(term);
				}
			});

			rel.setTerm(newTerms);
			
			
			taxTermRelDto.add(rel);

		});

		dto.setTaxonomies(taxTermRelDto);

		BaseResponseDTO resp = new BaseResponseDTO();
		resp.setMsg("Listed successfully ");
		resp.setResponseContent(dto);

		return resp;
	}

	@Override
	public BaseResponseDTO getAllProducts(Long categoryId) throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomProductVariantRepo.findByEcomProductByCategoryId(categoryId));

	}
	
	
	@Override
	public BaseResponseDTO getAvailableProductList(Long categoryId) throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomProductVariantRepo.findAvailableEcomProductByCategoryId(categoryId));

	}

	@Override
	public BaseResponseDTO getProductByVariantId(Long variantId) throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomProductVariantRepo.findByVariantId(variantId));

	}

	@Override
	public BaseResponseDTO getVariantByProductIdColorIdSizeId(Long productId, Long colorId, Long sizeId)
			throws Exception {

		BaseResponseDTO response = new BaseResponseDTO();
		
		EcomProductVariantDto dto = new EcomProductVariantDto();

		Optional<EcomProductVariant> v = Optional.ofNullable(
				ecomProductVariantRepo.findByProductIdAndColorTermIdAndSizeTermId(productId, colorId, sizeId));

		if (v.isPresent()) {
			Long variantId = v.isPresent() ? v.get().getId() : 0;
			EcomProductVariantMapper.toDto(v.get(), dto);
			List<EcomProductSpecification> prdSpecList = specRepo.findByProduct(v.get().getProduct());
			List<EcomProductVariantSpecificationDto> ls = new ArrayList<>();

			prdSpecList.stream().forEach(spec -> {

				EcomProductVariantSpecificationDto specDto = new EcomProductVariantSpecificationDto();
				EcomVariantSpecMapper.toDto(spec, specDto);
				ls.add(specDto);

			});

			dto.setVariantSpec(ls);

			Set<EcomTaxonomyTerm> colorTerms = new HashSet<EcomTaxonomyTerm>();
			Set<EcomTaxonomyTerm> sizeTerms = new HashSet<EcomTaxonomyTerm>();

			List<EcomProductVariant> variants = evRepo.findByProductId(v.get().getProduct().getId());

			variants.forEach(variantItem -> {
				sizeTerms.add(variantItem.getSizeTermId());
				colorTerms.add(variantItem.getColorTermId());
			});

			dto.setColors(colorTerms.stream().collect(Collectors.toList()));
			dto.setSize(sizeTerms.stream().collect(Collectors.toList()));

			List<EcomTaxonomyDto> taxonomies = ecomTaxonomyRepository.findTaxonomyByVariantId(variantId);
			List<EcomTaxonomyTermDto> terms = ecomTaxonomyTermRepository.findTaxonomyTermByVariantId(variantId);

			List<EcomTaxonomyTermRelationDto> taxTermRelDto = new ArrayList<EcomTaxonomyTermRelationDto>();

			taxonomies.stream().forEach(taxn -> {
				EcomTaxonomyTermRelationDto rel = new EcomTaxonomyTermRelationDto();
				rel.setId(taxn.getId());
				rel.setTaxonomyName(taxn.getTaxonomyName());

				List<EcomTaxonomyTermDto> newTerms = new ArrayList<EcomTaxonomyTermDto>();

				terms.stream().forEach(term -> {
					if (term.getTaxonomyId() == taxn.getId()) {

						newTerms.add(term);
					}
				});

				rel.setTerm(newTerms);
				taxTermRelDto.add(rel);

			});

			dto.setTaxonomies(taxTermRelDto);

			response.setMsg("Listed successfully");
			response.setResponseContent(dto);
			return  response;		
		}else {

			response.setMsg("Empty data");
			return  response;		

			
			
		}

		
		
	}

	
	
	
	@Override
	public BaseResponseDTO getAllOurProducts() throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomProductVariantRepo.findByOurProducts());
	}
	
	
	
	@Override
	public BaseResponseDTO getSimilarProducts(Long variantId,Long categoryId) throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomProductVariantRepo.findSimilarProducts(variantId,categoryId));

	}

	@Override
	public BaseResponseDTO save(EcomProductDto ecomProductDto) throws Exception {
		ecomProductRepository.save(objectMapper.convertValue(ecomProductDto, EcomProduct.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}
	@Override
	public BaseResponseDTO createEcomProductVarients(EcomProductVariantDto ecomProductVariantDto) throws Exception {
		ecomProductVariantRepo.save(objectMapper.convertValue(ecomProductVariantDto, EcomProductVariant.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO update(EcomProductDto ecomProductDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<EcomProduct> ecomProduct = ecomProductRepository.findByVid(ecomProductDto.getVid());
		if (ecomProduct.isPresent()) {
			EcomProductMapper.toEcomProduct(ecomProductDto, ecomProduct.get());
			ecomProductRepository.save(ecomProduct.get());
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No Product for given vid.");
		}
		return baseResponseDTO;
	}

	
	@Override
	public BaseResponseDTO deleteByVid(Long vid) throws Exception {
	      ecomProductRepository.findByEcomProductByVid(vid);
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}
}
