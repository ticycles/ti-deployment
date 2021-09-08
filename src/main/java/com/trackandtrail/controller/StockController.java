package com.trackandtrail.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.StockDto;
import com.trackandtrail.dto.StoreDetailDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail;
import com.trackandtrail.model.stock.Stock;
import com.trackandtrail.model.userpreference.UserPreference;
import com.trackandtrail.repository.StockRepository;
import com.trackandtrail.service.StockService;
import com.trackandtrail.util.RequestStatusUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/stock")
@CrossOrigin
public class StockController {

	@Autowired
	private StockService stockService;
	
	
	@Autowired
	private StockRepository stockRepository;

	/// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createStock", response = BaseResponseDTO.class)
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody StockDto stockDto)
			throws CusDataIntegrityViolationException, Exception {

		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;

		if (stockDto.getId() != null)
			throw new BadRequestException("Id must be null");

		Optional<Stock> stock = stockRepository
				.findByBikeRentalProductIdAndStoredetailId(stockDto.getBikeRentalProduct().getId(),stockDto.getStoredetail().getId());

		if (stock.isPresent()) {
			responseModel.setMsg("Product Already exist for store.");
			responseModel.setErrorCode(RequestStatusUtil.DUPLICATE_ENTRY.getErrorCode());
			httpStatus = HttpStatus.CONFLICT;
			return new ResponseEntity<>(responseModel, httpStatus);
		}

		return new ResponseEntity<BaseResponseDTO>(stockService.save(stockDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateStock", response = BaseResponseDTO.class)
	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> updateUser(@RequestBody StockDto stockDto)
			throws CusDataIntegrityViolationException, Exception {
		if (stockDto.getId() == null)
			throw new BadRequestException("Id cannot be null");
		return new ResponseEntity<BaseResponseDTO>(stockService.update(stockDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllStock", response = BaseResponseDTO.class)
	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getall() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(stockService.getAll(), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getbyid", response = BaseResponseDTO.class)
	@GetMapping(value = "/getbyid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getById(@RequestParam Long id) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(stockService.getById(id), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deletebyid", response = BaseResponseDTO.class)
	@DeleteMapping(value = "/deletebyid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> deletebyid(@RequestParam Long id, @RequestParam Boolean isSoftDelete)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(stockService.deleteById(id, isSoftDelete), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "changeStatusById", response = BaseResponseDTO.class)
	@GetMapping(value = "/changeStatusById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> changeStatusById(@RequestParam Long id, @RequestParam Integer statusId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(stockService.changeStatusById(id, statusId), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "searchAndPaginate", response = BaseResponseDTO.class)
	@PostMapping(value = "/searchAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> searchAndPaginate(@RequestBody PaginationDTO paginationDTO)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(stockService.searchAndPaginate(paginationDTO), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getStockInQty", response = BaseResponseDTO.class)
	@GetMapping(value = "stockQty/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getallStock() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(stockService.getAllStock(), HttpStatus.OK);
	}

}
