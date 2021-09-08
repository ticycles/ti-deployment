package com.trackandtrail.controller.ecommercev2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcommerceTaxonomyDto;
import com.trackandtrail.dto.ecommercev2.EcommerceTaxonomyTermDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.service.ecommercev2.EcomTaxonomyTermService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ecomTaxonomyTerm")
public class EcomTaxonomyTermController {
	
	@Autowired
	private EcomTaxonomyTermService ecomTaxonomyTermService;
	
	@ApiOperation(value = "createEcomTaxonomyTerm", response = BaseResponseDTO.class)
	@PostMapping(value = "createEcomTaxonomyTerm", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> save(@RequestBody EcommerceTaxonomyTermDto ecommerceTaxonomyTermDto)
			throws CusDataIntegrityViolationException, Exception {
		if(ecommerceTaxonomyTermDto.getId() != null)
			throw new BadRequestException("Id must be null");
		return new ResponseEntity<BaseResponseDTO>(ecomTaxonomyTermService.save(ecommerceTaxonomyTermDto), HttpStatus.OK);
	}

	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateEcomTaxonomyTerm", response = BaseResponseDTO.class)
	@PutMapping(value = "updateEcomTaxonomyTerm", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> update(@RequestBody EcommerceTaxonomyTermDto ecommerceTaxonomyTermDto)
			throws CusDataIntegrityViolationException, Exception {
		if (ecommerceTaxonomyTermDto.getTid() == null)
			throw new BadRequestException("Id cannot be null");
		return new ResponseEntity<BaseResponseDTO>(ecomTaxonomyTermService.update(ecommerceTaxonomyTermDto), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deleteByTid", response = BaseResponseDTO.class)
	@DeleteMapping(value = "deleteByTid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> deleteByTid(@RequestParam Long tid)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomTaxonomyTermService.deleteById(tid), HttpStatus.OK);
	}

	

}
