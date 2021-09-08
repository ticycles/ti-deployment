package com.trackandtrail.model.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Entity
@Table(name = "event_user")
@Data
public class EventUser extends AuditableBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_user_id")
    private Long id;

    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "event_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Event event;

    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel user;

    @Column(name = "date_of_joining")
    private Date dateOfJoining;
    
    
   

	

}
