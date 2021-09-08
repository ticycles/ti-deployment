package com.trackandtrail.common.dto;

public class PaginationResponseDTO {
	
	private Object content;
	
	private Integer totalPages;
	
	private Long totalElements;
	
	private Integer currentPageNo;
	
	private Integer currentPageSize;

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public Integer getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(Integer currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public Integer getCurrentPageSize() {
		return currentPageSize;
	}

	public void setCurrentPageSize(Integer currentPageSize) {
		this.currentPageSize = currentPageSize;
	}

	@Override
	public String toString() {
		return "PaginationResponseDTO [content=" + content + ", totalPages=" + totalPages + ", totalElements="
				+ totalElements + ", currentPageNo=" + currentPageNo + ", currentPageSize=" + currentPageSize + "]";
	}
	
	public PaginationResponseDTO() {}
	
	public PaginationResponseDTO(Object content, int totalPages, Long totalElements, int currentPageNo, int currentPageSize) {
		this.content = content;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
		this.currentPageNo = currentPageNo;
		this.currentPageSize = currentPageSize;
	}

}
