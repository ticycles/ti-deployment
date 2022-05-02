package com.trackandtrail.service.impl.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.config.TxNamespaceHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomTaxonomyTermRelationDto;
import com.trackandtrail.model.common.StaticParamModel;
import com.trackandtrail.model.ecommercev2.EcomOrders;
import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.ecommercev2.EcomProductSpecification;
import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.model.ecommercev2.EcomTaxonomy;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyRelationship;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;
import com.trackandtrail.repository.ecommercev2.EcomOrdersRepository;
import com.trackandtrail.repository.ecommercev2.EcomProductRepository;
import com.trackandtrail.repository.ecommercev2.EcomProductVariantRepository;
import com.trackandtrail.repository.ecommercev2.EcomProductVariantSpecRepo;
import com.trackandtrail.repository.ecommercev2.EcomTaxonomyRelationshipRepo;
import com.trackandtrail.repository.ecommercev2.EcomTaxonomyRepository;
import com.trackandtrail.repository.ecommercev2.EcomTaxonomyTermRepository;
import com.trackandtrail.service.RestTemplate.DrTaxonomyService;
import com.trackandtrail.util.RequestStatusUtil;

@Service
public class DrTaxonomyServiceImpl implements DrTaxonomyService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	EcomTaxonomyRepository taxRepo;

	@Autowired
	EcomProductRepository ecomProductRepository;

	@Autowired
	EcomProductVariantRepository ecomProductVariantRepository;

	@Autowired
	EcomTaxonomyTermRepository ecomTaxonomyTermRepository;

	@Autowired
	EcomOrdersRepository ecomOrdersRepository;
	
	@Autowired
	EcomProductVariantSpecRepo ecomProductVariantSpecRepo;
	
	@Autowired
	EcomTaxonomyRelationshipRepo ecomTaxonomyRelationshipRepo;
	

	private JsonNode colour;

	/*
	 * Prepare HTTP Headers.
	 */
	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	/*
	 * Add HTTP Authorization header, using Basic-Authentication to send
	 * client-credentials.
	 */
	private static HttpHeaders getHeadersWithClientCredentials() {
		String plainClientCredentials = "nikhilar_7463:ktCm5xHV";
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

		HttpHeaders headers = getHeaders();
		headers.add("Authorization", "Basic " + base64ClientCredentials);
		return headers;
	}

	@Override
	public List<EcomTaxonomy> getTaxonomy(String value) {
		
//		url="https://www.trackandtrail.in/api/v1/taxonomy_vocabulary?page=0"
		
		String url = "https://www.trackandtrail.in/api/v1/taxonomy_vocabulary?";
		String finalurl=url.concat("page").concat("=").concat(value);
		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
		ResponseEntity<String> resp = restTemplate.exchange(finalurl, HttpMethod.GET, request, String.class);
		List<EcomTaxonomy> ts = new ArrayList<EcomTaxonomy>();
		try {
			JsonNode listofTaxon = objectMapper.readTree(resp.getBody());
			System.out.println(listofTaxon.toPrettyString());
			listofTaxon.forEach(o -> {
				EcomTaxonomy tx = new EcomTaxonomy();
				tx.setId(o.get("vid").asLong());
				tx.setTaxonomyName(o.get("name").textValue());
				tx.setVid(o.get("vid").asLong());
				ts.add(tx);
				taxRepo.saveAll(ts);
			});
			//taxRepo.saveAll(ts);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ts;
	}

	@Override
	public List<EcomTaxonomyTerm> getTaxonomyTerm(String value) {

		String url = "https://www.trackandtrail.in/api/v1/taxonomy_term?name=accessories_brands";
		String finalurl=url.concat("&").concat("page").concat("=").concat(value);
		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
		ResponseEntity<String> resp = restTemplate.exchange(finalurl, HttpMethod.GET, request, String.class);

		List<EcomTaxonomyTerm> ts = new ArrayList<EcomTaxonomyTerm>();

		try {
			JsonNode listofTaxon = objectMapper.readTree(resp.getBody());

			System.out.println(listofTaxon.toPrettyString());

			listofTaxon.forEach(o -> {
               
				Optional<EcomTaxonomy> et = Optional.ofNullable(new EcomTaxonomy());
				EcomTaxonomyTerm tx = new EcomTaxonomyTerm();

				tx.setId(o.get("tid").asLong());
				tx.setTermName(o.get("name").textValue());
				tx.setTid(o.get("tid").asLong());
				et = taxRepo.findByVid(o.get("vid").asLong());
				tx.setTaxonomy(et.get());
				if(et.isPresent()) {
					tx.setTaxonomy(et.get());
				System.out.println("*********tid*******"+tx.getTid());
				ecomTaxonomyTermRepository.save(tx);
				}
				//ecomTaxonomyTermRepository.saveAll(tx);
			});

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return ts;

	}

	@Override
	public List<EcomProduct> getProduct() {
		String url = "https://www.trackandtrail.in/api/v1/product-display/142415";

		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
		ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		List<EcomProduct> ecom = new ArrayList<EcomProduct>();

		try {
			JsonNode o = objectMapper.readTree(resp.getBody());

			System.out.println(o.toPrettyString());

			EcomProduct ecomProduct = new EcomProduct();

			ecomProduct.setId(o.get("uid").asLong());
			ecomProduct.setProductName(o.get("title").textValue());

			ecom.add(ecomProduct);
//				ecomProductRepository.saveAll(ecom);

		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ecom;

	}

	@Override
	public List<EcomProductVariant> getProductVariant(String type, String sort, String limit, String offset) {
		String url = "https://www.trackandtrail.in/api/v1/product-display?sort_by=created&";
		String value = url.concat("filter[type]").concat("=").concat(type).concat("&").concat("sort_order").concat("=")
				.concat(sort).concat("&").concat("limit").concat("=").concat(limit).concat("&").concat("offset")
				.concat("=").concat(offset);

		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
		ResponseEntity<String> response = restTemplate.exchange(value, HttpMethod.GET, request, String.class);
		
		List<EcomProductVariant> varient = new ArrayList<EcomProductVariant>();

		try {
			JsonNode listOfProdcutVarient = objectMapper.readTree(response.getBody());

			listOfProdcutVarient.fieldNames().forEachRemaining((String fieldName) -> {

				if (!listOfProdcutVarient.isEmpty()) {

					JsonNode item = listOfProdcutVarient.get(fieldName);	
					
			
//				           **** create product ****
					EcomProduct product = new EcomProduct();
					product.setProductName(item.get("title")==null ? null : item.get("title").asText());
					product.setVid(item.get("nid")==null ? null : item.get("nid").asLong());			
                     
//			           ***8 set product category from static param model ****
					StaticParamModel pm = new StaticParamModel();
					if (type.equals("bikes")) {
						pm.setId(1L);
					} else if (type.equals("fitness_equipment")) {
						pm.setId(2L);
					} else if (type.equals("accessories")) {
						pm.setId(3L);
					}
					product.setCategory(pm);	
					System.out.println("*************PRODUCT*************"+product);
					ecomProductRepository.save(product);
					
					
					
					
					/*
					 * Product Specification
					 * Static Param => Lable : Specification Name .eg. Persona, Road Type
					 * Ecom Variant/ Product Spec
					 * 
					 * */
					
					// *********** 1. Persona starts ************************** /
					EcomProductSpecification specificationpersona = new EcomProductSpecification();
					specificationpersona.setProduct(product);					
					System.out.println("*************specificationpersona*************"+specificationpersona);
					StaticParamModel specStaticParampersona = new StaticParamModel();									
					if(item.get("field_persona").asText() != null ) {
						specStaticParampersona.setId(49L);						
						specificationpersona.setSpecKey(specStaticParampersona);						
					}
					ecomProductVariantSpecRepo.save(specificationpersona);	
					// *********** 1. Persona ends ************************** /
					// *********** 2. field_gears starts ************************** /
					EcomProductSpecification specificationgears = new EcomProductSpecification();
					specificationgears.setProduct(product);
					StaticParamModel speciStaticParamgears = new StaticParamModel();						
					if(item.get("field_gears").asText() != null ) {
						speciStaticParamgears.setId(67L);
						specificationgears.setSpecKey(speciStaticParamgears);
					}
					 ecomProductVariantSpecRepo.save(specificationgears);
					// *********** 2. field_gears ends ************************** /
					// *********** 3. field_features starts ************************** /
					EcomProductSpecification specificationfeatures = new EcomProductSpecification();
					specificationfeatures.setProduct(product);
					StaticParamModel speciStaticParamfeatures = new StaticParamModel();											
					if(item.get("field_features").asText() != null ) {
					    speciStaticParamfeatures.setId(50L);
					    specificationfeatures.setSpecKey(speciStaticParamfeatures);
					}
					ecomProductVariantSpecRepo.save(specificationfeatures);
					// *********** 3. field_features ends ************************** /	
					// *********** 4. field_target_gender starts ************************** /
					EcomProductSpecification specificationgender = new EcomProductSpecification();
					specificationgender.setProduct(product);
					StaticParamModel speciStaticParamgender = new StaticParamModel();											
					if(item.get("field_target_gender").asText() != null ) {
						speciStaticParamgender.setId(51L);
						specificationgender.setSpecKey(speciStaticParamgender);
					}
					ecomProductVariantSpecRepo.save(specificationgender);
					// *********** 4. field_target_gender ends ************************** /
					// *********** 5. field_frame starts ************************** /
					EcomProductSpecification specificationframe = new EcomProductSpecification();
					specificationframe.setProduct(product);
					StaticParamModel speciStaticParamframe = new StaticParamModel();											
					if(item.get("field_frame").asText() != null ) {
						speciStaticParamframe.setId(4L);
						specificationframe.setSpecKey(speciStaticParamframe);
					}
					ecomProductVariantSpecRepo.save(specificationframe);
					// *********** 5. field_frame ends ************************** /
			 
					// *********** 6. field_crank starts ************************** /
					EcomProductSpecification specificationcrank = new EcomProductSpecification();
					specificationcrank.setProduct(product);
					StaticParamModel specStaticParamcrank = new StaticParamModel();									
					if(item.get("field_crank").asText() != null ) {
						specStaticParamcrank.setId(5L);
						specificationcrank.setSpecKey(specStaticParamcrank);
					}
					ecomProductVariantSpecRepo.save(specificationcrank);
					// *********** 6. field_crank ends ************************** /
					// *********** 7. field_shifters starts ************************** /
					EcomProductSpecification specificationshifters = new EcomProductSpecification();
					specificationshifters.setProduct(product);
					StaticParamModel speciStaticParamshifters = new StaticParamModel();						
					if(item.get("field_shifters").asText() != null ) {
						speciStaticParamshifters.setId(6L);
						specificationshifters.setSpecKey(speciStaticParamshifters);
					}
					 ecomProductVariantSpecRepo.save(specificationshifters);
					// *********** 7. field_shifters ends ************************** /
					// *********** 8. field_cog_set starts ************************** /
					EcomProductSpecification specificationcogset = new EcomProductSpecification();
					specificationcogset.setProduct(product);
					StaticParamModel speciStaticParamcogset = new StaticParamModel();											
					if(item.get("field_cog_set").asText() != null ) {
					    speciStaticParamcogset.setId(52L);
					    specificationcogset.setSpecKey(speciStaticParamcogset);
					}
					ecomProductVariantSpecRepo.save(specificationcogset);
					// *********** 8. field_cog_set ends ************************** /	
					// *********** 9. field_chain starts ************************** /
					EcomProductSpecification specificationchain = new EcomProductSpecification();
					specificationchain.setProduct(product);
					StaticParamModel speciStaticParamchain = new StaticParamModel();											
					if(item.get("field_chain").asText() != null ) {
						speciStaticParamchain.setId(54L);
						specificationchain.setSpecKey(speciStaticParamchain);
					}
					ecomProductVariantSpecRepo.save(specificationchain);
					// *********** 9. field_chain ends ************************** /
					// *********** 10. field_rims starts ************************** /
					EcomProductSpecification specificationrims = new EcomProductSpecification();
					specificationrims.setProduct(product);
					StaticParamModel speciStaticParamrims = new StaticParamModel();											
					if(item.get("field_rims").asText() != null ) {
						speciStaticParamrims.setId(7L);
						specificationrims.setSpecKey(speciStaticParamrims);
					}
					ecomProductVariantSpecRepo.save(specificationrims);
					// *********** 10. field_rims ends ************************** /
					// *********** 11. field_hubs starts ************************** /
					EcomProductSpecification specificationhubs = new EcomProductSpecification();
					specificationhubs.setProduct(product);
					StaticParamModel specStaticParamhubs = new StaticParamModel();									
					if(item.get("field_hubs").asText() != null ) {
						specStaticParamhubs.setId(53L);
						specificationhubs.setSpecKey(specStaticParamhubs);
					}
					ecomProductVariantSpecRepo.save(specificationhubs);
					// *********** 11. field_hubs ends ************************** /
					// *********** 12. field_tires starts ************************** /
					EcomProductSpecification specificationtires = new EcomProductSpecification();
					specificationtires.setProduct(product);
					StaticParamModel speciStaticParamtires = new StaticParamModel();						
					if(item.get("field_tires").asText() != null ) {
						speciStaticParamtires.setId(16L);
						specificationtires.setSpecKey(speciStaticParamtires);
					}
					 ecomProductVariantSpecRepo.save(specificationtires);
					// *********** 12. field_tires ends ************************** /
					// *********** 13. field_pedals starts ************************** /
					EcomProductSpecification specificationpedals = new EcomProductSpecification();
					specificationpedals.setProduct(product);
					StaticParamModel speciStaticParampedals = new StaticParamModel();											
					if(item.get("field_pedals").asText() != null ) {
					    speciStaticParampedals.setId(50L);
					    specificationpedals.setSpecKey(speciStaticParampedals);
					}
					ecomProductVariantSpecRepo.save(specificationpedals);
					// *********** 13. field_pedals ends ************************** /	
					// *********** 14. field_brakes starts ************************** /
					EcomProductSpecification specificationbrakes = new EcomProductSpecification();
					specificationbrakes.setProduct(product);
					StaticParamModel speciStaticParambrakes = new StaticParamModel();											
					if(item.get("field_brakes").asText() != null ) {
						speciStaticParambrakes.setId(18L);
						specificationbrakes.setSpecKey(speciStaticParambrakes);
					}
					ecomProductVariantSpecRepo.save(specificationbrakes);
					// *********** 14. field_brakes ends ************************** /
					// *********** 15. field_handlebar starts ************************** /
					EcomProductSpecification specificationhandlebar = new EcomProductSpecification();
					specificationhandlebar.setProduct(product);
					StaticParamModel speciStaticParamhandlebar = new StaticParamModel();											
					if(item.get("field_handlebar").asText() != null ) {
						speciStaticParamhandlebar.setId(4L);
						specificationhandlebar.setSpecKey(speciStaticParamhandlebar);
					}
					ecomProductVariantSpecRepo.save(specificationhandlebar);
					// *********** 15. field_handlebar ends ************************** /
			 
					// *********** 16. field_stem starts ************************** /
					EcomProductSpecification specificationstem = new EcomProductSpecification();
					specificationstem.setProduct(product);
					StaticParamModel specStaticParamstem = new StaticParamModel();									
					if(item.get("field_stem").asText() != null ) {
						specStaticParamstem.setId(5L);
						specificationstem.setSpecKey(specStaticParamstem);
					}
					ecomProductVariantSpecRepo.save(specificationstem);
					// *********** 16. field_stem ends ************************** /
					// *********** 17. field_headset starts ************************** /
					EcomProductSpecification specificationheadset = new EcomProductSpecification();
					specificationheadset.setProduct(product);
					StaticParamModel speciStaticParamheadset = new StaticParamModel();						
					if(item.get("field_headset").asText() != null ) {
						speciStaticParamheadset.setId(6L);
						specificationheadset.setSpecKey(speciStaticParamheadset);
					}
					 ecomProductVariantSpecRepo.save(specificationheadset);
					// *********** 17. field_headset ends ************************** /
					// *********** 18. field_grips starts ************************** /
					EcomProductSpecification specificationgrips = new EcomProductSpecification();
					specificationgrips.setProduct(product);
					StaticParamModel speciStaticParamgrips = new StaticParamModel();											
					if(item.get("field_grips").asText() != null ) {
					    speciStaticParamgrips.setId(21L);
					    specificationgrips.setSpecKey(speciStaticParamgrips);
					}
					ecomProductVariantSpecRepo.save(specificationgrips);
					// *********** 18. field_grips ends ************************** /	
					// *********** 19. field_saddle starts ************************** /
					EcomProductSpecification specificationsaddle = new EcomProductSpecification();
					specificationsaddle.setProduct(product);
					StaticParamModel speciStaticParamsaddle = new StaticParamModel();											
					if(item.get("field_saddle").asText() != null ) {
						speciStaticParamsaddle.setId(22L);
						specificationsaddle.setSpecKey(speciStaticParamsaddle);
					}
					ecomProductVariantSpecRepo.save(specificationsaddle);
					// *********** 19. field_saddle ends ************************** /
					// *********** 20. field_fork starts ************************** /
					EcomProductSpecification specificationfork = new EcomProductSpecification();
					specificationfork.setProduct(product);
					StaticParamModel speciStaticParamfork = new StaticParamModel();											
					if(item.get("field_fork").asText() != null ) {
						speciStaticParamfork.setId(23L);
						specificationfork.setSpecKey(speciStaticParamfork);
					}
					ecomProductVariantSpecRepo.save(specificationfork);
					// *********** 20. field_fork ends ************************** /
					// *********** 21. field_front_derailleur starts **************************  /
					EcomProductSpecification specificationfrontderailleur = new EcomProductSpecification();
					specificationfrontderailleur.setProduct(product);
					StaticParamModel specStaticParamfrontderailleur = new StaticParamModel();									
					if(item.get("field_front_derailleur").asText() != null ) {
						specStaticParamfrontderailleur.setId(15L);
						specificationfrontderailleur.setSpecKey(specStaticParamfrontderailleur);
					}
					ecomProductVariantSpecRepo.save(specificationfrontderailleur);
					// *********** 21. field_front_derailleur ends ************************** /
					// *********** 22. field_rear_derailleur starts ************************** /
					EcomProductSpecification specificationrearderailleur = new EcomProductSpecification();
					specificationrearderailleur.setProduct(product);
					StaticParamModel speciStaticParamrearderailleur = new StaticParamModel();						
					if(item.get("field_rear_derailleur").asText() != null ) {
						speciStaticParamrearderailleur.setId(28L);
						specificationrearderailleur.setSpecKey(speciStaticParamrearderailleur);
					}
					 ecomProductVariantSpecRepo.save(specificationrearderailleur);
					// *********** 22. field_rear_derailleur ends ************************** /
					// *********** 23. field_brake_levers starts ************************** /
					EcomProductSpecification specificationbrakelevers = new EcomProductSpecification();
					specificationbrakelevers.setProduct(product);
					StaticParamModel speciStaticParambrakelevers = new StaticParamModel();											
					if(item.get("field_brake_levers").asText() != null ) {
					    speciStaticParambrakelevers.setId(19L);
					    specificationbrakelevers.setSpecKey(speciStaticParambrakelevers);
					}
					ecomProductVariantSpecRepo.save(specificationbrakelevers);
					// *********** 23. field_brake_levers ends ************************** /	
					// *********** 24. field_seat_post starts ************************** /
					EcomProductSpecification specificationseatpost = new EcomProductSpecification();
					specificationseatpost.setProduct(product);
					StaticParamModel speciStaticParamseatpost = new StaticParamModel();											
					if(item.get("field_seat_post").asText() != null ) {
						speciStaticParamseatpost.setId(48L);
						specificationseatpost.setSpecKey(speciStaticParamseatpost);
					}
					ecomProductVariantSpecRepo.save(specificationseatpost);
					// *********** 24. field_seat_post ends ************************** /
					// *********** 25. field_left_shifter starts ************************** /
					EcomProductSpecification specificationleftshifter = new EcomProductSpecification();
					specificationleftshifter.setProduct(product);
					StaticParamModel speciStaticParamleftshifter = new StaticParamModel();											
					if(item.get("field_left_shifter").asText() != null ) {
						speciStaticParamleftshifter.setId(6L);
					}
					ecomProductVariantSpecRepo.save(specificationleftshifter);
					// *********** 25. field_left_shifter ends ************************** /
			 
					// *********** 26. field_right_shifter starts ************************** /
					EcomProductSpecification specificationrightshifter = new EcomProductSpecification();
					specificationrightshifter.setProduct(product);
					StaticParamModel specStaticParamrightshifter = new StaticParamModel();									
					if(item.get("field_right_shifter").asText() != null ) {
						specStaticParamrightshifter.setId(57L);
						specificationrightshifter.setSpecKey(specStaticParamrightshifter);
					}
					ecomProductVariantSpecRepo.save(specificationrightshifter);
					// *********** 26. field_right_shifter ends ************************** /
					// *********** 27. field_free_wheel starts ************************** /
					EcomProductSpecification specificationfreewheel = new EcomProductSpecification();
					specificationfreewheel.setProduct(product);
					StaticParamModel speciStaticParamfreewheel = new StaticParamModel();						
					if(item.get("field_free_wheel").asText() != null ) {
						speciStaticParamfreewheel.setId(58L);
						specificationfreewheel.setSpecKey(speciStaticParamfreewheel);
					}
					 ecomProductVariantSpecRepo.save(specificationfreewheel);
					// *********** 27. field_free_wheel ends ************************** /
					// *********** 28. field_chain_wheel starts ************************** /
					EcomProductSpecification specificationchainwheel = new EcomProductSpecification();
					specificationchainwheel.setProduct(product);
					StaticParamModel speciStaticParamchainwheel = new StaticParamModel();											
					if(item.get("field_chain_wheel").asText() != null ) {
					    speciStaticParamchainwheel.setId(24L);
					    specificationchainwheel.setSpecKey(speciStaticParamchainwheel);
					}
					ecomProductVariantSpecRepo.save(specificationchainwheel);
					// *********** 28. field_chain_wheel ends ************************** /	
					// *********** 29. field_bottom_bracket starts ************************** /
					EcomProductSpecification specificationbottombracket = new EcomProductSpecification();
					specificationbottombracket.setProduct(product);
					StaticParamModel speciStaticParambottombracket = new StaticParamModel();											
					if(item.get("field_bottom_bracket").asText() != null ) {
						speciStaticParambottombracket.setId(59L);
						specificationbottombracket.setSpecKey(speciStaticParambottombracket);
					}
					ecomProductVariantSpecRepo.save(specificationbottombracket);
					// *********** 29. field_bottom_bracket ends ************************** /
					// *********** 30. field_onlyf_eature starts ************************** /
					EcomProductSpecification specificationonlyfeature = new EcomProductSpecification();
					specificationonlyfeature.setProduct(product);
					StaticParamModel speciStaticParamonlyfeature = new StaticParamModel();											
					if(item.get("field_only_feature").asText() != null ) {
						speciStaticParamonlyfeature.setId(60L);
						specificationonlyfeature.setSpecKey(speciStaticParamonlyfeature);
					}
					ecomProductVariantSpecRepo.save(specificationonlyfeature);
					// *********** 30. field_only_feature ends ************************** /
					// *********** 31. field_chain_covers starts ************************** /
					EcomProductSpecification specificationchaincovers = new EcomProductSpecification();
					specificationchaincovers.setProduct(product);
					StaticParamModel specStaticParamchaincovers = new StaticParamModel();									
					if(item.get("field_chain_covers").asText() != null ) {
						specStaticParamchaincovers.setId(9L);
						specificationchaincovers.setSpecKey(specStaticParamchaincovers);
					}
					ecomProductVariantSpecRepo.save(specificationchaincovers);
					// *********** 31. field_chain_covers ends ************************** /
					// *********** 32. field_brakes_set starts ************************** /
					EcomProductSpecification specificationbrakesset = new EcomProductSpecification();
					specificationbrakesset.setProduct(product);
					StaticParamModel speciStaticParambrakesset = new StaticParamModel();						
					if(item.get("field_brakes_set").asText() != null ) {
						speciStaticParambrakesset.setId(29L);
						specificationbrakesset.setSpecKey(speciStaticParambrakesset);
					}
					 ecomProductVariantSpecRepo.save(specificationbrakesset);
					// *********** 32. field_brakes_set ends ************************** /
					// *********** 33. field_brakes_front starts ************************** /
					EcomProductSpecification specificationbrakesfront = new EcomProductSpecification();
					specificationbrakesfront.setProduct(product);
					StaticParamModel speciStaticParambrakesfront = new StaticParamModel();											
					if(item.get("field_brakes_front").asText() != null ) {
					    speciStaticParambrakesfront.setId(62L);
					    specificationbrakesfront.setSpecKey(speciStaticParambrakesfront);
					}
					ecomProductVariantSpecRepo.save(specificationbrakesfront);
					// *********** 33. field_brakes_front ends ************************** /	
					// *********** 34. field_brake_rear starts ************************** /
					EcomProductSpecification specificationbrakerear = new EcomProductSpecification();
					specificationbrakes.setProduct(product);
					StaticParamModel speciStaticParambrakerear = new StaticParamModel();											
					if(item.get("field_brake_rear").asText() != null ) {
						speciStaticParambrakerear.setId(61L);
						specificationbrakerear.setSpecKey(speciStaticParambrakerear);
					}
					ecomProductVariantSpecRepo.save(specificationbrakerear);
					// *********** 34. field_brake_rear ends ************************** /
					// *********** 35. field_stand starts ************************** /
					EcomProductSpecification specificationstand = new EcomProductSpecification();
					specificationstand.setProduct(product);
					StaticParamModel speciStaticParamstand = new StaticParamModel();											
					if(item.get("field_stand").asText() != null ) {
						speciStaticParamstand.setId(63L);
						specificationstand.setSpecKey(speciStaticParamstand);
					}
					ecomProductVariantSpecRepo.save(specificationstand);
					// *********** 35. field_stand ends ************************** /
			 
					// *********** 36. field_mudguards starts ************************** /
					EcomProductSpecification specificationmudguards = new EcomProductSpecification();
					specificationmudguards.setProduct(product);
					StaticParamModel specStaticParammudguards = new StaticParamModel();									
					if(item.get("field_mudguards").asText() != null ) {
						specStaticParammudguards.setId(8L);
						specificationmudguards.setSpecKey(specStaticParammudguards);
					}
					ecomProductVariantSpecRepo.save(specificationmudguards);
					// *********** 36. field_mudguards ends ************************** /
					// *********** 37. field_seat_clamps starts ************************** /
					EcomProductSpecification specificationseatclamps = new EcomProductSpecification();
					specificationseatclamps.setProduct(product);
					StaticParamModel speciStaticParamseatclamps = new StaticParamModel();						
					if(item.get("field_seat_clamps").asText() != null ) {
						speciStaticParamseatclamps.setId(55L);
						specificationseatclamps.setSpecKey(speciStaticParamseatclamps);
					}
					 ecomProductVariantSpecRepo.save(specificationseatclamps);
					// *********** 37. field_seat_clamps ends ************************** /
					// *********** 38. field_basket starts ************************** /
					EcomProductSpecification specificationbasket = new EcomProductSpecification();
					specificationbasket.setProduct(product);
					StaticParamModel speciStaticParambasket = new StaticParamModel();											
					if(item.get("field_basket").asText() != null ) {
					    speciStaticParambasket.setId(64L);
					    specificationbasket.setSpecKey(speciStaticParambasket);
					}
					ecomProductVariantSpecRepo.save(specificationbasket);
					// *********** 38. field_basket ends ************************** /	
					// *********** 39. field_suspension starts ************************** /
					EcomProductSpecification specificationsuspension = new EcomProductSpecification();
					specificationsaddle.setProduct(product);
					StaticParamModel speciStaticParamsuspension = new StaticParamModel();											
					if(item.get("field_suspension").asText() != null ) {
						speciStaticParamsuspension.setId(65L);
						specificationsuspension.setSpecKey(speciStaticParamsuspension);
					}
					ecomProductVariantSpecRepo.save(specificationsuspension);
					// *********** 39. field_suspension ends ************************** /
					// *********** 40. field_carrier starts ************************** /
					EcomProductSpecification specificationcarrier = new EcomProductSpecification();
					specificationcarrier.setProduct(product);
					StaticParamModel speciStaticParamcarrier = new StaticParamModel();											
					if(item.get("field_carrier").asText() != null ) {
						speciStaticParamcarrier.setId(66L);
						specificationcarrier.setSpecKey(speciStaticParamcarrier);
					}
					ecomProductVariantSpecRepo.save(specificationcarrier);
					// *********** 40. field_carrier ends ************************** /
					
					// *********** 41. field_product_shifters starts ************************** /
					EcomProductSpecification specificationproductshifters = new EcomProductSpecification();
					specificationproductshifters.setProduct(product);
					StaticParamModel specStaticParamproductshifters = new StaticParamModel();									
					if(item.get("field_product_shifters").asText() != null ) {
						specStaticParamproductshifters.setId(49L);
						specificationproductshifters.setSpecKey(specStaticParamproductshifters);
					}
					ecomProductVariantSpecRepo.save(specificationproductshifters);
					// *********** 41. field_product_shifters ends ************************** /
					// *********** 42. field_barends starts ************************** /
					EcomProductSpecification specificationbarends = new EcomProductSpecification();
					specificationbarends.setProduct(product);
					StaticParamModel speciStaticParambarends = new StaticParamModel();						
					if(item.get("field_barends").asText() != null ) {
						speciStaticParambarends.setId(27L);
						specificationbarends.setSpecKey(speciStaticParambarends);
					}
					 ecomProductVariantSpecRepo.save(specificationbarends);
					// *********** 42. field_barends ends ************************** /
					// *********** 43. field_foam_pad starts ************************** /
					EcomProductSpecification specificationfoampad = new EcomProductSpecification();
					specificationfoampad.setProduct(product);
					StaticParamModel speciStaticParamfoampad = new StaticParamModel();											
					if(item.get("field_foam_pad").asText() != null ) {
						speciStaticParamfoampad.setId(50L);
						specificationfoampad.setSpecKey(speciStaticParamfoampad);
					}
					ecomProductVariantSpecRepo.save(specificationfoampad);
					// *********** 43. field_foam_pad ends ************************** /	
					// *********** 44. field_cable starts ************************** /
					EcomProductSpecification specificationcable = new EcomProductSpecification();
					specificationcable.setProduct(product);
					StaticParamModel speciStaticParamcable = new StaticParamModel();											
					if(item.get("field_cable").asText() != null ) {
						speciStaticParamcable.setId(51L);
						specificationcable.setSpecKey(speciStaticParamcable);
					}
					ecomProductVariantSpecRepo.save(specificationcable);
					// *********** 44. field_cable ends ************************** /
					// *********** 45. field_backrest starts ************************** /
					EcomProductSpecification specificationbackrest = new EcomProductSpecification();
					specificationbackrest.setProduct(product);
					StaticParamModel speciStaticParambackrest = new StaticParamModel();											
					if(item.get("field_backrest").asText() != null ) {
						speciStaticParambackrest.setId(4L);
						specificationbackrest.setSpecKey(speciStaticParambackrest);
					}
					ecomProductVariantSpecRepo.save(specificationbackrest);
					// *********** 45. field_backrest ends ************************** /
			 
					// *********** 46. field_trainer_wheel starts ************************** /
					EcomProductSpecification specificationtrainerwheel = new EcomProductSpecification();
					specificationtrainerwheel.setProduct(product);
					StaticParamModel specStaticParamtrainerwheel = new StaticParamModel();									
					if(item.get("field_trainer_wheel").asText() != null ) {
						specStaticParamtrainerwheel.setId(5L);
						specificationtrainerwheel.setSpecKey(specStaticParamtrainerwheel);
					}
					ecomProductVariantSpecRepo.save(specificationtrainerwheel);
					// *********** 46. field_trainer_wheel ends ************************** /
					// *********** 47. field_saddle_adjustment starts ************************** /
					EcomProductSpecification specificationsaddleadjustment = new EcomProductSpecification();
					specificationsaddleadjustment.setProduct(product);
					StaticParamModel speciStaticParamsaddleadjustment = new StaticParamModel();						
					if(item.get("field_saddle_adjustment").asText() != null ) {
						speciStaticParamsaddleadjustment.setId(6L);
						specificationsaddleadjustment.setSpecKey(speciStaticParamsaddleadjustment);
					}
					 ecomProductVariantSpecRepo.save(specificationsaddleadjustment);
					// *********** 47. field_saddle_adjustment ends ************************** /
					// *********** 48. field_frame_height starts ************************** /
					EcomProductSpecification specificationframeheight = new EcomProductSpecification();
					specificationframeheight.setProduct(product);
					StaticParamModel speciStaticParamframeheight = new StaticParamModel();											
					if(item.get("field_frame_height").asText() != null ) {
						speciStaticParamframeheight.setId(82L);
						specificationframeheight.setSpecKey(speciStaticParamframeheight);
					}
					ecomProductVariantSpecRepo.save(specificationframeheight);
					// *********** 48. field_frame_height ends ************************** /	
					// *********** 49. field_head_fittings starts ************************** /
					EcomProductSpecification specificationheadfittings = new EcomProductSpecification();
					specificationheadfittings.setProduct(product);
					StaticParamModel speciStaticParamheadfittings = new StaticParamModel();											
					if(item.get("field_head_fittings").asText() != null ) {
						speciStaticParamheadfittings.setId(81L);
						specificationheadfittings.setSpecKey(speciStaticParamheadfittings);
					}
					ecomProductVariantSpecRepo.save(specificationheadfittings);
					// *********** 49. field_head_fittings ends ************************** /
					// *********** 50. field_extras starts ************************** /
					EcomProductSpecification specificationextras = new EcomProductSpecification();
					specificationextras.setProduct(product);
					StaticParamModel speciStaticParamextras = new StaticParamModel();											
					if(item.get("field_extras").asText() != null ) {
						speciStaticParamextras.setId(80L);
						specificationextras.setSpecKey(speciStaticParamextras);
					}
					ecomProductVariantSpecRepo.save(specificationextras);
					// *********** 50. field_extras ends ************************** /
					// *********** 51. field_cockpit starts ************************** /
					EcomProductSpecification specificationcockpit = new EcomProductSpecification();
					specificationcockpit.setProduct(product);
					StaticParamModel specStaticParamcockpit = new StaticParamModel();									
					if(item.get("field_cockpit").asText() != null ) {
						specStaticParamcockpit.setId(79L);
						specificationcockpit.setSpecKey(specStaticParamcockpit);
					}
					ecomProductVariantSpecRepo.save(specificationcockpit);
					// *********** 51. field_cockpit ends ************************** /
					// *********** 52. field_gear_system starts ************************** /
					EcomProductSpecification specificationgearsystem = new EcomProductSpecification();
					specificationgearsystem.setProduct(product);
					StaticParamModel speciStaticParamgearsystem = new StaticParamModel();						
					if(item.get("field_gear_system").asText() != null ) {
						speciStaticParamgearsystem.setId(78L);
						specificationgearsystem.setSpecKey(speciStaticParamgearsystem);
					}
					 ecomProductVariantSpecRepo.save(specificationgearsystem);
					// *********** 52. field_gear_system ends ************************** /
					// *********** 53. field_front_suspension starts ************************** /
					EcomProductSpecification specificationfrontsuspension = new EcomProductSpecification();
					specificationfrontsuspension.setProduct(product);
					StaticParamModel speciStaticParamfrontsuspension = new StaticParamModel();											
					if(item.get("field_front_suspension").asText() != null ) {
						speciStaticParamfrontsuspension.setId(30L);
						specificationfrontsuspension.setSpecKey(speciStaticParamfrontsuspension);
					}
					ecomProductVariantSpecRepo.save(specificationfrontsuspension);
					// *********** 53. field_front_suspension ends ************************** /	
					// *********** 54. field_wheel_set starts ************************** /
					EcomProductSpecification specificationwheelset = new EcomProductSpecification();
					specificationwheelset.setProduct(product);
					StaticParamModel speciStaticParamwheelset = new StaticParamModel();											
					if(item.get("field_wheel_set").asText() != null ) {
						speciStaticParamwheelset.setId(47L);
						specificationwheelset.setSpecKey(speciStaticParamwheelset);
					}
					ecomProductVariantSpecRepo.save(specificationwheelset);
					// *********** 54. field_wheel_set ends ************************** /
					// *********** 55. field_braking starts ************************** /
					EcomProductSpecification specificationbraking = new EcomProductSpecification();
					specificationbraking.setProduct(product);
					StaticParamModel speciStaticParambraking = new StaticParamModel();											
					if(item.get("field_braking").asText() != null ) {
						speciStaticParambraking.setId(18L);
						specificationbraking.setSpecKey(speciStaticParambraking);
					}
					ecomProductVariantSpecRepo.save(specificationbraking);
					// *********** 55. field_braking ends ************************** /
			 
					// *********** 56. field_seating starts ************************** /
					EcomProductSpecification specificationseating = new EcomProductSpecification();
					specificationseating.setProduct(product);
					StaticParamModel specStaticParamseating = new StaticParamModel();									
					if(item.get("field_seating").asText() != null ) {
						specStaticParamseating.setId(56L);
						specificationseating.setSpecKey(specStaticParamseating);
					}
					ecomProductVariantSpecRepo.save(specificationseating);
					// *********** 56. field_seating ends ************************** /
					// *********** 57. field_derailleurs starts ************************** /
					EcomProductSpecification specificationderailleurs = new EcomProductSpecification();
					specificationderailleurs.setProduct(product);
					StaticParamModel speciStaticParamderailleurs = new StaticParamModel();						
					if(item.get("field_derailleurs").asText() != null ) {
						speciStaticParamderailleurs.setId(77L);
						specificationderailleurs.setSpecKey(speciStaticParamderailleurs);
					}
					 ecomProductVariantSpecRepo.save(specificationderailleurs);
					// *********** 57. field_derailleurs ends ************************** /
					// *********** 58. field_spokes starts ************************** /
					EcomProductSpecification specificationspokes = new EcomProductSpecification();
					specificationspokes.setProduct(product);
					StaticParamModel speciStaticParamspokes = new StaticParamModel();											
					if(item.get("field_spokes").asText() != null ) {
						speciStaticParamspokes.setId(76L);
						specificationspokes.setSpecKey(speciStaticParamspokes);
					}
					ecomProductVariantSpecRepo.save(specificationspokes);
					// *********** 58. field_spokes ends ************************** /	
					// *********** 59. field_accessories starts ************************** /
					EcomProductSpecification specificationaccessories = new EcomProductSpecification();
					specificationaccessories.setProduct(product);
					StaticParamModel speciStaticParamaccessories = new StaticParamModel();											
					if(item.get("field_accessories").asText() != null ) {
						speciStaticParamaccessories.setId(3L);
						specificationaccessories.setSpecKey(speciStaticParamaccessories);
					}
					ecomProductVariantSpecRepo.save(specificationaccessories);
					// *********** 59. field_accessories ends ************************** /
					// *********** 60. field_front_shifter starts ************************** /
					EcomProductSpecification specificationfrontshifter = new EcomProductSpecification();
					specificationfrontshifter.setProduct(product);
					StaticParamModel speciStaticParamfrontshifter = new StaticParamModel();											
					if(item.get("field_front_shifter").asText() != null ) {
						speciStaticParamfrontshifter.setId(75L);
						specificationfrontshifter.setSpecKey(speciStaticParamfrontshifter);
					}
					ecomProductVariantSpecRepo.save(specificationfrontshifter);
					// *********** 60. field_front_shifter ends ************************** /
					// *********** 61. field_rear_shifter starts ************************** /
					EcomProductSpecification specificationrearshifter = new EcomProductSpecification();
					specificationrearshifter.setProduct(product);
					StaticParamModel specStaticParamrearshifter = new StaticParamModel();									
					if(item.get("field_rear_shifter").asText() != null ) {
						specStaticParamrearshifter.setId(74L);
						specificationrearshifter.setSpecKey(specStaticParamrearshifter);
					}
					ecomProductVariantSpecRepo.save(specificationrearshifter);
					// *********** 61. field_rear_shifter ends ************************** /
					// *********** 62. field_chainwheel_and_crank starts ************************** /
					EcomProductSpecification specificationchainwheelcrank = new EcomProductSpecification();
					specificationchainwheelcrank.setProduct(product);
					StaticParamModel speciStaticParamchainwheelcrank = new StaticParamModel();						
					if(item.get("field_chainwheel_and_crank").asText() != null ) {
						speciStaticParamchainwheelcrank.setId(73L);
						specificationchainwheelcrank.setSpecKey(speciStaticParamchainwheelcrank);
					}
					 ecomProductVariantSpecRepo.save(specificationchainwheelcrank);
					// *********** 62. field_chainwheel_and_crank ends ************************** /
					// *********** 63. field_free_wheel_cassette_sprock starts ************************** /
					EcomProductSpecification specificationcassettesprock = new EcomProductSpecification();
					specificationcassettesprock.setProduct(product);
					StaticParamModel speciStaticParamcassettesprock = new StaticParamModel();											
					if(item.get("field_free_wheel_cassette_sprock").asText() != null ) {
						speciStaticParamcassettesprock.setId(72L);
						specificationcassettesprock.setSpecKey(speciStaticParamcassettesprock);
					}
					ecomProductVariantSpecRepo.save(specificationcassettesprock);
					// *********** 63. field_free_wheel_cassette_sprock ends ************************** /	
					// *********** 64. field_rear_hub starts ************************** /
					EcomProductSpecification specificationrearhub = new EcomProductSpecification();
					specificationrearhub.setProduct(product);
					StaticParamModel speciStaticParamrearhub = new StaticParamModel();											
					if(item.get("field_rear_hub").asText() != null ) {
						speciStaticParamrearhub.setId(71L);
						specificationrearhub.setSpecKey(speciStaticParamrearhub);
					}
					ecomProductVariantSpecRepo.save(specificationrearhub);
					// *********** 64. field_rear_hub ends ************************** /
					// *********** 65. field_front_hub starts ************************** /
					EcomProductSpecification specificationfronthub = new EcomProductSpecification();
					specificationfronthub.setProduct(product);
					StaticParamModel speciStaticParamfronthub = new StaticParamModel();											
					if(item.get("field_front_hub").asText() != null ) {
						speciStaticParamfronthub.setId(70L);
						specificationfronthub.setSpecKey(speciStaticParamfronthub);
					}
					ecomProductVariantSpecRepo.save(specificationfronthub);
					// *********** 65. field_front_hub ends ************************** /
			 
					// *********** 66. field_sizes starts ************************** /
					EcomProductSpecification specificationsizes = new EcomProductSpecification();
					specificationsizes.setProduct(product);
					StaticParamModel specStaticParamsizes = new StaticParamModel();									
					if(item.get("field_sizes").asText() != null ) {
						specStaticParamsizes.setId(69L);
						specificationsizes.setSpecKey(specStaticParamsizes);
					}
					ecomProductVariantSpecRepo.save(specificationsizes);
					// *********** 66. field_sizes ends ************************** /
					// *********** 67. field_handle_bar_type starts ************************** /
					EcomProductSpecification specificationhandlebartype = new EcomProductSpecification();
					specificationhandlebartype.setProduct(product);
					StaticParamModel speciStaticParamhandlebartype = new StaticParamModel();						
					if(item.get("field_handle_bar_type").asText() != null ) {
						speciStaticParamhandlebartype.setId(25L);
						specificationhandlebartype.setSpecKey(speciStaticParamhandlebartype);
					}
					 ecomProductVariantSpecRepo.save(specificationhandlebartype);
					// *********** 67. field_handle_bar_type ends ************************** /
					// *********** 68. field_head_tube starts ************************** /
					EcomProductSpecification specificationheadtube = new EcomProductSpecification();
					specificationheadtube.setProduct(product);
					StaticParamModel speciStaticParamheadtube = new StaticParamModel();											
					if(item.get("field_head_tube").asText() != null ) {
						speciStaticParamheadtube.setId(26L);
						specificationheadtube.setSpecKey(speciStaticParamheadtube);
					}
					ecomProductVariantSpecRepo.save(specificationheadtube);
					// *********** 68. field_head_tube ends ************************** /	
					// *********** 69. field_product_gear starts ************************** /
					EcomProductSpecification specificationproductgear = new EcomProductSpecification();
					specificationproductgear.setProduct(product);
					StaticParamModel speciStaticParamproductgear = new StaticParamModel();											
					if(item.get("field_product_gear").asText() != null ) {
						speciStaticParamproductgear.setId(27L);
						specificationproductgear.setSpecKey(speciStaticParamproductgear);
					}
					ecomProductVariantSpecRepo.save(specificationproductgear);
					// *********** 69. field_product_gear ends ************************** /
					// *********** 70. field_rider_s_height starts ************************** /
					EcomProductSpecification specificationridersheight = new EcomProductSpecification();
					specificationridersheight.setProduct(product);
					StaticParamModel speciStaticParamridersheight = new StaticParamModel();											
					if(item.get("field_rider_s_height").asText() != null ) {
						speciStaticParamridersheight.setId(68L);
						specificationridersheight.setSpecKey(speciStaticParamridersheight);
					}
					ecomProductVariantSpecRepo.save(specificationridersheight);
					// *********** 70. field_rider_s_height ends ************************** /
					// *********** 71. field_sub_category starts ************************** /
					EcomProductSpecification specificationsubcategory = new EcomProductSpecification();
					specificationsubcategory.setProduct(product);
					StaticParamModel speciStaticParamsubcategory = new StaticParamModel();											
					if(item.get("field_sub_category").asText() != null ) {
						speciStaticParamsubcategory.setId(45L);
						specificationsubcategory.setSpecKey(speciStaticParamsubcategory);
					}
					ecomProductVariantSpecRepo.save(specificationsubcategory);
					// *********** 71. field_sub_category ends ************************** /	 
					
		             
							 
					/* *************************** Variant Starts***********/
					item.get("field_variations_entities").fieldNames().forEachRemaining((String variant) -> {
						
						System.out.println("********variant**************"+variant);
						// variants with color Term, Size Term
						
						JsonNode variantNode = item.get("field_variations_entities").get(variant);
						 
                       
						 
						 
						System.out.println("##################variantNode####################"+variantNode);
						
						 EcomProductVariant ecomproductvariant = new EcomProductVariant();	
						 
						 ecomproductvariant.setProduct(product);
						 ecomproductvariant.setVariantCode(variantNode.get("sku").asText());
						 ecomproductvariant.setId(item.get("nid")==null ? null : item.get("nid").asLong());
						 ecomproductvariant.setPrice(variantNode.get("commerce_price").get("amount").asText());
						 ecomproductvariant.setProductUrl(variantNode.get("field_product_images_url").toPrettyString());
						 ecomproductvariant.setDiscountAmount(variantNode.get("field_is_discounted").asBoolean());
						 ecomproductvariant.setDiscountPercentage(variantNode.get("field_discount_in_percentage").asDouble());		        	 
						 ecomproductvariant.setQuantity(variantNode.get("commerce_stock").asInt());
						 ecomproductvariant.setVideoUrl(variantNode.get("field_product_video_links").toPrettyString());
                         ecomproductvariant.setImage1(item.get("field_image_url") == null ? null : item.get("field_image_url").asText());
						 ecomproductvariant.setImage2(item.get("field_image_url")==null ? null : item.get("field_image_url").asText());
						 ecomproductvariant.setImage3(item.get("field_image_url")==null ? null : item.get("field_image_url").asText());
						 ecomproductvariant.setImage4(item.get("field_image_url")==null ? null : item.get("field_image_url").asText());
						 ecomproductvariant.setImage5(item.get("field_image_url")==null ? null : item.get("field_image_url").asText());
						 ecomproductvariant.setDescription(item.get("body").get("value")==null ? null : item.get("body").get("value").asText());
						 ecomproductvariant.setVariantName(item.get("title")==null ? null : item.get("title").asText());
					        
							// **** color starts here **** //

						 if(Integer.valueOf(variantNode.get("field_color").asInt())!=null) {
							 Optional<EcomTaxonomyTerm> taxonomytermcolor = ecomTaxonomyTermRepository.findByEcomTaxonmyTid(Long.valueOf(variantNode.get("field_color").asLong()));								 
						 ecomproductvariant.setColorTermId(taxonomytermcolor.get());	
						 ecomproductvariant.setTid(taxonomytermcolor.get());
						 }
						 
						// **** color ends here **** //
						 
						 if(Integer.valueOf(variantNode.get("field_size").asInt())!=null) {
						
							// **** size starts here **** //
						 Optional<EcomTaxonomyTerm> taxonomytermsize = ecomTaxonomyTermRepository.findByEcomTaxonmyTid(Long.valueOf(variantNode.get("field_size").asLong()));							
						 ecomproductvariant.setSizeTermId(taxonomytermsize.get());
						
						 }
						 
						// **** size ends here **** //
						 
						 // ****within array -- field_road_type , field_features , field_only_feature ... starts here//
						 
						//item.get("field_road_type_entities").fieldNames().forEachRemaining((String var) -> {
						System.out.println("**********************"+ item);
						if(item.get("field_road_type") != null) {
							
							System.out.println("******field_road_type****************"+ item.get("field_road_type"));
						}
						
						if(item.get("field_age") != null) {
							System.out.println("********field_age*************"+ item.get("field_age"));
							
						}
						if(item.get("field_features") != null) {
							
							System.out.println("*********field_features*************"+ item.get("field_features"));
						}
						else
							if(item.get("field_features").equals(null)){
				            System.out.println("field_features"+null);
				        }
						if(item.get("field_only_feature") != null) {
							
							System.out.println("*********field_only_feature*************"+ item.get("field_only_feature"));
						}
						else
							if(item.get("field_only_feature").equals(null)){
				            System.out.println("field_only_feature"+null); 
				        }
						
						
//						JsonNode persona = item.get("field_road_type_entities");
//						
//						System.out.println("field_road_type_entities--****-->"+persona);

//                        item.get("field_road_type_entities").fieldNames().forEachRemaining((String personacheck) -> {
//							
//							JsonNode node= item.get("field_road_type_entities").get(personacheck);
//							
//							System.out.println("personacheck--****-->"+personacheck);
//							
//							System.out.println("node--****-->"+node);
//
//	                
//						});
//						
//						if(persona.get("field_persona") != null) {
//							System.out.println("*********field_persona*************"+ item.get("field_persona"));
//						}
						 // **** field_road_type , field_features , field_only_feature ... ends here//
						
						//item.get("field_road_type_entities").fieldNames().forEachRemaining((String persona) -> {
//						JsonNode node = item.get("field_road_type_entities");
//						
//						System.out.println("*********node*********"+node);
						
//						node.fieldNames().forEachRemaining((String personachk) -> {
//							
//							System.out.println("*********personachk*********"+personachk);
//							
//							
//						});
						

//							
//						}
						
						
							
						//	JsonNode personaNode = item.get("field_road_type_entities").get(persona);
						
							
							//System.out.println("*********personaNode*********"+personaNode);
							
							
							
							
//							
//							
//							
//						});
						
//						 ObjectMapper mapper = new ObjectMapper();
//						 mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
//						 JSONObject jsonObject = new JSONObject(response);
//						 JSONObject parse = new JSONObject(response);
//						 JsonNode node = mapper.convertValue(parse,JsonNode.class);
//						 //JsonNode personanode = item.get("field_road_type_entities").get(var);
//						// System.out.println("==>"+personanode);
//						 if(item.get("field_road_type")==null ? null : item.get("field_road_type").asText() != null ) {
//							 JSONArray jsonArrayObj = (JSONArray) parse.get("field_road_type");
//					            String[] stringlen=new String[jsonArrayObj.length()];
//					     
//					            for(int i=0;i<jsonArrayObj.length();i++){
//					                JSONObject jsonobj=(JSONObject)jsonArrayObj.get(i);
//					         
//					                System.out.println("==>"+stringlen[i]);
//					        
//					            }
//						 }
						 //field_persona*******************
//						  if(parse.get("field_road_type_entities") instanceof JSONObject){
//				            JSONObject jsonobj = (JSONObject) parse.get("tid");
//				            System.out.println("tid"+jsonobj.getString("tid"));
//				     
//				        }
//				        else 
//						 if(node.get("field_persona") instanceof JSONArray){
//				            JSONArray jsonArrayObj = (JSONArray) parse.get("field_persona");
//				            String[] stringlen=new String[jsonArrayObj.length()];
//				     
//				            for(int i=0;i<jsonArrayObj.length();i++){
//				                JSONObject jsonobj=(JSONObject)jsonArrayObj.get(i);
//				         
//				                System.out.println("==>"+stringlen[i]);
//				        
//				            }
//				        }
//				        else if (parse.get("field_persona").equals(null)){
//				            System.out.println("field_persona"+null);
//				        }
//						// });
//						
						  //field_age************************
//				        if(parse.get("field_age") instanceof JSONArray){
//				            JSONArray jsonArrayObj = (JSONArray) parse.get("field_age");
//				            String[] stringlen=new String[jsonArrayObj.length()];
//				     
//				            for(int i=0;i<jsonArrayObj.length();i++){
//				                JSONObject jsonobj=(JSONObject)jsonArrayObj.get(i);
//				         
//				                System.out.println("==>"+stringlen[i]);
//				        
//				            }
//				        }
//				            else if (parse.get("field_age").equals(null)){
//					            System.out.println("field_age"+null);
//					        }
//				       
//				        //field_only_feature****************
//				        if(parse.get("field_only_feature") instanceof JSONArray){
//				            JSONArray jsonArrayObj = (JSONArray) parse.get("field_only_feature");
//				            String[] stringlen=new String[jsonArrayObj.length()];
//				     
//				            for(int i=0;i<jsonArrayObj.length();i++){
//				                JSONObject jsonobj=(JSONObject)jsonArrayObj.get(i);
//				         
//				                System.out.println("==>"+stringlen[i]);
//				        
//				            }
//				        }
//				            else if (parse.get("field_only_feature").equals(null)){
//					            System.out.println("field_only_feature"+null);
//					        }
//				       
//				      //field_road_type****************
//				        if(parse.get("field_road_type") instanceof JSONArray){
//				            JSONArray jsonArrayObj = (JSONArray) parse.get("field_road_type");
//				            String[] stringlen=new String[jsonArrayObj.length()];
//				     
//				            for(int i=0;i<jsonArrayObj.length();i++){
//				                JSONObject jsonobj=(JSONObject)jsonArrayObj.get(i);
//				         
//				                System.out.println("==>"+stringlen[i]);
//				        
//				            }
//				        }
//				            else if (parse.get("field_road_type").equals(null)){
//					            System.out.println("field_road_type"+null);
//					        }
//				       
//				        //field_features****************
//				        if(parse.get("field_features") instanceof JSONArray){
//				            JSONArray jsonArrayObj = (JSONArray) parse.get("field_features");
//				            String[] stringlen=new String[jsonArrayObj.length()];
//				     
//				            for(int i=0;i<jsonArrayObj.length();i++){
//				                JSONObject jsonobj=(JSONObject)jsonArrayObj.get(i);
//				         
//				                System.out.println("==>"+stringlen[i]);
//				        
//				            }
//				        }
//				            else if (parse.get("field_features").equals(null)){
//					            System.out.println("field_features"+null);
//					        }
						 
						 ecomProductVariantRepository.save(ecomproductvariant);
						 
					/* *************************** Variant Ends  */
					
						 
						 // term Relation - insert on ecomm_taxonomy_relationship
//					 
//					 // ***********  1. Brand starts ******************* /
//				EcomTaxonomyRelationship  termRel = new EcomTaxonomyRelationship();
//					 //termRel.setProductVariant(ecomproductvariant);
//					 termRel.setCategory(pm);
//					 //findByEcomVariantId
//					 
//					 EcomProductVariant ecomp=new EcomProductVariant();
//					 Optional<EcomTaxonomyTerm> termRelTaxTerm = ecomTaxonomyTermRepository.findByEcomTaxonmyTid(Long.valueOf(item.get("field_brand")==null ? null : item.get("field_brand").asLong()));
//					 Optional <EcomProductVariant> productvariant = ecomProductVariantRepository.findById(Long.valueOf(item.get("field_brand")==null ? null : item.get("field_brand").asLong()));
//					 if(productvariant.isPresent())
//					 {
//						 System.out.println("productvariant=>"+productvariant);
//						 termRel.setProductVariant(productvariant.get());
//					 }
//					 
//					 termRel.setProductVariant(ecomproductvariant);
//					// termRel.()
//					 termRel.setTaxonomyTerm(termRelTaxTerm.get());
//					termRel.setTaxonomy(termRelTaxTerm.get());
//					 ecomTaxonomyRelationshipRepo.save(termRel);
//						// ***********   Brand Ends ******************* /
					//});	
					//System.out.println("*********->listOfProdcutVarient******************"+listOfProdcutVarient);
					
//					listOfProdcutVarient.get("field_road_type_entities").fieldNames().forEachRemaining((String persona) -> {
//						
//						System.out.println("*********->persona******************"+persona);
//						
//						JsonNode personaNode = item.get("field_road_type_entities").get(persona);
//						
//						System.out.println("**************personaNode------------>"+personaNode);
//						
					});
				}

			});
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return varient;
	}

	@Override
	public BaseResponseDTO getProductStockavailablityWithPinCode(String product_id, String pincode) throws Exception {

		String url = "https://www.trackandtrail.in/api/v1/product_stock_availablity/";
		String value = url.concat(product_id).concat("/").concat(pincode);

		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());

		ResponseEntity<String> response = restTemplate.exchange(value, HttpMethod.GET, request, String.class);

		JsonNode o = objectMapper.readTree(response.getBody());

		return new BaseResponseDTO(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription(),
				response.getStatusCode().value(), o);
	}

	@Override
	public List<EcomOrders> getAllordersList(String sort, String sortOrder, String limit, String offSet, String status)
			throws Exception {
		String url = "https://www.trackandtrail.in/api/v1/order?";
		String value = url.concat("sort_by").concat("=").concat(sort).concat("&").concat("sort_order").concat("=")
				.concat(sortOrder).concat("&").concat("limit").concat("=").concat(limit).concat("&").concat("offset")
				.concat("=").concat(offSet).concat("&").concat("filter[status]").concat("=").concat(status);

		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
		ResponseEntity<String> response = restTemplate.exchange(value, HttpMethod.GET, request, String.class);
		List<EcomOrders> varient = new ArrayList<EcomOrders>();

		try {

			JsonNode listofOrders = objectMapper.readTree(response.getBody());
//			 System.out.println(listOfProdcutVarient.toPrettyString());

			listofOrders.fieldNames().forEachRemaining((String fieldName) -> {
				System.out.println(fieldName + "rajapandi---->");

				if (!listofOrders.isEmpty()) {
					JsonNode item = listofOrders.get(fieldName);

					System.out.println(item.toPrettyString());

					System.out.println("----------------------------------**************************************");

					item.fieldNames().forEachRemaining((String variant) -> {

						JsonNode o = item.get("commerce_customer_shipping_entities").get(variant);

						EcomOrders order = new EcomOrders();

						order.setId(item.get("uid") == null ? null : item.get("uid").asLong());
						order.setOrderNo(item.get("order_number") == null ? null : item.get("order_number").asText());
						order.setPaymentMethod(item.get("data").get("payment_method") == null ? null
								: item.get("data").get("payment_method").asText());
						order.setTotalAmount(item.get("commerce_order_total").get("amount") == null ? null
								: item.get("commerce_order_total").get("amount").asDouble());
						order.setPaymentStatus(item.get("status") == null ? null : item.get("status").asText());
						order.setPaymentTransactionId(item.get("data").get("payment_redirect_key") == null ? null
								: item.get("data").get("payment_redirect_key").asText());

						String shipping_id = item.get("commerce_customer_shipping").asText();

						if (shipping_id != null) {
							order.setFirstName(item.get("commerce_customer_shipping_entities").get(shipping_id)
									.get("commerce_customer_address").get("first_name").asText());
							order.setLastName(item.get("commerce_customer_shipping_entities").get(shipping_id)
									.get("commerce_customer_address").get("last_name").asText());
							order.setPinCode(item.get("commerce_customer_shipping_entities").get(shipping_id)
									.get("commerce_customer_address").get("postal_code").asText());
							order.setMobile(item.get("commerce_customer_shipping_entities").get(shipping_id)
									.get("commerce_customer_address").get("mobile_number").asText());
							order.setDoorNo(item.get("commerce_customer_shipping_entities").get(shipping_id)
									.get("commerce_customer_address").get("thoroughfare").asText());
							order.setCity(item.get("commerce_customer_shipping_entities").get(shipping_id)
									.get("commerce_customer_address").get("locality").asText());
							order.setState(item.get("commerce_customer_shipping_entities").get(shipping_id)
									.get("commerce_customer_address").get("administrative_area").asText());
							order.setOrderStatus(item.get("commerce_customer_shipping_entities").get(shipping_id)
									.get("status").asInt());
							order.setAddressType(item.get("commerce_customer_shipping_entities").get(shipping_id)
									.get("type").asText());
							order.setStreet(item.get("commerce_customer_shipping_entities").get(shipping_id)
									.get("commerce_customer_address").get("premise").asText());

						}

//			String billing_id = item.get("commerce_customer_billing").asText();
//			if (billing_id != null) {
//				order.setAddressType(item.get("commerce_customer_billing_entities").get(billing_id).get("type").asText());
//			}
//		
//		
						varient.add(order);

					});

				}
			});
			ecomOrdersRepository.saveAll(varient);

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return varient;
	}
}
