package com.trackandtrail.service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.ContentDto;
import com.trackandtrail.dto.ContentRatingDto;

public interface ContentService {

	public BaseResponseDTO createContent(ContentDto contentDto) throws Exception;

	public BaseResponseDTO updateContent(ContentDto contentDto) throws Exception;

	public BaseResponseDTO getAll() throws Exception;

	public BaseResponseDTO getByContentId(Long id) throws Exception;

	public BaseResponseDTO deleteById(Long id, boolean flag) throws Exception;

	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception;

	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception;

	public BaseResponseDTO getRatingAndComment(Long id, Long contentId) throws Exception;

	public BaseResponseDTO getRatingAndCommentByContentId(Long contentId) throws Exception;

	public int updateViewsCount(Long contentId) throws Exception;

//	public BaseResponseDTO addRatingAndComment(ContentRatingDto contentRatingDto) throws Exception;
	
	public BaseResponseDTO getContentByUserId(Long userId) throws Exception;

	public BaseResponseDTO doFilterAndPaginate(Long userid, String month, String title, Integer pageNo, Integer pageSize)
			throws Exception;
	
	public BaseResponseDTO getAllByIsLike(Long contentId) throws Exception;

	public BaseResponseDTO getAllByRatingStar(Long contentId) throws Exception;
	
	public BaseResponseDTO getAllByComment(Long contentId) throws Exception;
	
	public BaseResponseDTO update(ContentRatingDto contentRatingDto) throws Exception;

}