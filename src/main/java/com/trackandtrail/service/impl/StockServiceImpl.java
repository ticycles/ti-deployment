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
import com.trackandtrail.dto.StockDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.StockMapper;
import com.trackandtrail.model.stock.Stock;
import com.trackandtrail.repository.StockRepository;
import com.trackandtrail.repository.StockViewRepository;
import com.trackandtrail.service.StockService;
import com.trackandtrail.util.GenericSearchandPaginationUtil;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class StockServiceImpl implements StockService{
	
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private StockViewRepository stockViewRepository;
	
	
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	EntityManager entityManager;

	@Override
	public BaseResponseDTO save(StockDto stockDto) throws Exception {
		stockRepository.save(objectMapper.convertValue(stockDto, Stock.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO update(StockDto stockDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<Stock> stock = stockRepository.findById(stockDto.getId());
		if (stock.isPresent()) {
			StockMapper.toStock(stockDto, stock.get());
			stockRepository.save(stock.get());
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
					RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No stock found for given Id.");
		}
		return baseResponseDTO;
	}

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(stockRepository.findAll());
	}

	@Override
	public BaseResponseDTO getById(Long id) throws Exception {
		return GenericUtils.wrapOrNotFound(stockRepository.findById(id));
	}

	@Override
	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception {
		Optional<Stock> stock = stockRepository.findById(id);
		if (stock.isPresent()) {
			if (isSoftDelete) {
				stock.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				stockRepository.save(stock.get());
			} else {
				stockRepository.deleteById(id);
			}
		} else {
			throw new ResourceNotFoundException("No Stock found for given Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception {
		Optional<Stock> stock = stockRepository.findById(id);
		BaseResponseDTO responseDTO = null;
		if (stock.isPresent()) {
			switch (statusId) {
			case 2:
				stock.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
				break;
			case 0:
				stock.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			case 1:
				stock.get().setStatus(RecordStatusUtil.RECORD_ACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.ACTIVE.getErrorDescription(),
						RequestStatusUtil.ACTIVE.getErrorCode());
				break;
			default:
				stock.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			}
			stockRepository.save(stock.get());
		} else {
			throw new ResourceNotFoundException("No Badge Reward found for given Id.");
		}
		return responseDTO;
	}

	@Override
	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<Stock> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames = new HashMap<>();
		columnNames.put("bikeRentalProduct", "name");
		columnNames.put("storedetail", "name");
		return GenericUtils.wrapOrEmptyPagination(
				util.searchByColumns(paginationDTO, columnNames, Stock.class, entityManager));
	}
	
	
	@Override
	public BaseResponseDTO getAllStock() throws Exception {
		return GenericUtils.wrapOrEmptyList(stockViewRepository.findAllStock());
	}
}

	


