package de.mem89.kursalarm;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.mem89.kursalarm.model.Stock;
import de.mem89.kursalarm.processor.InfoProcessor;
import de.mem89.kursalarm.processor.PriceProcessor;
import de.mem89.kursalarm.processor.ThresholdFilterProcessor;
import de.mem89.kursalarm.reader.StockThresholdReader;
import de.mem89.kursalarm.writer.LogWriter;

@EnableBatchProcessing
@Configuration
public class JobConfig {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Resource(name = StockThresholdReader.BEAN_ID)
	public ItemReader<Stock> reader;

	@Resource(name = InfoProcessor.BEAN_ID)
	public ItemProcessor<Stock,Stock> infoProcessor;

	@Resource(name = PriceProcessor.BEAN_ID)
	public ItemProcessor<Stock,Stock> priceProcessor;
	
	@Resource(name = ThresholdFilterProcessor.BEAN_ID)
	public ItemProcessor<Stock,Stock> thresholdFilterProcessor;
	
	@Resource(name = LogWriter.BEAN_ID)
	public ItemWriter<Stock> writer;

	ItemProcessor<Stock,Stock> chainProcessor() {
		CompositeItemProcessor<Stock, Stock> processor = new CompositeItemProcessor<>();
		processor.setDelegates(Arrays.asList(
				infoProcessor, 
				priceProcessor,
				thresholdFilterProcessor
				));
		
		return processor;
	}
	
	@Bean
	Step fetchPrices() {
		return stepBuilderFactory.get("fileToLogStep")
				.<Stock, Stock>chunk(1)
				.reader(reader)
				.processor(chainProcessor())
				.writer(writer)
				.build();
	}

	@Bean
	Job fileToLogJob(JobExecutionListener listener) {
		return jobBuilderFactory.get("fileToLogJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(fetchPrices())
				.end()
				.build();
	}
}
