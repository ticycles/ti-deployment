package com.trackandtrail.service.impl.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import com.google.firestore.admin.v1.Index.IndexField.Order;
import com.google.gson.JsonObject;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.model.ecommercev2.EcomOrders;
import com.trackandtrail.model.ecommercev2.EcomProduct;
import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.model.ecommercev2.EcomTaxonomy;
import com.trackandtrail.model.ecommercev2.EcomTaxonomyTerm;
import com.trackandtrail.model.stock.Stock;
import com.trackandtrail.repository.ecommercev2.EcomProductRepository;
import com.trackandtrail.repository.ecommercev2.EcomTaxonomyRepository;
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

//	@Override
//	public List<EcomTaxonomy> getTaxonomy() {
//
//		String url = "https://www.trackandtrail.in/api/v1/taxonomy_vocabulary?page=0";
//
//		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
//		ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
//
//		List<EcomTaxonomy> ts = new ArrayList<EcomTaxonomy>();
//
//		try {
//			JsonNode listofTaxon = objectMapper.readTree(resp.getBody());
//
//			System.out.println(listofTaxon.toPrettyString());
//
//			listofTaxon.forEach(o -> {
//
//				EcomTaxonomy tx = new EcomTaxonomy();
//
//				tx.setId(o.get("vid").asLong());
//				tx.setTaxonomyName(o.get("name").textValue());
//
//				ts.add(tx);
//
//				taxRepo.saveAll(ts);
//
//			});
//
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ts;
//
//	}
//
//	@Override
//	public List<EcomTaxonomyTerm> getTaxonomyTerm() {
//
//		String url = "https://www.trackandtrail.in/api/v1/taxonomy_term?name=accessories_brands";
//
//		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
//		ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
//
//		List<EcomTaxonomyTerm> ts = new ArrayList<EcomTaxonomyTerm>();
//
//		try {
//			JsonNode listofTaxon = objectMapper.readTree(resp.getBody());
//
//			System.out.println(listofTaxon.toPrettyString());
//
//			listofTaxon.forEach(o -> {
//
//				EcomTaxonomyTerm tx = new EcomTaxonomyTerm();
//
//				tx.setId(o.get("tid").asLong());
//				tx.setTermName(o.get("name").textValue());
////				tx.setTaxonomy(o.get("vid"));
//
//				ts.add(tx);
//
//			});
//
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return ts;
//
//	}
//
//	@Override
//	public List<EcomProduct> getProduct() {
//		String url = "https://www.trackandtrail.in/api/v1/product-display/142415";
//
//		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
//		ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
//
//		List<EcomProduct> ecom = new ArrayList<EcomProduct>();
//
//		try {
//			JsonNode o = objectMapper.readTree(resp.getBody());
//
//			System.out.println(o.toPrettyString());
//
////			product.forEach(o -> {
//
//			EcomProduct ecomProduct = new EcomProduct();
//
//			ecomProduct.setId(o.get("uid").asLong());
//			ecomProduct.setProductName(o.get("title").textValue());
//
//			ecom.add(ecomProduct);
////				ecomProductRepository.saveAll(ecom);
//
////			});
//
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ecom;
//
//	}
//
//	@Override
//	public List<EcomProductVariant> getProductVariant() {
//		String url="https://www.trackandtrail.in/api/v1/product-display?sort_by=created&filter[type]=bikes&sort_order=ASC&limit=10&offset=0";
//		
//		HttpEntity<String> request=new HttpEntity<String>(getHeadersWithClientCredentials());
//		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
//		List<EcomProductVariant> varient = new ArrayList<EcomProductVariant>();
//		
//		try {
//			JsonNode listOfProdcutVarient = objectMapper.readTree(response.getBody());
////			 System.out.println(listOfProdcutVarient.toPrettyString());
//				
//					listOfProdcutVarient.fieldNames().forEachRemaining((String fieldName) -> {
//						System.out.println(fieldName + "rajapandi---->"    );
//						
//						if(!listOfProdcutVarient.isEmpty()) {
//				            JsonNode item = listOfProdcutVarient.get(fieldName);	
//				           
//				            System.out.println(item.toPrettyString());
//				           
//				            System.out.println("----------------------------------**************************************");
//				            
//				            
//				           
//				            item.get("field_variations_entities").fieldNames().forEachRemaining((String variant) -> {
//				            	
//				            	JsonNode o = listOfProdcutVarient.get(variant);
//				            
//				            
//
//				          
//					            
//				           
//			EcomProductVariant pro = new EcomProductVariant();	
//			
//			EcomProduct ecom=new EcomProduct();
//			
//			
////				pro.setId(o.get("vid").asLong());
////				pro.setImage1(o.get("field_image_url") == null ? null : o.get("field_image_url").asText());
////				pro.setImage2(o.get("field_logo_url")==null ? null : o.get("field_logo_url").asText());
////				pro.setImage3(o.get("field_tab_listing_image_url")==null ? null : o.get("field_tab_listing_image_url").asText());
////				pro.setImage4(o.get("field_mobile_listing_image_url")==null ? null : o.get("field_mobile_listing_image_url").asText());
////				pro.setImage5(o.get("field_homepage_mobile_listing_im_url")==null ? null : o.get("field_homepage_mobile_listing_im_url").asText());
////				pro.setPrice(o.get("commerce_price")==null ? null : o.get("commerce_price").asText());
////				pro.setDiscountAmount(o.get("field_is_discounted")==null ? null : o.get("field_is_discounted").asDouble());
////				pro.setDiscountPercentage(o.get("field_discount_in_percentage")==null ? null : o.get("field_discount_in_percentage").asDouble());
////				pro.setDescription(o.get("description")==null ? null : o.get("description").asText());
////				pro.setVariantCode(o.get("sku")==null ? null : o.get("sku").asText());
////				pro.setQuantity(o.get("commerce_stock")==null ? null : o.get("commerce_stock").asInt());			
////				
////				varient.add(pro);
//			
//			pro.setId(item.get("vid").asLong());
//			pro.setImage1(item.get("field_image_url") == null ? null : item.get("field_image_url").asText());
//			pro.setImage2(item.get("field_image_url")==null ? null : item.get("field_image_url").asText());
//			pro.setImage3(item.get("field_image_url")==null ? null : item.get("field_image_url").asText());
//			pro.setImage4(item.get("field_image_url")==null ? null : item.get("field_image_url").asText());
//			pro.setImage5(item.get("field_image_url")==null ? null : item.get("field_image_url").asText());
//			pro.setPrice(item.get("field_variations_entities").get("ammount")==null ? null : item.get("field_variations_entities").get("amount").asText());
//			pro.setDiscountAmount(item.get("field_is_discounted")==null ? null : item.get("field_is_discounted").asDouble());
//			pro.setDiscountPercentage(item.get("field_discount_in_percentage")==null ? null : item.get("field_discount_in_percentage").asDouble());
//			pro.setDescription(item.get("body").get("value")==null ? null : item.get("body").get("value").asText());
//			pro.setVariantCode(item.get("sku")==null ? null : item.get("sku").asText());
//			pro.setQuantity(item.get("commerce_stock")==null ? null : item.get("commerce_stock").asInt());	
//			pro.setVariantName(item.get("title")==null ? null : item.get("title").asText());
//
//			
//			
//
////			pro.setProductType(1);
//
//			
//
//			
//			varient.add(pro);
//				
//				
//				            });
//				            
//					}
//			});
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return varient;
//	}

	@Override
	public BaseResponseDTO getProductStockavailablityWithPinCode(String product_id,String pincode) throws Exception {
		
		String url="https://www.trackandtrail.in/api/v1/product_stock_availablity/";
		String value=url.concat(product_id).concat("/").concat(pincode);
			
		HttpEntity<String> request=new HttpEntity<String>(getHeadersWithClientCredentials());
		
		ResponseEntity<String> response = restTemplate.exchange(value, HttpMethod.GET, request, String.class);

		JsonNode o = objectMapper.readTree(response.getBody());		
		

		return new BaseResponseDTO(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription(),
				response.getStatusCode().value(), o);
	}

		

//	@Override
//	public List<EcomOrders> getAllordersList() throws Exception {
//        String url="https://www.trackandtrail.in/api/v1/order?sort_by=created&sort_order=DESC&limit=10&offset=0&filter[status]=completed";
//		
//		HttpEntity<String> request=new HttpEntity<String>(getHeadersWithClientCredentials());
//		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
//		List<EcomOrders> varient = new ArrayList<EcomOrders>();
//		
//
//		
//		try {
//			
//			   
//			JsonNode listofOrders = objectMapper.readTree(response.getBody());
////			 System.out.println(listOfProdcutVarient.toPrettyString());
//				
//			listofOrders.fieldNames().forEachRemaining((String fieldName) -> {
//						System.out.println(fieldName + "rajapandi---->"    );
//						
//						if(!listofOrders.isEmpty()) {
//				            JsonNode item = listofOrders.get(fieldName);	
//				           
//				            System.out.println(item.toPrettyString());
//				           
//				            System.out.println("----------------------------------**************************************");
//				            
//				            
//				           
//				            item.fieldNames().forEachRemaining((String variant) -> {
//				            	
//				     
//				            	JsonNode o = item.get("commerce_customer_shipping_entities").get(variant);
//				            	
//				            	
//			            
//				        
//					            
//					           
//					           
//			EcomOrders order = new EcomOrders();	
//			
//
//			
//			order.setId(item.get("uid")==null? null : item.get("uid").asLong());
//			order.setOrderNo(item.get("order_number")==null ? null : item.get("order_number").asText());
//			order.setPaymentMethod(item.get("data").get("payment_method")==null ? null : item.get("data").get("payment_method").asText());
//			order.setTotalAmount(item.get("commerce_order_total").get("amount")==null ? null : item.get("commerce_order_total").get("amount").asDouble());
//			order.setFirstName(item.get("commerce_customer_shipping_entities").get("first_name")==null ? null : item.get("commerce_customer_shipping_entities").get("first_name").asText());
//			order.setLastName(item.get("commerce_customer_shipping_entities").get("last_name")==null ? null : item.get("commerce_customer_shipping_entities").get("last_name").asText());
//			order.setStreet(item.get("thoroughfare")==null ? null : item.get("thoroughfare").asText());
//			order.setMobile(item.get("mobile_number")==null ? null : item.get("mobile_number").asText());
//			order.setPinCode(item.get("postal_code")==null ? null : item.get("postal_code").asText());
//			order.setCity(item.get("commerce_customer_billing_entities").get("locality")==null? null: item.get("commerce_customer_billing_entities").get("locality").asText());
//			order.setPaymentStatus(item.get("status")==null? null :item.get("status").asText());
//			
//			
//
//			
//			
//			
//
//
//			
//			varient.add(order);
//				
//			            });
//				            
//							}
//					});
//			  
//			
//						}
//		
//						catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return varient;
//	}
}


	
	
	
	

