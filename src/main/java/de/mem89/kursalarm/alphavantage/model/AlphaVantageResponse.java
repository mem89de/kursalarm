package de.mem89.kursalarm.alphavantage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphaVantageResponse {
	@JsonProperty("Global Quote")
	public GlobalQuote globalQuote;
}
