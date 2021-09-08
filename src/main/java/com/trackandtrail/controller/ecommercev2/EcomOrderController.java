package com.trackandtrail.controller.ecommercev2;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomOrderDto;
import com.trackandtrail.dto.ecommercev2.OrderRequestDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.model.ecommercev2.EcomOrderItems;
import com.trackandtrail.model.ecommercev2.EcomOrders;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.repository.ecommercev2.EcomOrdersItemsRepository;
import com.trackandtrail.repository.ecommercev2.EcomOrdersRepository;
import com.trackandtrail.repository.ecommercev2.EcomProductVariantRepository;
import com.trackandtrail.service.ecommercev2.EcomOrderService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/Ecom/orders")
public class EcomOrderController {

	@Autowired
	private EcomOrderService ecomOrderService;

	@Autowired
	private EcomOrdersRepository ecomOrdersRepository;

	@Autowired
	private EcomOrdersItemsRepository eoiRepo;

	@Autowired
	private EcomProductVariantRepository pvRepo;

	@Autowired
	private UserRepository userRepo;

//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "createOrder", response = BaseResponseDTO.class)
	@PostMapping("/createOrder")
	public ResponseEntity<BaseResponseDTO> create(@RequestBody OrderRequestDto orderRequestDto)
			throws CusDataIntegrityViolationException, Exception {

		return new ResponseEntity<BaseResponseDTO>(ecomOrderService.save(orderRequestDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping(value = "/getByUserid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getByUserId(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomOrderService.getById(userId), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "changeStatusById", response = BaseResponseDTO.class)
	@GetMapping(value = "/changeStatusById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> changeStatusById(@RequestParam Long id, @RequestParam Integer statusId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomOrderService.changeStatusById(id, statusId), HttpStatus.OK);
	}
	
	

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
		@ApiOperation(value = "getAllOrders", response = BaseResponseDTO.class)
		@GetMapping(value = "/getAllOrders", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<BaseResponseDTO> getAllOrders() throws Exception {
			return new ResponseEntity<BaseResponseDTO>(ecomOrderService.getAll(), HttpStatus.OK);
		}


	@ApiOperation(value = "OrderDetails", response = BaseResponseDTO.class)
	@GetMapping(value = "/{OrderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> changeStatusById(@PathVariable Long OrderId) throws Exception {

		Optional<EcomOrders> orderOpt = ecomOrdersRepository.findById(OrderId);

		EcomOrders orders = new EcomOrders();
		if(orderOpt.isPresent()) {
			orders =  orderOpt.get() ;	//orders.getItems();
		}

	List<EcomOrderItems> orderItemsOpt =  eoiRepo.findByOrdersId(OrderId);

		if (!orderItemsOpt.isEmpty()) {
			orders.setItems(orderItemsOpt);
		}

		BaseResponseDTO resp = new BaseResponseDTO();
		resp.setMsg("Success");
		resp.setResponseContent(orders);

		return new ResponseEntity<BaseResponseDTO>(resp, HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping(value = "/getOrderItemsByUserid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getById(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomOrderService.getByUserId(userId), HttpStatus.OK);
	}

	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping(value = "/getByOrderItemId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getByOrderItemId(@RequestParam Long orderItemId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(ecomOrderService.getByOrderItemId(orderItemId), HttpStatus.OK);
	}
	
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
			@ApiOperation(value = "getAllOrderItems", response = BaseResponseDTO.class)
			@GetMapping(value = "/getAllOrderItems", produces = MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<BaseResponseDTO> getAllOrderItems() throws Exception {
				return new ResponseEntity<BaseResponseDTO>(ecomOrderService.getAllOrderItems(), HttpStatus.OK);
			}
			
//			@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
			@ApiOperation(value = "updateEcomOrder", response = BaseResponseDTO.class)
			@PutMapping(value = "updateEcomOrder", produces = MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<BaseResponseDTO> update(@RequestBody EcomOrderDto ecomOrderDto)
					throws CusDataIntegrityViolationException, Exception {
				if (ecomOrderDto.getId() == null)
					throw new BadRequestException("Id cannot be null");
				return new ResponseEntity<BaseResponseDTO>(ecomOrderService.update(ecomOrderDto), HttpStatus.OK);
			}
			
//			@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
			@ApiOperation(value = "changeStatusByOrderId", response = BaseResponseDTO.class)
			@GetMapping(value = "/changeStatusByOrderId", produces = MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<BaseResponseDTO> changeStatusByOrderId(@RequestParam Long id, @RequestParam Integer statusId)
					throws Exception {
				return new ResponseEntity<BaseResponseDTO>(ecomOrderService.changeStatusByOrderId(id, statusId), HttpStatus.OK);
			}
}
