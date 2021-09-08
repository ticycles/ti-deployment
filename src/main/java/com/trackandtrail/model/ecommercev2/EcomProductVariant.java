package com.trackandtrail.model.ecommercev2;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchProfiles;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.MapKeyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Persistent;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trackandtrail.common.AuditableBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "ecomm_variant")
@Data
@EqualsAndHashCode(callSuper = false)
public class EcomProductVariant extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "variant_id")
	private Long id;

	@Column(name = "variant_name")
	private String variantName;

	@Column(name = "variant_code")
	private String variantCode;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "product_id", nullable = true)
	private EcomProduct product;

	private String price;
	private String productUrl;
	private Integer quantity;
	private Double discountPercentage;
	private Double discountAmount;
	private String image1;
	private String image2;
	private String image3;
	private String image4;
	private String image5;
	private String videoUrl;

	private String description;

	@Transient
	private Integer averageRating;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "product_type_term_id")
	private EcomTaxonomyTerm productType;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "color_term_id")
	private EcomTaxonomyTerm colorTermId;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "size_term_id")
	private EcomTaxonomyTerm sizeTermId;

}