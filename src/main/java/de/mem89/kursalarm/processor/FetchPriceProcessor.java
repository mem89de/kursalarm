package de.mem89.kursalarm.processor;

import java.math.BigDecimal;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import de.mem89.kursalarm.model.Stock;

@Component(FetchPriceProcessor.BEAN_ID)
public class FetchPriceProcessor implements ItemProcessor<Stock, Stock> {
	
	public static final String BEAN_ID = "de.mem89.kursalarm.processor.fetchPriceProcessor";
	
	@Override
	public Stock process(Stock stock) throws Exception {
		processStock(stock);
		
		return stock;
	}

	private void processStock(Stock stock) {
		stock.setName("Some stock");
		stock.setPrice(BigDecimal.ONE);
	}
}