package de.mem89.kursalarm.processor;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import de.mem89.kursalarm.alphavantage.StockTimeSeriesService;
import de.mem89.kursalarm.alphavantage.model.GlobalQuote;
import de.mem89.kursalarm.model.Stock;

@Component(PriceProcessor.BEAN_ID)
public class PriceProcessor implements ItemProcessor<Stock, Stock> {

	public static final String BEAN_ID = "de.mem89.kursalarm.processor.priceProcessor";

	private static Logger LOG = LoggerFactory.getLogger(PriceProcessor.class);

	@Resource(name = StockTimeSeriesService.BEAN_ID)
	private StockTimeSeriesService stockTimeSeriesService;

	@Override
	public Stock process(Stock stock) throws Exception {
		LOG.info("Querying price for {}", stock.getSymbol());

		GlobalQuote globalQuote = stockTimeSeriesService.globalQuote(stock.getSymbol());
		stock.setPrice(globalQuote.getPrice());

		LOG.debug("stock = {}", stock);
		return stock;
	}
}