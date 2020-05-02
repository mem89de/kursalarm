package de.mem89.kursalarm.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import de.mem89.kursalarm.model.Stock;

@Component(StockThresholdReader.BEAN_ID)
public class StockThresholdReader extends FlatFileItemReader<Stock> {
	public static final String BEAN_ID = "de.mem89.kursalarm.reader.stockThresholdReader";
	
	public StockThresholdReader() {
		super();
		this.setResource(new ClassPathResource("data/data.tsv"));
		this.setLineMapper(getLineMapper());
	}
	
	private static LineMapper<Stock> getLineMapper() {
		String[] headers = { "symbol", "upperThreshold" };
		
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB);
		lineTokenizer.setNames(headers);
		
		DefaultLineMapper<Stock> lineMapper = new DefaultLineMapper<Stock>();
		lineMapper.setLineTokenizer(lineTokenizer);
		BeanWrapperFieldSetMapper<Stock> fieldSetMapper = new BeanWrapperFieldSetMapper<Stock>();
		fieldSetMapper.setTargetType(Stock.class);
		lineMapper.setFieldSetMapper(fieldSetMapper);

		return lineMapper;
	}
}
