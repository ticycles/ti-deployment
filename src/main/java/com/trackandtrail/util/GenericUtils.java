package com.trackandtrail.util;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationResponseDTO;

public interface GenericUtils {

	static <X> BaseResponseDTO wrapOrNotFound(Optional<X> value) {
		if (value.isPresent())
			return new BaseResponseDTO(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription(),
					RequestStatusUtil.SUCCESS_RESPONSE.getErrorCode(), value.get());
		else
			return new BaseResponseDTO(RequestStatusUtil.ID_NOT_FOUND.getErrorDescription(),
					RequestStatusUtil.ID_NOT_FOUND.getErrorCode());
	}

	static <X> BaseResponseDTO wrapOrEmptyList(List<X> value) {
		if (!value.isEmpty())
			return new BaseResponseDTO(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription(),
					RequestStatusUtil.SUCCESS_RESPONSE.getErrorCode(), value);
		else
			return new BaseResponseDTO(RequestStatusUtil.EMPTY_DATA.getErrorDescription(),
					RequestStatusUtil.EMPTY_DATA.getErrorCode());
	}

	static <X> BaseResponseDTO wrapOrEmptyPagination(Page<X> value) {
		if (!value.isEmpty()) {
			PaginationResponseDTO response = new PaginationResponseDTO();
			response.setContent(value.getContent());
			response.setTotalElements(value.getTotalElements());
			response.setTotalPages(value.getTotalPages());
			response.setCurrentPageSize(value.getSize());
			response.setCurrentPageNo(value.getNumber());

			return new BaseResponseDTO(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription(),
					RequestStatusUtil.SUCCESS_RESPONSE.getErrorCode(), response);
		} else
			return new BaseResponseDTO(RequestStatusUtil.EMPTY_DATA.getErrorDescription(),
					RequestStatusUtil.EMPTY_DATA.getErrorCode());
	}

}
