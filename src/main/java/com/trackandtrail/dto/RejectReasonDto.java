package com.trackandtrail.dto;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalBooking;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class RejectReasonDto extends AuditableBase {

	
	@Id
	private Long bookingId;

	private Date fromDate;

	private Date toDate;

	private String totalCost;

	private Integer quantity;

	private String uuid;

	private UserModel userModel;

	private BikeRentalProduct bikeRentalProduct;

	private StoreDetail storeDetail;

	private String rejectReason;

	private Date rejectionDate;
	
	private Date conformDate;

	private Date returnDate;


}
