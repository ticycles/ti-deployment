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
import com.trackandtrail.dto.RegisterBikeDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.service.BikeBrandService;
import com.trackandtrail.service.BikeModelService;
import com.trackandtrail.service.RegisterBikeService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/registerbike")
@CrossOrigin
public class RegisterBikeController {

	@Autowired
	private RegisterBikeService registerBikeService;
	
	
	
	
	
	


//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createRegisterBike", response = BaseResponseDTO.class)
	@PostMapping("/save")
	public ResponseEntity<BaseResponseDTO> create(@RequestBody RegisterBikeDto registerBikeDto) throws Exception {
		if (registerBikeDto.getId() != null)
			throw new BadRequestException("Id cannot contain value");
		return new ResponseEntity<BaseResponseDTO>(registerBikeService.save(registerBikeDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateRegisterBike", response = BaseResponseDTO.class)
	@PutMapping("/update")
	public ResponseEntity<BaseResponseDTO> updateRegisterBike(@RequestBody RegisterBikeDto registerBikeDto) 
			throws CusDataIntegrityViolationException,Exception {
		if (registerBikeDto.getId() == null)
			throw new BadRequestException("Id cannot be null");
		return new ResponseEntity<BaseResponseDTO>(registerBikeService.update(registerBikeDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAllRegisterBike", response = BaseResponseDTO.class)
	@GetMapping("/getall")
	public ResponseEntity<BaseResponseDTO> getAll() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(registerBikeService.getAll(), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getRegisterBikebyid", response = BaseResponseDTO.class)
	@GetMapping("/getby-registerBikeId")
	public ResponseEntity<BaseResponseDTO> getByRegisterBikeId(@RequestParam Long id) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(registerBikeService.getById(id), HttpStatus.OK);
	}


	

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deleteRegisterBikebyid", response = BaseResponseDTO.class)
	@DeleteMapping("/deletebyid")
	public ResponseEntity<BaseResponseDTO> deleteByRegisterBikeId(@RequestParam Long id, @RequestParam Boolean flag)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(registerBikeService.deleteById(id, flag), HttpStatus.OK);
	}

	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
    @ApiOperation(value = "searchAndPaginate", response = BaseResponseDTO.class)
	@PostMapping(value = "/searchAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> searchAndPaginate(@RequestBody PaginationDTO paginationDTO) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(registerBikeService.searchAndPaginate(paginationDTO), HttpStatus.OK);
	}

    
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping(value = "/getByUserid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getByUserId(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(registerBikeService.getByUserId(userId), HttpStatus.OK);
	}


	
	


	
	


}

