package com.trackandtrail.common.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponseDTO {
	
	private String msg;
	private Integer errorCode;
	private Object responseContent;
	private Object extras;
	
	public BaseResponseDTO() {
	}

	
	public BaseResponseDTO(String msg, Integer errorCode, Object responseContent) {
		super();
		this.msg = msg;
		this.errorCode = errorCode;
		this.responseContent = responseContent;
		this.extras=extras;
	}

	public BaseResponseDTO(String msg, Integer errorCode) {
		super();
		this.msg = msg;
		this.errorCode = errorCode;
	}
	
	

}
