package com.trackandtrail.service;

import java.util.List;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.BikeRentalProductDto;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;

public interface BikeRentalService {

	public BaseResponseDTO save(BikeRentalProductDto bikeRentalManagementDTO) throws Exception;

	public BaseResponseDTO update(BikeRentalProductDto bikeRentalManagementDTO) throws Exception;

	public BaseResponseDTO getAll() throws Exception;


	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception;

	public BaseResponseDTO getByBikeRentalId(Long bikeRentalId) throws Exception;

	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception;

	public List<BikeRentalProduct> findByProductSku(String productSku, Long id) throws Exception;

	public BaseResponseDTO doFilterAndPaginate(String color, String size, String type, String priceRange, Integer pageNo,
			Integer pageSize, String sortBy) throws Exception;

}
