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
		HistoryItem first = history.Get("A");
		assertEquals(first.id, "A");
		assertEquals(first.equation, "1+1");
		assertEquals(first.result, new BigDecimal("2"));
		assertEquals(first.comment, "");
	}

	@Test
	public void testAll() throws Exception {
		History history = new History();
		history.Add("1+1", new BigDecimal("2"));
		HistoryItem first = history.Get("A");
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

		HistoryItem first = newHistory.Get("A");
		assertEquals(first.id, "A");
		assertEquals(first.equation, "1+1");
		assertEquals(first.result, new BigDecimal("2"));
		assertEquals(first.comment, "");
		
	}
}
