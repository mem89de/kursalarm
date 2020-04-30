package de.mem89.kursalarm.alphavantage.implementation;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import de.mem89.kursalarm.alphavantage.StockTimeSeriesService;
import de.mem89.kursalarm.alphavantage.model.GlobalQuote;
import de.mem89.kursalarm.alphavantage.model.AlphaVantageResponse;
import de.mem89.kursalarm.alphavantage.model.StockTimeSeriesFunction;

@Component(StockTimeSeriesService.BEAN_ID)
public class DefaultStockTimeSeriesService implements StockTimeSeriesService {

	@Value("${alphavantage.apiKey}")
	public String API_KEY;

	private static Logger LOG = LoggerFactory.getLogger(DefaultStockTimeSeriesService.class);

	@Override
	public GlobalQuote globalQuote(String symbol) {
		Assert.hasText(symbol, "'symbol' must not be empty");
		
		URI uri = assembleGlobalQuoteURL(symbol);
		LOG.debug("uri = {}", uri);
		
		RestTemplate restTemplate = getRestTemplate();
		GlobalQuote globalQuote = restTemplate
				.getForObject(uri, AlphaVantageResponse.class)
				.getGlobalQuote();	
		LOG.debug("globalQuote = {}", globalQuote);
				
		return globalQuote;
	}

	private URI assembleGlobalQuoteURL(String symbol) {
		try {
			return new URIBuilder()
					.setScheme("https")
					.setHost("www.alphavantage.co")
					.setPath("/query")
					.setParameter("function", StockTimeSeriesFunction.GLOBAL_QUOTE.toString())
					.setParameter("apikey", API_KEY)
					.setParameter("symbol", symbol)
					.setParameter("datatype", "json")
					.build();
		} catch (URISyntaxException e) {
			LOG.error("Could not assemble URL", e);
			return null;
		}
	}
	
	private static RestTemplate getRestTemplate() {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		return restTemplateBuilder.build();
	}
}
