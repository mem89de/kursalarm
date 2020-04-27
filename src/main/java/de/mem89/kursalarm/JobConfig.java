package de.mem89.kursalarm;

import javax.annotation.Resource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.mem89.kursalarm.model.StockThreshold;
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
	public ItemReader<StockThreshold> reader;

	@Resource(name = LogWriter.BEAN_ID)
	public ItemWriter<StockThreshold> writer;

	@Bean
	Step fileToLogStep() {
		return stepBuilderFactory.get("fileToLogStep")
				.<StockThreshold, StockThreshold>chunk(1)
				.reader(reader)
				.writer(writer)
				.build();
	}

	@Bean
	Job fileToLogJob(JobExecutionListener listener) {
		return jobBuilderFactory.get("fileToLogJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(fileToLogStep())
				.end()
				.build();
	}
}
