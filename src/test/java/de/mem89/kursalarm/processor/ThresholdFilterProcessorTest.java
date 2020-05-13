package de.mem89.kursalarm.processor;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import de.mem89.kursalarm.model.Stock;

@RunWith(MockitoJUnitRunner.class)
public class ThresholdFilterProcessorTest {

    @InjectMocks
    private ThresholdFilterProcessor processor;

    @Test
    public void processStockExceedsThreshold() throws Exception {
	// given
	Stock stock = getStock(false);

	// when
	Stock result = processor.process(stock);

	// then
	assertThat(result, is(nullValue()));
    }

    @Test
    public void processStockDoesNotExceedThreshold() throws Exception {
	// given
	Stock stock = getStock(true);

	// when
	Stock result = processor.process(stock);

	// then
	assertThat(result, is(stock));
    }

    @Test
    public void processNullSafe() throws Exception {
	// given
	Stock stock = null;

	// when
	Stock result = processor.process(stock);

	// then
	assertThat(result, is(nullValue()));
    }

    private Stock getStock(boolean isUpperTresholdExceeded) {
	Stock stock = mock(Stock.class);
	doReturn(isUpperTresholdExceeded).when(stock).isUpperTresholdExceeded();
	return stock;
    }
}
