package com.trackandtrail.model.bikerentalmanagement;

import static javax.persistence.TemporalType.TIMESTAMP;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.registerbike.RegisterBike;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="bike_booking")
@Data
@EqualsAndHashCode(callSuper=false)
@EntityListeners(AuditingEntityListener.class)
public class BikeRentalBooking extends AuditableBase {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="booking_id")
	private Long bookingId;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Temporal(TIMESTAMP)
	@Column(name="from_date")
	private Date fromDate;

	@JsonFormat(pattern = "dd-MM-yyyy")
	@Temporal(TIMESTAMP)
	@Column(name="to_date")
	private Date  toDate;
	
	
	private String totalCost;
	
	private Integer quantity;
	
	private String uuid;

	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserModel userModel;
	

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "bike_rental_product_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BikeRentalProduct bikeRentalProduct;
	

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "store_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private StoreDetail storeDetail;
	
	
	private String rejectReason;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Temporal(TIMESTAMP)
	private Date rejectionDate;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Temporal(TIMESTAMP)
	private Date conformDate;
	

	@JsonFormat(pattern = "dd-MM-yyyy")
	@Temporal(TIMESTAMP)
	private Date returnDate;
	
	
	

}




