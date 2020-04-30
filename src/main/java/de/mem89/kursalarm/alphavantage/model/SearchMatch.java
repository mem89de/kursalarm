package de.mem89.kursalarm.alphavantage.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchMatch {
	@JsonProperty("1. symbol")
	public String symbol;
	
	@JsonProperty("2. name")
	public String name;
	
	@JsonProperty("3. type")
	public String type;
	
	@JsonProperty("4. region")
	public String region;
	
	@JsonProperty("5. marketOpen")
	public String marketOpen;
	
	@JsonProperty("6. marketClose")
	public String marketClose;
	
	@JsonProperty("7. timezone")
	public String timezone;
	
	@JsonProperty("8. currency")
	public String currency;
	
	@JsonProperty("9. matchScore")
	public BigDecimal matchScore;
}
