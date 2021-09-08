package com.trackandtrail.model.mycycle;


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

import com.trackandtrail.common.AuditableBase;
import com.trackandtrail.model.registerbike.BikeBrand;
import com.trackandtrail.model.user.UserModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name ="my_cycle")
@Data
@EqualsAndHashCode(callSuper=false)
public class MyCycle extends AuditableBase  {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="my_cycle_id")
	private Long id;
	
	
    private String image;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "brand_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BikeBrand bikeBrand;
	
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel user;

}
