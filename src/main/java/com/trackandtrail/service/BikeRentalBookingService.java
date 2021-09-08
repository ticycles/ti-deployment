package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.BikeRentalBookingDto;
import com.trackandtrail.dto.ManageDealerDto;
import com.trackandtrail.dto.RejectReasonDto;
public interface BikeRentalBookingService {
	
	public BaseResponseDTO save(BikeRentalBookingDto bikeRentalDetailDto) throws Exception;
	
	public BaseResponseDTO getByUserId(Long id) throws Exception;

	public BaseResponseDTO getById(Long id) throws Exception;

	public BaseResponseDTO searchAndPaginateBooking(PaginationDTO paginationDTO) throws Exception;
	
	public BaseResponseDTO getAll() throws Exception;
	
	public BaseResponseDTO getAllBikeFilter() throws Exception;
	
	public BaseResponseDTO changeStatusById(RejectReasonDto rejectReasonDto) throws Exception;

	public BaseResponseDTO doFilterAndPaginate(Long userid, String search, Integer pageNo, Integer pageSize) throws Exception;





}
