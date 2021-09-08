package com.trackandtrail.model.challenge;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "ride_type")
@Data
public class RideType {
    @Column(name = "ride_type_id")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String rideName;
}
