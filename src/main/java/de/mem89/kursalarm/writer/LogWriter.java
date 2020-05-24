package de.mem89.kursalarm.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import de.mem89.kursalarm.model.Stock;

@Component(LogWriter.BEAN_ID)
public class LogWriter implements ItemWriter<Stock> {
	public static final String BEAN_ID = "de.mem89.kursalarm.writer.logWriter";
	private static Logger LOG = LoggerFactory.getLogger(LogWriter.class);

	@Override
	public void write(List<? extends Stock> stocks) throws Exception {
		for (Stock stock : stocks) {
			LOG.info("Stock {} exceeded the threshold of {}. Current price is {}", stock.getDisplayName(),stock.getUpperThreshold(), stock.getPrice());
		}
	}
}
