package de.mem89.kursalarm.model;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public class Stock {
	private String name;
	
	private String symbol;
	
	private BigDecimal price;
}
