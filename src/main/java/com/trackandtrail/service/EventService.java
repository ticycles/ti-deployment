package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.EventDto;

public interface EventService {

	public BaseResponseDTO save(EventDto EventDto) throws Exception;

	public BaseResponseDTO update(EventDto EventDto) throws Exception;

	public BaseResponseDTO getAll() throws Exception;

	public BaseResponseDTO getById(Long id) throws Exception;

	public BaseResponseDTO getByUserId(Long id) throws Exception;

	public BaseResponseDTO deleteById(Long id, boolean flag) throws Exception;

	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception;

	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception;
	
	public BaseResponseDTO changeEventStatusById(EventDto eventDto) throws Exception;


	public BaseResponseDTO getEventMemberCount() throws Exception;

	public BaseResponseDTO getAllEventType() throws Exception;

	public BaseResponseDTO doFilterAndPaginate(Long userid, String gender, String age, String plans, String title,
			String eventType, Integer pageNo, Integer pageSize) throws Exception;
	

}
