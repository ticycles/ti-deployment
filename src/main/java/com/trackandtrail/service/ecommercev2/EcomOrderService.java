package com.trackandtrail.service.ecommercev2;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ecommercev2.EcomOrderDto;
import com.trackandtrail.dto.ecommercev2.OrderRequestDto;

public interface EcomOrderService {
	


	public BaseResponseDTO getAll() throws Exception;

	public BaseResponseDTO getById(Long userId) throws Exception;
	
	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception;

	public BaseResponseDTO save(OrderRequestDto orderRequestDto) throws Exception;

	public BaseResponseDTO getByUserId(Long userId) throws Exception;

	public BaseResponseDTO getByOrderItemId(Long orderItemId) throws Exception;

	public BaseResponseDTO getAllOrderItems() throws Exception;
	
	public BaseResponseDTO update(EcomOrderDto ecomOrderDto) throws Exception;

	
	public BaseResponseDTO changeStatusByOrderId(Long id, Integer statusId) throws Exception;


}
