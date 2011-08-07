/**
 * 
 */
package mulCal.currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import mulCal.settings.Settings;

import org.junit.Ignore;
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
	public void testNoSettings() throws Exception {
		Settings setting = new Settings();
		Currency currency = new Currency(setting);
		assertEquals(currency.get("GBP").toPlainString(), "1.00");
		assertEquals(currency.get("MYR").toPlainString(), "4.90");
	}

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
		Settings setting = new Settings();
		setting.Set("USD", "10.00");
		Currency currency = new Currency(setting);
		
		assertEquals(currency.convert(new BigDecimal("1.00"), "GBP", "GBP"), new BigDecimal("1.00"));
		assertEquals(currency.convert(new BigDecimal("12.34"), "GBP", "GBP"), new BigDecimal("12.34"));
		assertEquals(currency.convert(new BigDecimal("1.00"), "GBP", "MYR"), new BigDecimal("4.900")); // TODO: unexpected extra decimal place.
	}

	/**
	 * Test method for {@link mulCal.currency.Currency#convert(java.math.BigDecimal, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testSet() throws Exception {
		Settings setting = new Settings();
		setting.Set("USD", "10.00");
		Currency currency = new Currency(setting);
		assertEquals(currency.convert(new BigDecimal("1.00"), "GBP", "USD"), new BigDecimal("10.00"));
	}

	/**
	 * Test method for {@link mulCal.currency.Currency#convert(java.math.BigDecimal, java.lang.String, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testIrrational() throws Exception {
		Settings setting = new Settings();
		setting.Set("USD", "3.00");
		Currency currency = new Currency(setting);
		assertTrue((currency.convert(new BigDecimal("10.00"), "GBP", "USD").doubleValue() -  30.0) < 0.001);
		assertTrue((currency.convert(new BigDecimal("10.00"), "USD", "GBP").doubleValue() -  3.333) < 0.001);
	}
	
	/**
	 * Test method for {@link mulCal.currency.Currency#update()}.
	 * @throws Exception 
	 */
	@Ignore("Best not to piss of XE.com") @Test
	public void testUpdate() throws Exception {
		Settings setting = new Settings();
		setting.Set("USD", "10.00");
		Currency currency = new Currency(setting);
		currency.update();
		assertTrue(currency.get("USD").toPlainString() != "10.00");
		
    	System.out.format("USD %s\n", currency.get("USD").toPlainString());
    	System.out.format("GBP %s\n", currency.get("GBP").toPlainString());
    	System.out.format("MYR %s\n", currency.get("MYR").toPlainString());
	}

	/**
	 * Test method for {@link mulCal.currency.Currency#internetTestConvert(BigDecimal, String, String)}.
	 * @throws Exception 
	 */
	@Test
	public void testOfflineUpdate() throws Exception {
		assertEquals(
				Currency.internetTestConvert(new BigDecimal("9754.00"), "CAD", "AED"), 
				new BigDecimal("36509.79"));
	}
	
	
}
