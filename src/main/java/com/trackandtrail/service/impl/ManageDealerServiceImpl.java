package com.trackandtrail.service.impl;


import java.util.Optional;
import java.util.Map;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.ManageDealerDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.ManageDealerMapper;
import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.model.managedealer.ManageDealer;
import com.trackandtrail.repository.ManageDealerRepository;
import com.trackandtrail.service.ManageDealerService;
import com.trackandtrail.util.GenericSearchandPaginationUtil;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class ManageDealerServiceImpl implements ManageDealerService {

	@Autowired
	ManageDealerRepository manageDealerRepository;

	@Autowired
	private ObjectMapper objectMapper;

	
	@Autowired
	EntityManager entityManager;

	

	@Override
	public BaseResponseDTO save(ManageDealerDto manageDealerDto) throws Exception {
		manageDealerRepository.save(objectMapper.convertValue(manageDealerDto, ManageDealer.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO update(ManageDealerDto manageDealerDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<ManageDealer> manageDealer = manageDealerRepository.findById(manageDealerDto.getId());
		if (manageDealer.isPresent()) {
			ManageDealerMapper.toManageDealers(manageDealerDto, manageDealer.get());
			manageDealerRepository.save(manageDealer.get());
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
					RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No Manage Dealers found for given shopId.");
		}
		return baseResponseDTO;
	}


	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(manageDealerRepository.findAll());
	}
	
	@Override
	public BaseResponseDTO getActiveDealers() throws Exception {
		return GenericUtils.wrapOrEmptyList(manageDealerRepository.findActiveDealers());
	}

	@Override
	public BaseResponseDTO getById(Long shopId) throws Exception {
		return GenericUtils.wrapOrNotFound(manageDealerRepository.findById(shopId));
	}


	@Override
	public BaseResponseDTO deleteById(Long shopId, boolean isSoftDelete) throws Exception {
		Optional<ManageDealer> managedealers = manageDealerRepository.findById(shopId);
		if (managedealers.isPresent()) {
			if (isSoftDelete) {
				managedealers.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				manageDealerRepository.save(managedealers.get());
			} else {
				manageDealerRepository.deleteById(shopId);
			}
		} else {
			throw new ResourceNotFoundException("No Manage Dealers found for given shopId.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

	
	@Override
	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception {
		Optional<ManageDealer> dealer = manageDealerRepository.findById(id);
		BaseResponseDTO responseDTO = null;
		if (dealer.isPresent()) {
			switch (statusId) {
			case 2:
				dealer.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
				break;
			case 0:
				dealer.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			case 1:
				dealer.get().setStatus(RecordStatusUtil.RECORD_ACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.ACTIVE.getErrorDescription(),
						RequestStatusUtil.ACTIVE.getErrorCode());
				break;
			default:
				dealer.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			}
			manageDealerRepository.save(dealer.get());
		} else {
			throw new ResourceNotFoundException("No dealer found for given Id.");
		}
		return responseDTO;
	}
	
	
	@Override
	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<ManageDealer> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames = new HashMap<>();
		columnNames.put("state", "name");
		columnNames.put("city", "name");
		columnNames.put("pincode", "name");
		return GenericUtils.wrapOrEmptyPagination(
				util.searchByColumns(paginationDTO, columnNames, ManageDealer.class, entityManager));
	}

	
}

	

