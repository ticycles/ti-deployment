package com.trackandtrail.notification;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO  implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long mobileNumber;
	private String smsContent;
	private String serviceName;
	private String serviceCode;
	private String to;
	private String sub;
	private String text;
	
	public NotificationDTO(Long mobileNumber, String smsContent, String serviceName) {
		super();
		this.mobileNumber = mobileNumber;
		this.smsContent = smsContent;
		this.serviceName = serviceName;
	}

	public NotificationDTO(String serviceName, String to, String sub, String text) {
		super();
		this.serviceName = serviceName;
		this.to = to;
		this.sub = sub;
		this.text = text;
	}

	public NotificationDTO(Long mobileNumber, String smsContent, String serviceName ,String to,
			String sub, String text) {
		super();
		this.mobileNumber = mobileNumber;
		this.smsContent = smsContent;
		this.serviceName = serviceName;
		this.to = to;
		this.sub = sub;
		this.text = text;
	}
	
	
	

}
