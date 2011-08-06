/**
 * 
 */
package mulCal.currency;

import static org.junit.Assert.*;

import mulCal.settings.Settings;

import org.junit.Test;

/**
 * @author james
 *
 */
public class CurrencyTest {

	/**
	 * Test method for {@link mulCal.currency.Currency#get(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testGet() throws Exception {
		Settings setting = new Settings();
		setting.Load("settings.txt");
		Currency currency = new Currency(setting);
		assertEquals(currency.get("GBP").toPlainString(), "1");
		assertEquals(currency.get("MYR").toPlainString(), "50");
	}
	
	/**
	 * Test method for {@link mulCal.currency.Currency#convert(java.math.BigDecimal, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testConvert() throws Exception {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link mulCal.currency.Currency#update()}.
	 */
	@Test
	public void testUpdate() throws Exception {
		fail("Not yet implemented"); // TODO
	}

}
