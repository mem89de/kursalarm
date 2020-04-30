package de.mem89.kursalarm.alphavantage.implementation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
import de.mem89.kursalarm.alphavantage.model.SearchMatch;
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

	@Override
	public List<SearchMatch> searchEndpoint(String keywords) {
		Assert.hasText(keywords, "'keywords' must not be empty");
		
		URI uri = assembleSearchEndpointURL(keywords);
		LOG.debug("uri = {}", uri);
		
		RestTemplate restTemplate = getRestTemplate();
		List<SearchMatch> bestMatches = restTemplate
				.getForObject(uri, AlphaVantageResponse.class)
				.getBestMatches();	
		LOG.debug("bestMatches = {}", bestMatches);
		
		return bestMatches;
	}

	private URI assembleGlobalQuoteURL(String symbol) {
		try {
			return getURIBuilder(StockTimeSeriesFunction.GLOBAL_QUOTE)
					.setParameter("symbol", symbol)
					.build();
		} catch (URISyntaxException e) {
			LOG.error("Could not assemble Global Quote URL", e);
			return null;
		}
	}

	private URI assembleSearchEndpointURL(String keywords) {
		try {
			return getURIBuilder(StockTimeSeriesFunction.SYMBOL_SEARCH)
					.setParameter("keywords", keywords)
					.build();
		} catch (URISyntaxException e) {
			LOG.error("Could not assemble Global Quote URL", e);
			return null;
		}
	}

	private static RestTemplate getRestTemplate() {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		return restTemplateBuilder.build();
	}

	private URIBuilder getURIBuilder(StockTimeSeriesFunction function) {
		Assert.notNull(function, "'function' must not be empty");
		return new URIBuilder()
				.setScheme("https")
				.setHost("www.alphavantage.co")
				.setPath("/query")
				.setParameter("apikey", API_KEY)
				.setParameter("function", function.toString())
				.setParameter("datatype", "json");
	}
}
