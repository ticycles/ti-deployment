package com.trackandtrail.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.BikeRentalBookingDto;
import com.trackandtrail.dto.ManageDealerDto;
import com.trackandtrail.dto.RejectReasonDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalBooking;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail;
import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.managedealer.ManageDealer;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.BikeFilterRepository;
import com.trackandtrail.repository.BikeRentalBookingRepository;
import com.trackandtrail.repository.BikeRentalProductRepository;
import com.trackandtrail.repository.ManageDealerRepository;
import com.trackandtrail.repository.StoreDetailRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.AlertService;
import com.trackandtrail.service.BikeRentalBookingService;
import com.trackandtrail.util.Constants;
import com.trackandtrail.util.GenericSearchandPaginationUtil;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;
import com.trackandtrail.util.StringUtils;

@Service
public class BikeRentalBookingServiceImpl implements BikeRentalBookingService {

	@Autowired
	BikeRentalBookingRepository bikeRentalBookingRepository;

	@Autowired
	BikeFilterRepository bikefilterRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private AlertService alertService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	BikeRentalProductRepository bikeRentalProductRepository;

	@Autowired
	StoreDetailRepository storeDetailRepository;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	ManageDealerRepository manageDealerRepository;

	@Override
	public BaseResponseDTO save(BikeRentalBookingDto bikeRentalDetailDto) throws Exception {
		String uuid = StringUtils.getAlphaNumericString();
		bikeRentalDetailDto.setUuid(uuid);
		bikeRentalBookingRepository.save(objectMapper.convertValue(bikeRentalDetailDto, BikeRentalBooking.class));
		
		
		Optional<UserModel> userModel = userRepository.findById(bikeRentalDetailDto.getUserModel().getId());
		Optional<BikeRentalProduct> bikeRentalProduct = bikeRentalProductRepository
				.findById(bikeRentalDetailDto.getBikeRentalProduct().getId());
		Optional<StoreDetail> storeDetail = storeDetailRepository
				.findById(bikeRentalDetailDto.getStoreDetail().getId());
		
		// String orderNumber=uuid;
		if (userModel.isPresent() && bikeRentalProduct.isPresent() && storeDetail.isPresent())
			alertService.sendBikeRentalBookingAlert(userModel.get(), bikeRentalProduct.get(), storeDetail.get(),
					uuid);

		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode(), uuid);
	}

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(bikeRentalBookingRepository.findAll());
	}

//
	@Override
	public BaseResponseDTO getByUserId(Long id) throws Exception {
		return GenericUtils.wrapOrEmptyList(bikeRentalBookingRepository.findByUserModelId(id));
	}
//	

	@Override
	public BaseResponseDTO getById(Long id) throws Exception {
		return GenericUtils.wrapOrEmptyList(bikeRentalBookingRepository.findByBookingId(id));
	}

	@Override
	public BaseResponseDTO searchAndPaginateBooking(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<BikeRentalBooking> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames = new HashMap<>();
		columnNames.put("userModel", "id");
		columnNames.put("bikeRentalProduct", "name");
		columnNames.put("storeDetail", "name");
		return GenericUtils.wrapOrEmptyPagination(
				util.searchBySpecificColumns(paginationDTO, columnNames, BikeRentalBooking.class, entityManager));
	}

	@Override
	public BaseResponseDTO getAllBikeFilter() throws Exception {
		return GenericUtils.wrapOrEmptyList(bikefilterRepository.findAllBikeFilter());
	}

	@Override
	public BaseResponseDTO changeStatusById(RejectReasonDto rejectReasonDto) throws Exception {
		Optional<BikeRentalBooking> bikeRentalBooking = bikeRentalBookingRepository
				.findById(rejectReasonDto.getBookingId());
		BaseResponseDTO responseDTO = null;
		if (bikeRentalBooking.isPresent()) {
			switch (rejectReasonDto.getStatus()) {
			case 2:
				bikeRentalBooking.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				bikeRentalBooking.get().setRejectReason(rejectReasonDto.getRejectReason());
				bikeRentalBooking.get().setRejectionDate(new Date());
				responseDTO = new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
				break;
			case 0:
				bikeRentalBooking.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				bikeRentalBooking.get().setConformDate(new Date());
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			case 3:
				bikeRentalBooking.get().setStatus(RecordStatusUtil.RETURN);
				bikeRentalBooking.get().setReturnDate(rejectReasonDto.getReturnDate());
				responseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
				break;
			case 1:
				bikeRentalBooking.get().setStatus(RecordStatusUtil.RECORD_ACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.ACTIVE.getErrorDescription(),
						RequestStatusUtil.ACTIVE.getErrorCode());
				break;
			default:
				bikeRentalBooking.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;

			}
			bikeRentalBookingRepository.save(bikeRentalBooking.get());

		} else {
			throw new ResourceNotFoundException("No bookings for given Id.");
		}
		return responseDTO;
	}

	@Override
	public BaseResponseDTO doFilterAndPaginate(Long userid, String search, Integer pageNo, Integer pageSize)
			throws Exception {
		List<BikeRentalBooking> booking = new ArrayList<BikeRentalBooking>();
		StringBuffer sqlQuery = new StringBuffer(Constants.BIKEBOOKING_QUERY);

		if (null != userid && userid != 0) {
			sqlQuery.append("AND (b.user_id=" + userid + " ) ");
		}

		if (null != search && !search.equalsIgnoreCase("null")) {
//			String searches = "'" + search.replace(",", "','") + "'";
			sqlQuery.append("AND r.name like '%" + search + "%'");

		}

		sqlQuery.append(" group by user_id order by updated_on desc");

		Query query = entityManager.createNativeQuery(sqlQuery.toString(), BikeRentalBooking.class);
		query.setFirstResult(pageNo * pageSize).setMaxResults(pageSize);
		booking = query.getResultList();
		String countquery = Constants.BIKERENTAL_FILTER_QUERY_COUNT.replace("BIKERENTAL_FILTER_QUERY",
				sqlQuery.toString());
		Long count = ((Number) entityManager.createNativeQuery(countquery).getSingleResult()).longValue();
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<BikeRentalBooking> page = new PageImpl<>(booking, pageable, count);
		return GenericUtils.wrapOrEmptyPagination(page);
	}

}
