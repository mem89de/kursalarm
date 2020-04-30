package de.mem89.kursalarm.alphavantage.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GlobalQuote {
	@JsonProperty("01. symbol")
	public String symbol;
	
	@JsonProperty("02. open")
	public BigDecimal open;
	
	@JsonProperty("03. high")
	public BigDecimal high;
	
	@JsonProperty("04. low")
	public BigDecimal low;
	
	@JsonProperty("05. price")
	private BigDecimal price;
	
	@JsonProperty("06. volume")
	public Integer volume;
	
//	@JsonProperty("07. latest trading day")
//	public LocalDate latestTradingDay;
	
	@JsonProperty("08. previous close")
	public BigDecimal previousClose;
	
	@JsonProperty("09. change")
	public BigDecimal change;
	
	
//	@JsonProperty("10. change percent")
//	public BigDecimal changePercent;
	
}
