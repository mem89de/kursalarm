package de.mem89.kursalarm.processor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import de.mem89.kursalarm.alphavantage.StockTimeSeriesService;
import de.mem89.kursalarm.alphavantage.model.SearchMatch;
import de.mem89.kursalarm.model.Stock;

@Component(InfoProcessor.BEAN_ID)
public class InfoProcessor implements ItemProcessor<Stock, Stock> {

	public static final String BEAN_ID = "de.mem89.kursalarm.processor.infoProcessor";

	private static Logger LOG = LoggerFactory.getLogger(InfoProcessor.class);
	
	@Resource(name = StockTimeSeriesService.BEAN_ID)
	private StockTimeSeriesService stockTimeSeriesService;

	@Override
	public Stock process(Stock stock) throws Exception {
		String symbol = stock.getSymbol();
		LOG.info("Querying meta data for {}", symbol);
		
		List<SearchMatch> matches = stockTimeSeriesService.searchEndpoint(symbol);
		
		if(!matches.isEmpty()) {
			Optional<SearchMatch> matchOptional = matches.stream()
					.filter(m -> BigDecimal.ONE.compareTo(m.getMatchScore()) == 0)
					.findFirst();
			
			if(matchOptional.isPresent()) {
				SearchMatch match = matchOptional.get();
				stock.setName(match.getName());
				
				LOG.debug("stock = {}", stock);
				return stock;
			}
		}
		
		LOG.warn("There where no results for symbol '{}'", symbol);
		return stock;
	}

}