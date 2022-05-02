package com.trackandtrail.controller.RestTemplate;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.model.ecommercev2.EcomOrders;
import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.model.ecommercev2.EcomTaxonomy;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;
import com.trackandtrail.service.RestTemplate.DrTaxonomyService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ecomm")
public class DrTaxonomyController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	DrTaxonomyService taxonomy;

	@ApiOperation(value = "taxonomy", response = BaseResponseDTO.class)
	@GetMapping(value = "/taxonomy", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EcomTaxonomy>> getForEntity(String value) throws Exception {
		return new ResponseEntity<List<EcomTaxonomy>>(taxonomy.getTaxonomy(value), HttpStatus.OK);

	}

	
	@ApiOperation(value = "taxonomyTerm", response = BaseResponseDTO.class)
	@GetMapping(value = "/taxonomyTerm", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EcomTaxonomyTerm>> getTaxonomyTerm(@RequestParam String value) throws Exception {
		return new ResponseEntity<List<EcomTaxonomyTerm>>(taxonomy.getTaxonomyTerm(value), HttpStatus.OK);

	}
	
	@ApiOperation(value = "ecomProduct", response = BaseResponseDTO.class)
	@GetMapping(value = "/ecomProduct", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EcomProduct>> getProduct() throws Exception {
		return new ResponseEntity<List<EcomProduct>>(taxonomy.getProduct(), HttpStatus.OK);

	}
	

	@ApiOperation(value = "productVariant", response = BaseResponseDTO.class)
	@GetMapping(value = "/productVariant", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EcomProductVariant>> getProductVariant(@RequestParam String type,@RequestParam String sort,@RequestParam String limit,@RequestParam String offset) throws Exception {
		
		
		return new ResponseEntity<List<EcomProductVariant>>(taxonomy.getProductVariant(type,sort,limit,offset), HttpStatus.OK);

	}
	
	
	@ApiOperation(value = "productStockAvailablity", response = BaseResponseDTO.class)
	@GetMapping(value = "productStockAvailablity", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> productStockAvailablity(@RequestParam String product_id,@RequestParam String pincode) throws Exception {
		
	
		return new ResponseEntity<BaseResponseDTO>(taxonomy.getProductStockavailablityWithPinCode(product_id, pincode),HttpStatus.OK);
	
	}
	
	
	@ApiOperation(value = "getAllordersList", response = BaseResponseDTO.class)
	@GetMapping(value = "/getAllordersList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EcomOrders>> getAllordersList(@RequestParam String sort,@RequestParam String sortOrder,@RequestParam String limit,@RequestParam String offSet,@RequestParam String status) throws Exception {
		return new ResponseEntity<List<EcomOrders>>(taxonomy.getAllordersList(sort,sortOrder,limit,offSet,status), HttpStatus.OK);
	}
	
}
