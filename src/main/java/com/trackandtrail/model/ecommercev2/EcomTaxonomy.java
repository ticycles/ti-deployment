package com.trackandtrail.model.ecommercev2;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trackandtrail.model.common.StaticParamModel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="ecomm_taxonomy")
@Getter @Setter
public class EcomTaxonomy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name="taxonomy_id")
	private Long id;
	
	@Column(name="taxonomy_name")
	private String taxonomyName;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "category_id", nullable = true)
	private StaticParamModel category;
	

//	@JsonIgnore
	@OneToMany( mappedBy = "taxonomy", cascade = CascadeType.ALL)
//	@OneToMany(mappedBy="taxonomy",cascade = {CascadeType.ALL},fetch= FetchType.EAGER)
	private List<EcomTaxonomyTerm> term=new ArrayList<>();	
	
	private Long vid;
	
}
