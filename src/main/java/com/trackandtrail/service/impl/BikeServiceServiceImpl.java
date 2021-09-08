package com.trackandtrail.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.BikeServiceDto;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalBooking;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail;
import com.trackandtrail.model.bikeservicepackage.BikeService;
import com.trackandtrail.model.bikeservicepackage.BikeServicePackage;
import com.trackandtrail.model.registerbike.BikeBrand;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.BikeBrandRepository;
import com.trackandtrail.repository.BikeServicePackageRepository;
import com.trackandtrail.repository.BikeServiceRepository;
import com.trackandtrail.repository.StoreDetailRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.AlertService;
import com.trackandtrail.service.BikeServiceService;
import com.trackandtrail.util.GenericSearchandPaginationUtil;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RequestStatusUtil;
import com.trackandtrail.util.StringUtils;


@Service
public class BikeServiceServiceImpl implements BikeServiceService {
	
	@Autowired
	BikeServiceRepository bikeServiceRepository;
	
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	AlertService alertService;
	
	@Autowired
	StoreDetailRepository storeDetailRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BikeServicePackageRepository bikeServicePackageRepository;
	
	@Autowired
	BikeBrandRepository bikeBrandRepository;
	


	
	@Override
	public BaseResponseDTO save(BikeServiceDto bikeServiceDto) throws Exception {
		 String uuid=StringUtils.getAlphaNumericString();
		 bikeServiceDto.setUuid(uuid);
		bikeServiceRepository.save(objectMapper.convertValue(bikeServiceDto, BikeService.class));
		
		 Optional<UserModel> userModel=userRepository.findById(bikeServiceDto.getUserModel().getId());
		 Optional<BikeServicePackage> bikeServicePackage=bikeServicePackageRepository.findById(bikeServiceDto.getBikeServicePackage().getId());
		 Optional<BikeBrand> bikeBrand=bikeBrandRepository.findById(bikeServiceDto.getBikeBrand().getId());
		 Optional<StoreDetail> storeDetails=storeDetailRepository.findById(bikeServiceDto.getStoreDetail().getId());
		 
			if(userModel.isPresent() && bikeServicePackage.isPresent() && bikeBrand.isPresent() && storeDetails.isPresent())
				alertService.sendBikeServiceAlert(userModel.get(),bikeServicePackage.get(),bikeBrand.get(),storeDetails.get(),uuid);
			
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode(),uuid);
	}
	
	

	@Override
	public BaseResponseDTO getByUserId(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(bikeServiceRepository.findByUserModelId(userId));			
		
	}
	
	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(bikeServiceRepository.findAll());
	}

	@Override
	public BaseResponseDTO getById(Long id) throws Exception {
		return GenericUtils.wrapOrNotFound(bikeServiceRepository.findById(id));
	}

	
	@Override
	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<BikeService> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames = new HashMap<>();
		columnNames.put("userModel", "username");
		columnNames.put("storeDetail", "name");
		columnNames.put("bikeModel", "name");
		columnNames.put("bikeServicePackage", "name");
		columnNames.put("bikeBrand", "name");		
		return GenericUtils.wrapOrEmptyPagination(
				util.searchByColumns(paginationDTO, columnNames, BikeService.class, entityManager));
	}
	}

