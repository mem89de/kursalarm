package de.mem89.kursalarm.model;

import java.math.BigDecimal;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.Data;

@Data
public class Stock {
	private String name;

	private String symbol;

	private BigDecimal price;

	private BigDecimal upperThreshold;

	public String getDisplayName() {
		if (StringUtils.isBlank(name)) {
			return symbol;
		}
		return name;
	}

	public boolean isUpperTresholdExceeded() {
		if (upperThreshold == null || price == null)
			return false;
		return price.compareTo(upperThreshold) <= 0;
	}
}
