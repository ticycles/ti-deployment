package com.trackandtrail.model.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trackandtrail.common.AuditableBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "static_param")
@Data
@EqualsAndHashCode(callSuper = false)
public class StaticParamModel extends AuditableBase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "static_param_id")
	private Long id;
	
	@Column(name = "key",nullable = false)
	private String key;
	
	@Column(name = "value",nullable = false)
	private String value;
	
	@Column(name = "display_order",nullable = false)
	private Long displayOrder;
}
