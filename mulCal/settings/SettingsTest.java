package mulCal.settings;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

import mulCal.util.KeyException;

import org.junit.Test;

public class SettingsTest {
	
	@Test
	public void testDefaultVAT() throws KeyException {
		Settings set = new Settings();
		assertEquals("17.5", set.Get("vat"));
	}

	@Test
	public void testDefaultGBP() throws KeyException {
		Settings set = new Settings();
		assertEquals("1.0", set.Get("GBP"));
	}
	
    @Test(expected=KeyException.class)
	public void testGetKeyError() throws KeyException {
		Settings set = new Settings();
		set.Get("notHere");
	}
    
    @Test(expected=KeyException.class)
	public void testGetKeyCaseError() throws KeyException {
		Settings set = new Settings();
		set.Get("vAt");
	}
    
	@Test
	public void testSetVAT() throws KeyException {
		Settings set = new Settings();
		set.Set("vat", "20");
		assertEquals("20", set.Get("vat"));
	}

	@Test
	public void testSetMYR() throws KeyException {
		Settings set = new Settings();
		set.Set("MYR", "4.850");
		assertEquals("4.850", set.Get("MYR"));
	}

	@Test(expected=KeyException.class)
	public void testSetKeyError() throws KeyException {
		Settings set = new Settings();
		set.Set("notHere", "1");
	}
	
	@Test(expected=KeyException.class)
	public void testSetKeyCaseError() throws KeyException {
		Settings set = new Settings();
		set.Set("vAt", "1");
	}
	
	@Test
	public void testSaveLoad() throws KeyException, IOException {
		Settings set = new Settings();
		set.Set("vat", "25");
		set.Set("USD", "1.8");
		set.Save("temp.tmp");
		Settings newSet = new Settings();
		newSet.Load("temp.tmp");
		assertEquals("25", set.Get("vat"));
		assertEquals("1.8", set.Get("USD"));
	}
}
