package com.trackandtrail.model.ecommercev2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.common.StaticParamModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ecomm_variant_spec")
@Data
@EqualsAndHashCode(callSuper = false)
public class EcomProductSpecification extends AuditableBase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "variant_spec_id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "product_id", nullable = false)
	private EcomProduct product;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "spec_key", nullable = false)
	private StaticParamModel specKey;
	
	private String specValue;
}
