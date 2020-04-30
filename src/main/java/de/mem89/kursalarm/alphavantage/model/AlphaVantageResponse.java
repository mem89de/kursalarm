package de.mem89.kursalarm.alphavantage.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphaVantageResponse {
	@JsonProperty("Global Quote")
	public GlobalQuote globalQuote;
	
	public List<SearchMatch> bestMatches;
}
