package com.trackandtrail.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.BikeRentalBookingDto;
import com.trackandtrail.dto.BikeRentalProductDto;
import com.trackandtrail.dto.ManageDealerDto;
import com.trackandtrail.dto.RejectReasonDto;
import com.trackandtrail.dto.StoreDetailDto;
import com.trackandtrail.dto.WishListDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalBooking;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.BikeRentalBookingRepository;
import com.trackandtrail.repository.BikeRentalProductRepository;
import com.trackandtrail.repository.StoreDetailRepository;
import com.trackandtrail.service.BikeRentalBookingService;
import com.trackandtrail.service.BikeRentalService;
import com.trackandtrail.service.StoreDetailService;
import com.trackandtrail.service.WishListService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bike-rental")
@CrossOrigin
public class BikeRentalController {

	private static final Logger logger = LoggerFactory.getLogger(BikeRentalController.class);

	@Autowired
	BikeRentalService rentalService;

	@Autowired
	BikeRentalProductRepository repo;

	@Autowired
	StoreDetailRepository storeDetailRepository;

	@Autowired
	WishListService wishListService;

	@Autowired
	BikeRentalBookingService bikeRentalBookingService;

	@Autowired
	BikeRentalBookingRepository bikeRentalBookingRepository;

	@Autowired
	StoreDetailService storeDetailService;

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "createProduct", response = BaseResponseDTO.class)
	@PostMapping("product/save")
	public ResponseEntity<BaseResponseDTO> save(@RequestBody BikeRentalProductDto dto) throws Exception {

		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;

		if (dto.getId() != null)
			throw new BadRequestException("product id cannot contain value");

		Optional<BikeRentalProduct> prdEx = Optional.ofNullable(repo.findByProductSkuIgnoreCase(dto.getProductSku()));

		if (prdEx.isPresent()) {
			responseModel.setMsg("Product SKU Already Exist.");
			httpStatus = HttpStatus.CONFLICT;
			return new ResponseEntity<>(responseModel, httpStatus);
		}

		responseModel = rentalService.save(dto);
		httpStatus = HttpStatus.OK;
		try {

		} catch (Exception ex) {

			responseModel.setMsg("Exception occurred while creating the product");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.info("BikeRentalController:: create :: contentModel :: ", dto.toString());

		}

		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("BikeRentalController:: create :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "updateProduct", response = BaseResponseDTO.class)
	@PutMapping(value = "product/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> update(@RequestBody BikeRentalProductDto dto) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			logger.info("BikeRentalController:: update :: contentModel :: ", dto.toString());
			if (dto.getId() == null)
				throw new BadRequestException("Id cannot be null");

			Optional<List<BikeRentalProduct>> prdEx = Optional
					.ofNullable(rentalService.findByProductSku(dto.getProductSku(), dto.getId()));
			logger.info("sfsfsdfsfdsfdf:: update :: contentModel :: ", dto.getProductSku());
			if (prdEx.isPresent() && prdEx.get().size() > 0) {
				responseModel.setMsg("Product SKU Already Exist.");
				httpStatus = HttpStatus.CONFLICT;
				return new ResponseEntity<>(responseModel, httpStatus);
			}

			responseModel = rentalService.update(dto);

			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {

			responseModel.setMsg(ex.getMessage().toString());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("BikeRentalController:: update :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "getById", response = BaseResponseDTO.class)
	@GetMapping(value = "product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getById(@Valid @PathVariable Long id) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			logger.info("BikeRentalController:: getById :: contentModel :: ", id.toString());
			responseModel = rentalService.getByBikeRentalId(id);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			responseModel.setMsg(ex.getMessage().toString());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("BikeRentalController:: getById :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllBikeRentalProduct", response = BaseResponseDTO.class)
	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getall() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(rentalService.getAll(), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createWishList", response = BaseResponseDTO.class)
	@PostMapping(value = "wishlist/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody WishListDto wishListDto)
			throws CusDataIntegrityViolationException, Exception {
		if (wishListDto.getWishListId() != null)
			throw new BadRequestException("Id must be null");
		return new ResponseEntity<BaseResponseDTO>(wishListService.save(wishListDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllWishList", response = BaseResponseDTO.class)
	@GetMapping(value = "wishList/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getallWishList() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(wishListService.getAll(), HttpStatus.OK);
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deleteByWishListId", response = BaseResponseDTO.class)
	@DeleteMapping(value = "wishList/deletebyid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> deletebyWishListid(@RequestParam Long id, @RequestParam Boolean isSoftDelete)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(wishListService.deleteById(id, isSoftDelete), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "searchAndPaginate", response = BaseResponseDTO.class)
	@PostMapping(value = "product/searchAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> searchAndPaginate(@RequestBody PaginationDTO paginationDTO)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(rentalService.searchAndPaginate(paginationDTO), HttpStatus.OK);
	}

//	@PostMapping("/searchAndPaginate")
//	public ResponseEntity<BaseResponseDTO> searchAndPaginate(@RequestParam int pageNo, @RequestParam int pageSize,
//			@RequestParam String sortOrder, @RequestParam String sortBy, @RequestParam String searchData)
//			throws Exception {
//		return new ResponseEntity<BaseResponseDTO>(
//				rentalService.searchAndPaginate(new PaginationDTO(pageNo, pageSize, sortOrder, sortBy, searchData)),
//				HttpStatus.OK);
//	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createBikeRentalBooking", response = BaseResponseDTO.class)
	@PostMapping(value = "booking/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody BikeRentalBookingDto bikeRentalDetailDto)
			throws CusDataIntegrityViolationException, Exception {
		if (bikeRentalDetailDto.getBookingId() != null)
			throw new BadRequestException("Id must be null");
		return new ResponseEntity<BaseResponseDTO>(bikeRentalBookingService.save(bikeRentalDetailDto), HttpStatus.OK);

	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllBikeRentalBooking", response = BaseResponseDTO.class)
	@GetMapping(value = "booking/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getallBooking() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(bikeRentalBookingService.getAll(), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "searchAndPaginate", response = BaseResponseDTO.class)
	@PostMapping(value = "booking/searchAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> searchAndPaginateBooking(@RequestBody PaginationDTO paginationDTO)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(bikeRentalBookingService.searchAndPaginateBooking(paginationDTO),
				HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/getBookingByUserId")
	public ResponseEntity<BaseResponseDTO> getByUserId(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(bikeRentalBookingService.getByUserId(userId), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("booking/getById")
	public ResponseEntity<BaseResponseDTO> getByBookingId(@RequestParam Long id) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(bikeRentalBookingService.getById(id), HttpStatus.OK);
	}

	/*
	 * STORE DETAILS
	 */

////	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createStoreDetail", response = BaseResponseDTO.class)
	@PostMapping(value = "storeDetail/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody StoreDetailDto storeDetailDto)
			throws CusDataIntegrityViolationException, Exception {

		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;

		if (storeDetailDto.getId() != null)
			throw new BadRequestException("Id must be null");

		Optional<StoreDetail> prdEx = Optional
				.ofNullable(storeDetailRepository.findByNameIgnoreCase(storeDetailDto.getName()));

		if (prdEx.isPresent()) {
			responseModel.setMsg("Store Already Exist.");
			httpStatus = HttpStatus.CONFLICT;
			return new ResponseEntity<>(responseModel, httpStatus);
		}

		return new ResponseEntity<BaseResponseDTO>(storeDetailService.save(storeDetailDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllStoreDetail", response = BaseResponseDTO.class)
	@GetMapping(value = "storedetail/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getAllStoreDetail() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(storeDetailService.getAll(), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getByStoreId", response = BaseResponseDTO.class)
	@GetMapping(value = "store/getbyid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getByStoreId(@RequestParam Long id) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(storeDetailService.getById(id), HttpStatus.OK);
	}

////@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateStoreDetail", response = BaseResponseDTO.class)
	@PostMapping(value = "storeDetail/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> update(@RequestBody StoreDetailDto storeDetailDto)
			throws CusDataIntegrityViolationException, Exception {

		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;

		if (storeDetailDto.getId() == null)
			throw new BadRequestException("Id cannot be null");

		return new ResponseEntity<BaseResponseDTO>(storeDetailService.update(storeDetailDto), HttpStatus.OK);
	}

//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deleteByStoreId", response = BaseResponseDTO.class)
	@DeleteMapping(value = "store/deletebyid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> deletebyid(@RequestParam Long id, @RequestParam Boolean isSoftDelete)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(storeDetailService.deleteById(id, isSoftDelete), HttpStatus.OK);
	}

////	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
//	@ApiOperation(value = "getAll", response = BaseResponseDTO.class)
//	@GetMapping(value = "bikeFilter/getall", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<BaseResponseDTO> getAllBikeFilter() throws Exception {
//		return new ResponseEntity<BaseResponseDTO>(bikeRentalBookingService.getAllBikeFilter(), HttpStatus.OK);
//	}

	@ApiOperation(value = "doFilterAndPaginate", response = BaseResponseDTO.class)
	@GetMapping(value = "doFilterAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> doFilterAndPaginate(@RequestParam String color, @RequestParam String size,
			@RequestParam String type, @RequestParam String priceRange, @RequestParam Integer pageNo,
			@RequestParam Integer pageSize, @RequestParam String sortBy) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(
				rentalService.doFilterAndPaginate(color, size, type, priceRange, pageNo, pageSize, sortBy),
				HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "bookingStatus", response = BaseResponseDTO.class)
	@PostMapping(value = "/bookingStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> changeStatusById(@RequestBody RejectReasonDto rejectReasonDto)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(bikeRentalBookingService.changeStatusById(rejectReasonDto),
				HttpStatus.OK);
	}
	
	@ApiOperation(value = "BookingFilter", response = BaseResponseDTO.class)
	@GetMapping(value = "BookingFilter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> BookingFilter(@RequestParam Long userId,@RequestParam String search,
			 @RequestParam Integer pageNo,
			@RequestParam Integer pageSize) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(
				bikeRentalBookingService.doFilterAndPaginate( userId,search,pageNo, pageSize),
				HttpStatus.OK);
	}

}
