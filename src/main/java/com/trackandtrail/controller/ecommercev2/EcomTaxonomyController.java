package com.trackandtrail.controller.ecommercev2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcommerceTaxonomyDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.service.ecommercev2.EcomTaxonomyService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("Ecom/")
public class EcomTaxonomyController {

	@Autowired
	private EcomTaxonomyService ecomTaxonomyService;
	
	@ApiOperation(value = "createEcomTaxonomy", response = BaseResponseDTO.class)
	@PostMapping(value = "createEcomTaxonomy", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> save(@RequestBody EcommerceTaxonomyDto ecommerceTaxonomyDto)
			throws CusDataIntegrityViolationException, Exception {
		if(ecommerceTaxonomyDto.getId() != null)
			throw new BadRequestException("Id must be null");
		return new ResponseEntity<BaseResponseDTO>(ecomTaxonomyService.save(ecommerceTaxonomyDto), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateEcomTaxonomy", response = BaseResponseDTO.class)
	@PutMapping(value = "updateEcomTaxonomy", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> update(@RequestBody EcommerceTaxonomyDto ecommerceTaxonomyDto)
			throws CusDataIntegrityViolationException, Exception {
		if (ecommerceTaxonomyDto.getVid() == null)
			throw new BadRequestException("Id cannot be null");
		return new ResponseEntity<BaseResponseDTO>(ecomTaxonomyService.update(ecommerceTaxonomyDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
//	@ApiOperation(value = "createEcomTaxonomy", response = BaseResponseDTO.class)
//	@PostMapping("createEcomTaxonomy")
//	public ResponseEntity<BaseResponseDTO> pointsConfiguration(@RequestBody EcommerceTaxonomyDto ecommerceTaxonomyDto)
//			throws CusDataIntegrityViolationException, Exception {
//
//		return new ResponseEntity<BaseResponseDTO>(ecomTaxonomyService.save(ecommerceTaxonomyDto), HttpStatus.OK);
//	}
//	

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllTaxonomy", response = BaseResponseDTO.class)
	@GetMapping(value = "/getAllTaxonomy", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getAllTaxonomy() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomTaxonomyService.getAllTaxonomy(), HttpStatus.OK);
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getTaxonomyByCategoryId", response = BaseResponseDTO.class)
	@GetMapping(value = "/getTaxonomyByCategoryId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getTaxonomyByCategoryId(@RequestParam Long categoryId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomTaxonomyService.getTaxonomyByCategoryId(categoryId), HttpStatus.OK);
	}

	
	@ApiOperation(value = "deletebyvid", response = BaseResponseDTO.class)
	@DeleteMapping(value = "/deletebyvid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> deletebyid(@RequestParam Long vid)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomTaxonomyService.deleteById(vid), HttpStatus.OK);
	}
}
