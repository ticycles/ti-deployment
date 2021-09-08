package com.trackandtrail.model.user;

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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;


@Entity
@Table(name="user_follow")
@Data
public class UserFollow {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "following_user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel following;

    @Column(name = "created_on", nullable = false)
    private Date createdOn = new Date();
}
