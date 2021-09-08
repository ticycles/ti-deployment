package com.trackandtrail.controller.ecommercev2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomCartDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.model.ecommercev2.EcomCart;
import com.trackandtrail.service.ecommercev2.EcomCartService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("Ecom/")
public class EcomAddToCartController {
	
	
	
	@Autowired
	private EcomCartService addToCartService;
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createAddToCart", response = BaseResponseDTO.class)
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody EcomCartDto ecomCartDto)
			throws CusDataIntegrityViolationException, Exception {
		if (ecomCartDto.getId() != null)
			throw new BadRequestException("Id must be null");
		return new ResponseEntity<BaseResponseDTO>(addToCartService.save(ecomCartDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllCart", response = BaseResponseDTO.class)
	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getAllCart() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(addToCartService.getAll(), HttpStatus.OK);
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deleteByCartId", response = BaseResponseDTO.class)
	@DeleteMapping(value = "/deletebyid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> deleteById(@RequestParam Long id, @RequestParam Boolean isSoftDelete)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(addToCartService.deleteById(id, isSoftDelete),
				HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getCartByUserId", response = BaseResponseDTO.class)
	@GetMapping(value = "/getCartByUserId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getCartByUserId(@RequestParam Long id) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(addToCartService.getById(id), HttpStatus.OK);
	}



}
