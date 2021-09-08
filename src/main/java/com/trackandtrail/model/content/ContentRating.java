package com.trackandtrail.model.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

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

@Entity
@Table(name ="content_rating")
@Data
public class ContentRating extends AuditableBase {
    @Column(name = "rating_id")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel user;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "content_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Content content;
    
 
    private Float ratingStar;

    private String comment;
    
    private Boolean isLike; 

}
