package com.trackandtrail.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.ContentDto;
import com.trackandtrail.dto.ContentRatingDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.service.ContentService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/content")
public class ContentController {

	private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

	@Autowired
	public ContentService contentService;

//		@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createContent", response = BaseResponseDTO.class)
	@PostMapping(value = "/save-content", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> createBlog(@RequestBody ContentDto contentDto) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			logger.info("BlogController:: createBlog :: contentModel :: ", contentDto.toString());
			if (contentDto.getId() != null)
				throw new BadRequestException("Id must be null");
			responseModel = contentService.createContent(contentDto);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {

			logger.error("BlogController:: createBlog :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while create the blog");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("BlogController:: createGroup :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateContent", response = BaseResponseDTO.class)
	@PutMapping(value = "/updateContent", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> updateBlog(@RequestBody ContentDto contentDto) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {

			logger.info("BlogController:: updateBlog :: contentModel :: ", contentDto.toString());
			if (contentDto.getId() == null)
				throw new BadRequestException("blogId cannot be null");
			responseModel = contentService.updateContent(contentDto);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {

			logger.error("BlogController:: updateBlog :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while create the blog");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("BlogController:: updateBlog :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getallContent", response = BaseResponseDTO.class)
	@GetMapping(value = "/getallContent", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getAll() {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = contentService.getAll();
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("BlogController:: updateBlog :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while getAll the blog");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("BlogController:: updateBlog :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getContentById", response = BaseResponseDTO.class)
	@GetMapping(value = "/getContentById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getById(@RequestParam Long id) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = contentService.getByContentId(id);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("BlogController:: updateBlog :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while getId the Blog");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("BlogController:: updateBlog :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "deletebyid", response = BaseResponseDTO.class)
	@DeleteMapping(value = "/deletebyid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> deletebyid(@RequestParam Long id, @RequestParam Boolean flag) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = contentService.deleteById(id, flag);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("BlogController:: deleteBlog :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while delete the Blog");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("BlogController:: deleteBlog :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@ApiOperation(value = "changeStatusById", response = BaseResponseDTO.class)
	@GetMapping(value = "/changeStatusById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> changeStatusById(@RequestParam Long id, @RequestParam Integer statusId) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = contentService.changeStatusById(id, statusId);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			logger.error("BlogController:: changeStatusById :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while changeStatusById the blog");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("BlogController:: changeStatusById :: responseEntity :: ", responseModel.toString());
		return responseEntity;
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "searchAndPaginate", response = BaseResponseDTO.class)
	@PostMapping(value = "/searchAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> searchAndPaginate(@RequestBody PaginationDTO paginationDTO) {
		ResponseEntity<BaseResponseDTO> responseEntity = null;
		BaseResponseDTO responseModel = new BaseResponseDTO();
		HttpStatus httpStatus;
		try {
			responseModel = contentService.searchAndPaginate(paginationDTO);
			httpStatus = HttpStatus.OK;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("BlogController:: searchAndPaginate :: Exception :: " + ex);
			responseModel.setMsg("Exception occurred while searchAndPaginate the blog");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		responseEntity = new ResponseEntity<>(responseModel, httpStatus);
		logger.info("BlogController:: searchAndPaginate :: responseEntity :: ", responseModel.toString());
		return responseEntity;

	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getRatingAndComment", response = BaseResponseDTO.class)
	@GetMapping(value = "/getRatingAndComment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getRatingAndComment(@RequestParam Long userId, @RequestParam Long contentId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(contentService.getRatingAndComment(userId, contentId),
				HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getRatingAndCommentByContentId", response = BaseResponseDTO.class)
	@GetMapping(value = "/getRatingAndCommentByContentId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getRatingAndCommentByContentId(@RequestParam Long contentId)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(contentService.getRatingAndCommentByContentId(contentId),
				HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "updateViewsCount", response = BaseResponseDTO.class)
	@GetMapping(value = "/updateViewsCount", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> updateViewsCount(@RequestParam Long contentId) throws Exception {
		contentService.updateViewsCount(contentId);
		BaseResponseDTO resp = new BaseResponseDTO();
		resp.setMsg("updated successfully");
		return new ResponseEntity<BaseResponseDTO>(resp, HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "addRatingAndComment", response = BaseResponseDTO.class)
	@PostMapping(value = "/addRatingAndComment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody ContentRatingDto contentRatingDto)
			throws CusDataIntegrityViolationException, Exception {
		if (contentRatingDto.getId() != null)
			throw new BadRequestException("Id must be null");
		
	
		return new ResponseEntity<BaseResponseDTO>(contentService.update(contentRatingDto), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "getContentByUserId", response = BaseResponseDTO.class)
	@GetMapping("/getContentByUserId")
	public ResponseEntity<BaseResponseDTO> getContentByUserId(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(contentService.getContentByUserId(userId), HttpStatus.OK);
	}

	@ApiOperation(value = "doFilerAndPaginate", response = BaseResponseDTO.class)
	@GetMapping(value = "/doFilerAndPaginate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> doFilerAndPaginate(@RequestParam Long userid, @RequestParam String month,
			@RequestParam String title, @RequestParam Integer pageNo, @RequestParam Integer pageSize)
			throws Exception {
		return new ResponseEntity<BaseResponseDTO>(
				contentService.doFilterAndPaginate(userid, month, title, pageNo, pageSize), HttpStatus.OK);
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAllByIsLikeByContentId", response = BaseResponseDTO.class)
	@GetMapping("/getAllByIsLikeByContentId")
	public ResponseEntity<BaseResponseDTO> getAllByIsLikeByContentId( @RequestParam Long contentId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(contentService.getAllByIsLike(contentId) ,HttpStatus.OK);
		
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAllByRatingStarByContentId", response = BaseResponseDTO.class)
	@GetMapping("/getAllByRatingStarByContentId")
	public ResponseEntity<BaseResponseDTO> getAllByRatingStarByContentId(@RequestParam Long contentId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(contentService.getAllByRatingStar(contentId),HttpStatus.OK);
		
	}
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAllByCommentByContentId", response = BaseResponseDTO.class)
	@GetMapping("/getAllByCommentByContentId")
	public ResponseEntity<BaseResponseDTO> getAllByCommentByContentId(@RequestParam Long contentId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(contentService.getAllByComment(contentId),HttpStatus.OK);
		
	}
	
	
}
