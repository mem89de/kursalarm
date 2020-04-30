package de.mem89.kursalarm.alphavantage;

import java.util.List;

import de.mem89.kursalarm.alphavantage.model.GlobalQuote;
import de.mem89.kursalarm.alphavantage.model.SearchMatch;

public interface StockTimeSeriesService {
	public static final String BEAN_ID =  "de.mem89.kursalarm.alphavantage.implementation.defaultStockTimeSeriesService";
	
	public GlobalQuote globalQuote(String symbol);
	
	public List<SearchMatch> searchEndpoint(String keywords);
}
