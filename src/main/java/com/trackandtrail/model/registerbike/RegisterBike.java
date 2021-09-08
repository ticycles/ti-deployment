package com.trackandtrail.model.registerbike;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "register_bike")
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterBike extends AuditableBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "register_bike_id")
	private Long id;

	@Column(name = "customer_name", unique = true)
	private String customerName;

	@Column(name = "contact_number", unique = true)
	private String contactNumber;

	@Column(name = "e_mail", unique = true)
	private String email;

	private String gender;

	private String age;

	private String address;

	@Column(name = "date_of_purchase")
	@Temporal(TIMESTAMP)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateOfPurchase;

	@Column(name = "frame_serial_number")
	private String frameSerialNumber;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "brand_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BikeBrand bikeBrand;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "model_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BikeModel bikeModel;

	@Column(name = "invoice_image")
	private String invoiceImage;

	@Column(name = "frame_serial_no_image")

	private String frameSerialNoImage;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserModel user;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "state_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private State state;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "city_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private City city;

	private String pincode;

	private String image;

}
