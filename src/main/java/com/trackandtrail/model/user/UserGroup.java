package com.trackandtrail.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trackandtrail.common.AuditableBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "user_group")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGroup extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_group_id")
	private Long userGroupId;

	private String image;

	private String groupName;

	private String description;
	
	private String type;

	private String facebookUrl;
	
	@Transient
	private Boolean userGroupJoined;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserModel user;

	public UserGroup(Long userGroupId) {
		super();
		this.userGroupId = userGroupId;
	}

	public UserGroup() {
		super();
	}
	
	
}
