package com.trackandtrail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.util.RequestStatusUtil;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException re, WebRequest request) {
		BaseResponseDTO errorDetails = new BaseResponseDTO(re.getMessage(),
				RequestStatusUtil.ID_NOT_FOUND.getErrorCode(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badReqestException(BadRequestException be, WebRequest request) {
		BaseResponseDTO errorDetails = new BaseResponseDTO(be.getMessage(),
				RequestStatusUtil.BAD_REQUEST.getErrorCode(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CusDataIntegrityViolationException.class)
	public ResponseEntity<?> dataIntegrityViolationException(CusDataIntegrityViolationException de, WebRequest request) {
		BaseResponseDTO errorDetails = new BaseResponseDTO(de.getMostSpecificCause().toString()
				.replace("java.sql.SQLIntegrityConstraintViolationException: ", ""),
				RequestStatusUtil.BAD_REQUEST.getErrorCode(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<?> unauthorizedException(UnauthorizedException re, WebRequest request) {
		BaseResponseDTO errorDetails = new BaseResponseDTO(re.getMessage(),
				RequestStatusUtil.UNAUTHORIZED.getErrorCode(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
		BaseResponseDTO errorDetails = new BaseResponseDTO(ex.getMessage(),
				RequestStatusUtil.FAILURE_RESPONSE.getErrorCode(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
