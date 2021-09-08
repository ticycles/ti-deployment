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
import com.trackandtrail.model.user.UserModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ecomm_add_to_cart")
@Data
@EqualsAndHashCode(callSuper=false)
public class EcomCart extends AuditableBase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	private UserModel user;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "variant_id", nullable = true)
	private EcomProductVariant variant;
	
	private Integer quantity;

}
