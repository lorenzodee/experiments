package revenue.recognition.interfaces.facade;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.junit.Test;

public class MonetaryAmountTests {

	private static final CurrencyUnit USD = Monetary.getCurrency("USD");

	@Test
	public void fromMonetaryAmountToBigDecimal() throws Exception {
		MonetaryAmount amount;
		amount = Monetary.getDefaultAmountFactory()
					.setCurrency(USD)
					.setNumber(9.95)
					.create();
		BigDecimal numberValue;
		numberValue = amount.getNumber().numberValue(BigDecimal.class);
		assertEquals(USD.getDefaultFractionDigits(), numberValue.scale());
		assertEquals(new BigDecimal("9.95"), numberValue);

		amount = Monetary.getDefaultAmountFactory()
					.setCurrency(USD)
					.setNumber(5000.00)
					.create();
		numberValue = amount.getNumber().numberValue(BigDecimal.class);
		// assertEquals(USD.getDefaultFractionDigits(), numberValue.scale());
		numberValue = numberValue.setScale(USD.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
		assertEquals(new BigDecimal("5000.00"), numberValue);
		numberValue = numberValue.divide(new BigDecimal(3), RoundingMode.HALF_EVEN);
		assertEquals(new BigDecimal("1666.67"), numberValue);
	}
}
