package com.trackandtrail.common;

import java.io.Serializable;

import lombok.Data;

@Data
public class DocumentManagementDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String serviceName;

	private String docSavedPath;
	
	private String docRefCode;

	private String documentString;

	private String extensionType;

	public DocumentManagementDTO() {
	}

	public DocumentManagementDTO(Long id) {
		super();
		this.id = id;
	}

	public DocumentManagementDTO(String serviceName, String docRefCode, String documentString, String extensionType) {
		super();
		this.serviceName = serviceName;
		this.docRefCode = docRefCode;
		this.documentString = documentString;
		this.extensionType = extensionType;
	}
	
	

}
