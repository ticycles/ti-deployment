package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.http.MediaType;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.ManageDealerDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.service.ManageDealerService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/managedealer")
@CrossOrigin
public class ManageDealerController {

	@Autowired
	ManageDealerService manageDealerService;


	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createManageDealer", response = BaseResponseDTO.class)
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody ManageDealerDto manageDealerDto)
			throws CusDataIntegrityViolationException, Exception {
		if (manageDealerDto.getId() != null)
			throw new BadRequestException("Id must be null");
		return new ResponseEntity<BaseResponseDTO>(manageDealerService.save(manageDealerDto), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateManageDealer", response = BaseResponseDTO.class)
	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> update(@RequestBody ManageDealerDto manageDealersDto)
			throws CusDataIntegrityViolationException, Exception {
		if (manageDealersDto.getId() == null)
			throw new BadRequestException("Id cannot be null");
		return new ResponseEntity<BaseResponseDTO>(manageDealerService.update(manageDealersDto), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getbyid", response = BaseResponseDTO.class)
	@GetMapping(value = "/getbyid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getById(@RequestParam Long id) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(manageDealerService.getById(id), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deletebyid", response = BaseResponseDTO.class)
	@DeleteMapping(value = "/deletebyid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> deletebyid(@RequestParam Long id, @RequestParam Boolean isSoftDelete) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(manageDealerService.deleteById(id, isSoftDelete), HttpStatus.OK);
	}


//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getAllDealers", response = BaseResponseDTO.class)
	@GetMapping(value = "/getall-dealers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getAll() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(manageDealerService.getAll(), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getActiveDealers", response = BaseResponseDTO.class)
	@GetMapping(value = "/getActiveDealers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getActiveDealers() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(manageDealerService.getActiveDealers(), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "searchAndPaginate", response = BaseResponseDTO.class)
	@PostMapping(value = "/searchAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> searchAndPaginate(@RequestBody PaginationDTO paginationDTO) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(manageDealerService.searchAndPaginate(paginationDTO), HttpStatus.OK);
	}


	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "changeStatusById", response = BaseResponseDTO.class)
	@GetMapping(value = "/changeStatusById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> changeStatusById(@RequestParam Long id, @RequestParam Integer statusId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(manageDealerService.changeStatusById(id, statusId), HttpStatus.OK);
	}
}




