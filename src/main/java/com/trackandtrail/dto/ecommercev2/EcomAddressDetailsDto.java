package com.trackandtrail.dto.ecommercev2;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.trackandtrail.model.registerbike.City;
import com.trackandtrail.model.registerbike.State;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class EcomAddressDetailsDto {
	
	
	private Long id;

	private UserModel user;

	private String firstName;

	private String lastName;

	private String mobile;

	private String pinCode;

	private String doorNo;

	private String street;

	private String landMark;

	private String city;

	private String state;
	
	private String addressType;


}
