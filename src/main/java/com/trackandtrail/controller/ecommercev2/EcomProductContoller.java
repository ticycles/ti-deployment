package com.trackandtrail.controller.ecommercev2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.trackandtrail.dto.ecommercev2.EcomProductDto;
import com.trackandtrail.dto.ecommercev2.EcomProductVariantDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.service.ecommercev2.EcomProductService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("Ecom/")
public class EcomProductContoller {

	@Autowired
	EcomProductService ecomProductService;

//	@ApiOperation(value = "doFilterAndPaginate", response = BaseResponseDTO.class)
//	@GetMapping(value = "doFilterAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<BaseResponseDTO> doFilterAndPaginate(@RequestParam String catogery,
//			@RequestParam String priceRange, @RequestParam String brands, @RequestParam String bikeTypes,
//			@RequestParam String roadType, @RequestParam String age, @RequestParam String gender,
//			@RequestParam String gearType, @RequestParam String suspension, @RequestParam String persona,
//			@RequestParam String size, @RequestParam String color, @RequestParam Integer pageNo,
//			@RequestParam Integer pageSize, @RequestParam String sortBy) throws Exception {
//		return new ResponseEntity<BaseResponseDTO>(ecomProductService.doFilterAndPaginate(catogery, priceRange, brands,
//				bikeTypes, roadType, age, gender, gearType, suspension, persona, size, color, pageNo, pageSize, sortBy),
//				HttpStatus.OK);
//	}

	@ApiOperation(value = "list", response = BaseResponseDTO.class)
	@GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> list(@RequestParam Long categoryId,
			@RequestParam(required = false) Long productType, @RequestParam(required = false) Double priceRangeMin,
			@RequestParam(required = false) Double priceRangeMax, @RequestParam(required = false) List<String> termIds,
			@RequestParam(required = false) List<String> color, @RequestParam(required = false) List<String> size,
			@RequestParam(required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(required = false) String sortBy) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(
				ecomProductService.listFilterAndPaginate(categoryId, productType, priceRangeMin, priceRangeMax, termIds,
						color, size, pageNo, pageSize, sortBy),

				HttpStatus.OK);
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllProductsByCategoryId", response = BaseResponseDTO.class)
	@GetMapping(value = "/getAllProductsByCategoryId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getAllProducts(@RequestParam Long categoryId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomProductService.getAllProducts(categoryId), HttpStatus.OK);
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAvailableProductList", response = BaseResponseDTO.class)
	@GetMapping(value = "/getAvailableProductList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getAvailableProductList(@RequestParam Long categoryId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomProductService.getAvailableProductList(categoryId), HttpStatus.OK);
	}

////	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
//	@ApiOperation(value = "getProductByVariantId", response = BaseResponseDTO.class)
//	@GetMapping(value = "/getProductByVariantId", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<BaseResponseDTO> getById(@RequestParam Long variantId) throws Exception {
//		return new ResponseEntity<BaseResponseDTO>(ecomProductService.getProductByVariantId(variantId), HttpStatus.OK);
//	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "changeStatusById", response = BaseResponseDTO.class)
	@GetMapping(value = "/changeStatusById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> changeStatusById(@RequestParam Long id, @RequestParam Integer statusId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomProductService.changeStatusById(id, statusId), HttpStatus.OK);
	}

	@ApiOperation(value = "getProductVariantByVariantId", response = BaseResponseDTO.class)
	@GetMapping(value = "/getProductVariantByVariantId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getVariantById(@RequestParam Long variantId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomProductService.getVariantById(variantId), HttpStatus.OK);
	}

	@ApiOperation(value = "getProductByProcutIdColorAndSize", response = BaseResponseDTO.class)
	@GetMapping(value = "/getProductByProcutIdColorAndSize/{productId}/{colorId}/{sizeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getVariantById(@PathVariable Long productId, Long colorId, Long sizeId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(
				ecomProductService.getVariantByProductIdColorIdSizeId(productId, colorId, sizeId), HttpStatus.OK);

	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getOurProducts", response = BaseResponseDTO.class)
	@GetMapping(value = "/getOurProducts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getAllOurProducts() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomProductService.getAllOurProducts(), HttpStatus.OK);
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getSimilarProducts", response = BaseResponseDTO.class)
	@GetMapping(value = "/getSimilarProducts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getSimilarProducts(Long variantId, Long categoryId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomProductService.getSimilarProducts(variantId, categoryId),
				HttpStatus.OK);
	}
	
	@ApiOperation(value = "createEcomProduct", response = BaseResponseDTO.class)
	@PostMapping(value = "createEcomProduct", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody EcomProductDto ecomProductDto)
			throws CusDataIntegrityViolationException, Exception {
		if(ecomProductDto.getId() != null)
			throw new BadRequestException("Id must be null");
		return new ResponseEntity<BaseResponseDTO>(ecomProductService.save(ecomProductDto), HttpStatus.OK);
	}
	
	@ApiOperation(value = "createEcomProductVarients", response = BaseResponseDTO.class)
	@PostMapping(value = "createEcomProductVarients", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> save(@RequestBody EcomProductVariantDto ecomProductVariantDto)
			throws CusDataIntegrityViolationException, Exception {
		if(ecomProductVariantDto.getId() != null)
			throw new BadRequestException("Id must be null");
		return new ResponseEntity<BaseResponseDTO>(ecomProductService.createEcomProductVarients(ecomProductVariantDto), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateEcomProduct", response = BaseResponseDTO.class)
	@PutMapping(value = "updateEcomProduct", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> update(@RequestBody EcomProductDto ecomProductDto)
			throws CusDataIntegrityViolationException, Exception {
		if (ecomProductDto.getVid() == null)
			throw new BadRequestException("Id cannot be null");
		return new ResponseEntity<BaseResponseDTO>(ecomProductService.update(ecomProductDto), HttpStatus.OK);
	}

	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deleteByVid", response = BaseResponseDTO.class)
	@DeleteMapping(value = "/deleteByVid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> deletebyVid(@RequestParam Long vid)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomProductService.deleteByVid(vid), HttpStatus.OK);
	}

	

}
