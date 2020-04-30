package de.mem89.kursalarm.alphavantage;

import de.mem89.kursalarm.alphavantage.model.GlobalQuote;

public interface StockTimeSeriesService {
	public static final String BEAN_ID =  "de.mem89.kursalarm.alphavantage.implementation.defaultStockTimeSeriesService";
	
	public GlobalQuote globalQuote(String symbol);
}
