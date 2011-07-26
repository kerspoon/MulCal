package mulCal.history;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import mulCal.history.History.HistoryItem;
import mulCal.util.KeyException;


import org.junit.Test;

public class HistoryTest {
	
	@Test
	public void testBasic() throws Exception {
		History history = new History();
		history.Add("1+1", new BigDecimal("2"));
		HistoryItem first = history.get("A");
		assertEquals(first.id, "A");
		assertEquals(first.equation, "1+1");
		assertEquals(first.result, new BigDecimal("2"));
		assertEquals(first.comment, "");
	}

	@Test
	public void testAll() throws Exception {
		History history = new History();
		history.Add("1+1", new BigDecimal("2"));
		HistoryItem first = history.get("A");
		assertEquals(first.id, "A");
	}

	@Test(expected=Exception.class)
	public void testFail() throws Exception {
		History history = new History();
		history.Add("", new BigDecimal("2"));
	}
	
	@Test
	public void testSaveLoad() throws KeyException, IOException {
		History history = new History();
		history.Add("1+1", new BigDecimal("2"));
		history.Add("1+2", new BigDecimal("3"));

		history.Save("temp.tmp");
		History newHistory = new History();
		newHistory.Load("temp.tmp");

		HistoryItem first = newHistory.get("A");
		assertEquals(first.id, "A");
		assertEquals(first.equation, "1+1");
		assertEquals(first.result, new BigDecimal("2"));
		assertEquals(first.comment, "");
		
	}

	@Test
	public void testNoConstants() throws Exception {
		// check that it doesn't return E as an id as it is a constant
		History history = new History();
		history.Add("0+1", new BigDecimal("1")); // A
		history.Add("0+2", new BigDecimal("2")); // B
		history.Add("0+3", new BigDecimal("3")); // C
		history.Add("0+4", new BigDecimal("4")); // D
		history.Add("0+5", new BigDecimal("5")); // F*
		history.Add("0+6", new BigDecimal("6")); // G

		assertEquals(history.get("A").result, new BigDecimal("1"));
		assertEquals(history.get("B").result, new BigDecimal("2"));
		assertEquals(history.get("C").result, new BigDecimal("3"));
		assertEquals(history.get("D").result, new BigDecimal("4"));
		assertEquals(history.get("F").result, new BigDecimal("5")); // note not E
		assertEquals(history.get("G").result, new BigDecimal("6"));
		
	}
}
