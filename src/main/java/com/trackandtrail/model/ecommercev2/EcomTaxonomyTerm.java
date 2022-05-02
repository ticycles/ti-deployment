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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.common.AuditableBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ecomm_taxonomy_term")
@Getter @Setter
@EqualsAndHashCode(callSuper=false)
public class EcomTaxonomyTerm extends AuditableBase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "term_id")
	private Long id;

	@Column(name = "term_name")
	private String termName;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "taxonomy_id", nullable = true)
	private EcomTaxonomy taxonomy;
	
	private String imageUrl;
	
	private Long tid;
	
	@Column(name = "type")
	private String type;

}
