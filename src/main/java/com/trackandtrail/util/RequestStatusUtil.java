package com.trackandtrail.util;

public interface RequestStatusUtil {

	public static class Error {
		private Integer errorCode;
		private String errorDescription;

		public Integer getErrorCode() {
			return errorCode;
		}

		public Integer getCode() {
			return errorCode;
		}

		public String getErrorDescription() {
			return errorDescription;
		}

		public Error(Integer errorCode, String errorDescription) {
			this.errorCode = errorCode;
			this.errorDescription = errorDescription;
		}
	}

	public static final Error SUCCESS_RESPONSE = new Error(200, "Success");

	public static final Error CREATED_RESPONSE = new Error(200, "Created Successfully");
	
	public static final Error UPDATED_RESPONSE = new Error(200, "Updated Successfully");
	
	public static final Error DELETED_RESPONSE = new Error(200, "Deleted Successfully");
	
	public static final Error ID_NOT_FOUND = new Error(404, "ID not found");
	
	public static final Error EMPTY_DATA = new Error(204, "Empty Data");
	
	public static final Error FAILURE_RESPONSE = new Error(500, "Failed");
	
	public static final Error BAD_REQUEST = new Error(400, "Bad Request");
	
	public static final Error FOREIGN_KEY_CONSTRAINT_ISSUE = new Error(409, "Referred Entity cannot be deleted");
	
	public static final Error DUPLICATE_ENTRY = new Error(409, "Duplicate Entry");
	
	public static final Error CONFLICT = new Error(409, "Conflict");
	
	public static final Error UNAUTHORIZED = new Error(401, "Invalid Credentials");
	
	public static final Error INACTIVE = new Error(200, "Inactivated Successfully"); 
	
	public static final Error ACTIVE = new Error(200, "Activated Successfully"); 
}
