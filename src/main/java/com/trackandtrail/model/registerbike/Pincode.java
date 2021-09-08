package com.trackandtrail.model.registerbike;

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

import lombok.Data;
@Entity
@Table(name = "pincode")
@Data
public class Pincode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pincode_id", nullable = false)
    private Long id;

    private String name;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
   	@JoinColumn(name = "city_id", nullable = true)
   	@OnDelete(action = OnDeleteAction.CASCADE)
    private City city;

   
}
   



   
