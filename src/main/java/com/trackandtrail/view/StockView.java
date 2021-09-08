package com.trackandtrail.view;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Immutable;

import com.trackandtrail.model.stock.Stock;

import lombok.Data;

@Data
@Entity
@Immutable
public class StockView {
	
	@Id	
	private Integer availableQuantity;
	
	
    private Integer bookedQuantity;

	 
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "stock_id", nullable = false)
	private Stock stock;
    

}
