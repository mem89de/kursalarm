package de.mem89.kursalarm.processor;

import javax.annotation.Resource;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import de.mem89.kursalarm.alphavantage.StockTimeSeriesService;
import de.mem89.kursalarm.model.Stock;

@Component(ThresholdFilterProcessor.BEAN_ID)
public class ThresholdFilterProcessor implements ItemProcessor<Stock, Stock> {

	public static final String BEAN_ID = "de.mem89.kursalarm.processor.thresholdFilterProcessor";

	@Resource(name = StockTimeSeriesService.BEAN_ID)
	private StockTimeSeriesService stockTimeSeriesService;

	@Override
	public Stock process(Stock stock) throws Exception {
		return stock.isUpperTresholdExceeded() ? stock : null;
	}
}