package com.trackandtrail.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.model.event.Event;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
public class EventUserDto implements Serializable {

	private static final long serialVersionUID = 2134576513544568999L;

	private Long id;

	private Event event;

	private UserModel user;

	private Date dateOfJoining;

	
}
