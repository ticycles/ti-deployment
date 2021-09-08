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
import com.trackandtrail.dto.ecommercev2.EcomWishListDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.service.ecommercev2.EcomWishListService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("Ecom/")
public class EcomWishListController {

	
	
	@Autowired
	private EcomWishListService ecommerceWishListService;
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createEcommerceWishList", response = BaseResponseDTO.class)
	@PostMapping(value = "/createEcommerceWishList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody EcomWishListDto ecomWishListDto)
			throws CusDataIntegrityViolationException, Exception {
		if (ecomWishListDto.getId() != null)
			throw new BadRequestException("Id must be null");
		return new ResponseEntity<BaseResponseDTO>(ecommerceWishListService.save(ecomWishListDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllWishList", response = BaseResponseDTO.class)
	@GetMapping(value = "/getAllWishList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getallWishList() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecommerceWishListService.getAll(), HttpStatus.OK);
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deleteByWishListId", response = BaseResponseDTO.class)
	@DeleteMapping(value = "/deleteByWishListId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> deleteById(@RequestParam Long id, @RequestParam Boolean isSoftDelete)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecommerceWishListService.deleteById(id, isSoftDelete), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getWishListByUserId", response = BaseResponseDTO.class)
	@GetMapping(value = "/getWishListByUserId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getWishListByUserId(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecommerceWishListService.getByUserId(userId), HttpStatus.OK);
	}

}
