package com.trackandtrail.controller.ecommercev2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomReviewDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.service.ecommercev2.EcomReviewService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("Ecom/Review")
public class EcomReviewController {
	
	
	@Autowired
	private EcomReviewService ecomReviewService;
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createReview", response = BaseResponseDTO.class)
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody EcomReviewDto ecomReviewDto) throws Exception {
		if (ecomReviewDto.getId() != null)
			throw new BadRequestException("Id cannot contain value");
		return new ResponseEntity<BaseResponseDTO>(ecomReviewService.save(ecomReviewDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateReview", response = BaseResponseDTO.class)
	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> update(@RequestBody EcomReviewDto ecomReviewDto) 
			throws CusDataIntegrityViolationException,Exception {
		if (ecomReviewDto.getId() == null)
			throw new BadRequestException("Id cannot be null");
		return new ResponseEntity<BaseResponseDTO>(ecomReviewService.update(ecomReviewDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllEcomReview", response = BaseResponseDTO.class)
	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getAllEcomReview() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomReviewService.getAll(), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getReviewAndComment", response = BaseResponseDTO.class)
	@GetMapping(value = "/getReviewAndComment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getReviewAndComment(@RequestParam Long userId, @RequestParam Long variantId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomReviewService.getRewiewAndComment(userId, variantId),
				HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getByVariantId", response = BaseResponseDTO.class)
	@GetMapping(value = "/getByVariantId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getByVariantId(@RequestParam Long  variantId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomReviewService.getById(variantId),
				HttpStatus.OK);
	}
}