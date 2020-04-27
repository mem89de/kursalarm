package de.mem89.kursalarm.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import de.mem89.kursalarm.model.StockThreshold;

@Component(StockThresholdReader.BEAN_ID)
public class StockThresholdReader extends FlatFileItemReader<StockThreshold> {
	public static final String BEAN_ID = "de.mem89.kursalarm.reader.stockThresholdReader";
	
	public StockThresholdReader() {
		super();
		this.setResource(new ClassPathResource("data/data.tsv"));
		this.setLineMapper(getLineMapper());
	}
	
	private static LineMapper<StockThreshold> getLineMapper() {
		String[] headers = { "name" };
		
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB);
		lineTokenizer.setNames(headers);
		
		DefaultLineMapper<StockThreshold> lineMapper = new DefaultLineMapper<StockThreshold>();
		lineMapper.setLineTokenizer(lineTokenizer);
		BeanWrapperFieldSetMapper<StockThreshold> fieldSetMapper = new BeanWrapperFieldSetMapper<StockThreshold>();
		fieldSetMapper.setTargetType(StockThreshold.class);
		lineMapper.setFieldSetMapper(fieldSetMapper);

		return lineMapper;
		
	}
}
