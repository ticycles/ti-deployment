package com.trackandtrail.common.dto;

import java.io.Serializable;


import lombok.Data;




@Data
public class PaginationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5582738506194016801L;

	private int pageNo;

	private int pageSize;

	private String sortOrder;

	private String sortBy;

	private String searchData;

	

	
	
	

}
