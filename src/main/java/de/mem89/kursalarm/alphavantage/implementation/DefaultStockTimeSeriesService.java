package de.mem89.kursalarm.alphavantage.implementation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import de.mem89.kursalarm.alphavantage.StockTimeSeriesService;
import de.mem89.kursalarm.alphavantage.exception.AlphaVantageException;
import de.mem89.kursalarm.alphavantage.model.AlphaVantageResponse;
import de.mem89.kursalarm.alphavantage.model.GlobalQuote;
import de.mem89.kursalarm.alphavantage.model.SearchMatch;
import de.mem89.kursalarm.alphavantage.model.StockTimeSeriesFunction;

@Component(StockTimeSeriesService.BEAN_ID)
public class DefaultStockTimeSeriesService implements StockTimeSeriesService {

	@Value("${alphavantage.apiKey}")
	public String API_KEY;

	private static Logger LOG = LoggerFactory.getLogger(DefaultStockTimeSeriesService.class);

	@Override
	public GlobalQuote globalQuote(String symbol) throws AlphaVantageException {
		Assert.hasText(symbol, "'symbol' must not be empty");

		URI uri = assembleGlobalQuoteURL(symbol);
		LOG.debug("uri = {}", uri);

		ResponseEntity<AlphaVantageResponse> response = getRestTemplate().getForEntity(uri, AlphaVantageResponse.class);
		handleResponse(response);

		GlobalQuote globalQuote = response.getBody().getGlobalQuote();
		LOG.debug("globalQuote = {}", globalQuote);
		if(globalQuote == null) throw new AlphaVantageException();

		return globalQuote;
	}

	@Override
	public List<SearchMatch> searchEndpoint(String keywords) throws AlphaVantageException {
		Assert.hasText(keywords, "'keywords' must not be empty");

		URI uri = assembleSearchEndpointURL(keywords);
		LOG.debug("uri = {}", uri);

		ResponseEntity<AlphaVantageResponse> response = getRestTemplate().getForEntity(uri, AlphaVantageResponse.class);
		handleResponse(response);
		
		List<SearchMatch> bestMatches = response.getBody().getBestMatches();
		LOG.debug("bestMatches = {}", bestMatches);
		if(bestMatches == null) throw new AlphaVantageException();

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

	private void handleResponse(ResponseEntity<AlphaVantageResponse> response) {
		HttpStatus statusCode = response.getStatusCode();
		if (!HttpStatus.OK.equals(statusCode)) {
			LOG.warn("status code = {}", statusCode);
		}

		AlphaVantageResponse body = response.getBody();
		if (body == null) {
			LOG.error("Response body is null");
		} else {
			String information = body.getInformation();
			if(StringUtils.isNotBlank(information)) {
				LOG.warn("information = {}", information);
			}

			String note = body.getNote();
			if(StringUtils.isNotBlank(note)) {
				LOG.warn("note = {}", note);
			}
		}
	}
}
