package com.trackandtrail.view;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.trackandtrail.model.event.Event;
import com.trackandtrail.model.event.EventUser;

import lombok.Data;


@Data
@Entity
@Immutable
public class EventCount {
	
	@Id
	private Long eventId;
	
	private Long joined;
	
	 
  

}
