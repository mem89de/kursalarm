package de.mem89.kursalarm.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import de.mem89.kursalarm.model.Stock;

@ExtendWith(MockitoExtension.class)
public class ThresholdFilterProcessorTest {

    @InjectMocks
    private ThresholdFilterProcessor processor;

    @Test
    public void processStockExceedsThreshold() throws Exception {
	// given
	Stock stock = createMockedStockWhereUpperThresholdIs(false);

	// when
	Stock result = processor.process(stock);

	// then
	assertNull(result);
    }

    @Test
    public void processStockDoesNotExceedThreshold() throws Exception {
	// given
	Stock stock = createMockedStockWhereUpperThresholdIs(true);

	// when
	Stock result = processor.process(stock);

	// then
	assertEquals(stock, result);
    }

    @Test
    public void processNullSafe() throws Exception {
	// given
	Stock stock = null;

	// when
	Stock result = processor.process(stock);

	// then
	assertNull(result);
    }

    private Stock createMockedStockWhereUpperThresholdIs(boolean isUpperTresholdExceeded) {
	Stock stock = mock(Stock.class);
	doReturn(isUpperTresholdExceeded).when(stock).isUpperTresholdExceeded();
	return stock;
    }
}
