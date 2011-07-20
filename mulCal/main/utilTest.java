package mulCal.main;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;

import static mulCal.main.util.removeQuotes;
import static mulCal.main.util.Base10toN;

public class utilTest {
	@Test
	public void testRemoveQuotes() throws Exception {
		assertEquals("hello", removeQuotes("'hello'"));
		assertEquals("hello", removeQuotes("\"hello\""));
		assertEquals("", removeQuotes("\"\""));
		assertEquals("", removeQuotes("''"));
	}
	@Test
	public void testRemoveQuotes2() throws Exception {
		assertEquals("", removeQuotes("\"\""));
	}
	
	@Test
	public void testConvertBinary() throws Exception {
		assertEquals("0", Base10toN(0, "01"));
		assertEquals("1", Base10toN(1, "01"));
		assertEquals("10", Base10toN(2, "01"));
		assertEquals("11", Base10toN(3, "01"));
		assertEquals("100", Base10toN(4, "01"));
	}
	
	@Test
	public void testConvertHex() throws Exception {
		assertEquals("0", Base10toN(0, "0123456789ABCDEF"));
		assertEquals("1", Base10toN(1, "0123456789ABCDEF"));
		assertEquals("2", Base10toN(2, "0123456789ABCDEF"));
		assertEquals("F", Base10toN(15, "0123456789ABCDEF"));
		assertEquals("B9", Base10toN(185, "0123456789ABCDEF"));
	}
	
	@Test
	public void testConvertUniqChar() throws Exception {
		assertEquals("a", Base10toN(0, "abcdefghij"));
		assertEquals("b", Base10toN(1, "abcdefghij"));
		assertEquals("c", Base10toN(2, "abcdefghij"));
		assertEquals("j", Base10toN(9, "abcdefghij"));
		assertEquals("ba", Base10toN(10, "abcdefghij"));
	}

	
}



//for instance: base10toN(num,"0123456789ABCDEF") is convert to hex
//for instance: base10toN(num,"ABCDEFGHIJKLMNOP") is also hex with a diff look
//for instance: base10toN(num,"01") convert to binary