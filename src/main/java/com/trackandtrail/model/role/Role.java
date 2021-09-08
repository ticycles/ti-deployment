package com.trackandtrail.model.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id", nullable = false)
	
	private Long roleId;

	@Column(name = "role_name", nullable = false)
	private String name;
	
	
	public Role(Long roleId) {
		super();
		this.roleId = roleId;
	}
	
	

	public Role() {
		super();
	}



	public Role(String name) {
		super();
		this.name = name;
	}



	public Role(Long roleId, String name) {
		super();
		this.roleId = roleId;
		this.name = name;
	}

	
	
	


}
