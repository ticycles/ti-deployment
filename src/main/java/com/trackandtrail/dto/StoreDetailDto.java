package com.trackandtrail.dto;
import java.io.Serializable;
import lombok.Data;

@Data
public class StoreDetailDto implements Serializable {
	
	private static final long serialVersionUID = 2134576513544568999L;

	
	
    private Long id;
	
	private String name;
	 
	private String address;
	
	private String sourceLat;
	
	private String sourceLong;
	
	private String contact;
	

}
