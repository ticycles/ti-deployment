package com.trackandtrail.model.notificationAlert;

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

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trackandtrail.model.user.UserModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@Table(name = "notification_alert")

//@TypeDef(
//	    name = "json",
//	    typeClass = JsonType.class
//	)

public class NotificationAlert{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id")
	private Long id;

	private String title;

	private String body;

	private String userType;

	private Long sender;

	private Long receiver;

	private String moduleName;

	private String page;

	@Column(name = "data", columnDefinition = "json")
//	 @Type(type = "json")
//	    @Column(columnDefinition = "jsonb")
	private String data;

	@Column(name="msg_read",columnDefinition = "integer default 0",nullable = false)
	private Integer messageRead=0;
	

	@CreatedDate
	@Temporal(TIMESTAMP)
	@Column(name = "created_on", updatable = false, nullable = true)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	protected Date createdDate;
	
	@ManyToOne(fetch = FetchType.EAGER,optional = true)
	@JoinColumn(name="user_id",nullable = true)
	private UserModel user;

	
	

}
