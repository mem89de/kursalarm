package de.mem89.kursalarm.writer;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component(LogWriter.BEAN_ID)
public class LogWriter<T> implements ItemWriter<T> {
	public static final String BEAN_ID = "de.mem89.kursalarm.writer.logWriter";
	private static Logger LOG = LoggerFactory.getLogger(LogWriter.class);

	@Override
	public void write(List<? extends T> items) throws Exception {
		for (T item : items) {
			LOG.info(Objects.toString(item));
		}
	}
}
