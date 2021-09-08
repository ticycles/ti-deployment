package com.trackandtrail.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.trackandtrail.common.AuditableBase;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table(name="user_group_member")
@Data
@EqualsAndHashCode(callSuper=false)
public class UserGroupMember extends AuditableBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "user_group_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserGroup userGroup;
    
    @Transient
    private Boolean userGruopJoined;

}
