package com.trackandtrail.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.BikeRentalProductDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.BikeRentalProductMapper;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;
import com.trackandtrail.repository.BikeRentalBookingRepository;
import com.trackandtrail.repository.BikeRentalProductRepository;
import com.trackandtrail.service.BikeRentalService;
import com.trackandtrail.util.Constants;
import com.trackandtrail.util.GenericSearchandPaginationUtil;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class BikeRentalServiceImpl implements BikeRentalService {

	@Autowired
	BikeRentalProductRepository bikeRentalManagementrepository;

	@Autowired
	BikeRentalBookingRepository bikeRentalDetailRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private EntityManager entityManager;

	@Override
	public BaseResponseDTO save(BikeRentalProductDto bikeRentalManagementDTO) throws Exception {
		bikeRentalManagementrepository
				.save(objectMapper.convertValue(bikeRentalManagementDTO, BikeRentalProduct.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(bikeRentalManagementrepository.findAll());
	}

	@Override
	public BaseResponseDTO getByBikeRentalId(Long bikeRentalId) throws Exception {
		return GenericUtils.wrapOrNotFound(bikeRentalManagementrepository.findById(bikeRentalId));
	}

	@Override
	public BaseResponseDTO update(BikeRentalProductDto dto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<BikeRentalProduct> user = bikeRentalManagementrepository.findById(dto.getId());
		if (user.isPresent()) {
			BikeRentalProductMapper.toBikeRentalProduct(dto, user.get());
			bikeRentalManagementrepository.save(user.get());
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
					RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No product found for given Id.");
		}

		return baseResponseDTO;
	}

	@Override
	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception {
		return null;
	}

	public List<BikeRentalProduct> findByProductSku(String sku, Long ignoreProductId) throws Exception {

		CriteriaBuilder criteria = entityManager.getCriteriaBuilder();
		CriteriaQuery<BikeRentalProduct> criteriaQuery = criteria.createQuery(BikeRentalProduct.class);
		Root<BikeRentalProduct> rootQuery = criteriaQuery.from(BikeRentalProduct.class);

		List<Predicate> predicates = new ArrayList<>();

		if (sku != null) {
			predicates.add(criteria.equal(rootQuery.get("productSku"), sku));
		}

		if (ignoreProductId != null) {
			predicates.add(criteria.notEqual(rootQuery.get("id"), ignoreProductId));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		System.out.println(predicates.toArray(new Predicate[] {}).toString());

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<BikeRentalProduct> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames = new HashMap<>();
		columnNames.put("bikeBrand", "name");
		columnNames.put("bikeModel", "name");
		return GenericUtils.wrapOrEmptyPagination(
				util.searchByColumns(paginationDTO, columnNames, BikeRentalProduct.class, entityManager));
	}

	@Override
	public BaseResponseDTO doFilterAndPaginate(String color, String size, String type, String priceRange,
			Integer pageNo, Integer pageSize, String sortBy) throws Exception {

		List<BikeRentalProduct> bikeRentalProducts = new ArrayList<BikeRentalProduct>();
		StringBuffer sqlQuery = new StringBuffer(Constants.BIKERENTAL_FILTER_QUERY);

		if (null != color && !color.equalsIgnoreCase("null")) {
			String colors = "'" + color.replace(",", "','") + "'";
			sqlQuery.append("AND color IN(" + colors + ") ");
		}
		if (null != size && !size.equalsIgnoreCase("null"))
			sqlQuery.append("AND size IN(" + size + ") ");
		if (null != type && !type.equalsIgnoreCase("null")) {
			String types = "'" + type.replace(",", "','") + "'";
			sqlQuery.append("AND type IN(" + types + ") ");
		}
		if (null != priceRange && !priceRange.equalsIgnoreCase("null"))
			sqlQuery.append("AND price between " + priceRange.split("#")[0] + " and " + priceRange.split("#")[1] + "");
		
		

		if (null != sortBy && !sortBy.equalsIgnoreCase("null")) {
			if (sortBy.equalsIgnoreCase("price { high<low }"))
				sqlQuery.append(" order by price asc");
			if (sortBy.equalsIgnoreCase("price { high>low }"))
				sqlQuery.append(" order by price desc");
			if (sortBy.contains("newest first"))
				sqlQuery.append(" order by created_on asc");
			if (sortBy.contains("rating"))
				sqlQuery.append(" order by rating desc");
		}else
			sqlQuery.append(" order by updated_on desc");
		Query query = entityManager.createNativeQuery(sqlQuery.toString(), BikeRentalProduct.class);
		query.setFirstResult(pageNo * pageSize).setMaxResults(pageSize);
		bikeRentalProducts = query.getResultList();
		String countquery = Constants.BIKERENTAL_FILTER_QUERY_COUNT.replace("BIKERENTAL_FILTER_QUERY",
				sqlQuery.toString());
		Long count = ((Number) entityManager.createNativeQuery(countquery).getSingleResult()).longValue();
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<BikeRentalProduct> page = new PageImpl<>(bikeRentalProducts, pageable, count);
		return GenericUtils.wrapOrEmptyPagination(page);
	}
}
