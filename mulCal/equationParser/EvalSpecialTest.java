package mulCal.equationParser;

import static junit.framework.Assert.assertEquals;
import static mulCal.equationParser.Tokenize.toTokens;
import java.math.BigDecimal;
import java.util.List;

import mulCal.currency.Currency;
import mulCal.equationParser.Tokenize.Token;
import mulCal.history.History;
import mulCal.settings.Settings;
import mulCal.util.KeyException;

import org.junit.Test;

public class EvalSpecialTest {

	@Test
	public void testEvalSpecial() throws Exception  {
		Settings settings = new Settings();
		Currency currency = new Currency(settings);
		EvalSpecial evalSpecial = new EvalSpecial(currency);
		
		// create history
		History history = new History();
		history.Add("1+1", new BigDecimal("2"));
		history.Add("A*3", new BigDecimal("6"));
		
		// create token list
		List<Token> tokens = toTokens("1+2");
		
		// eval
		List<Token> result = evalSpecial.eval(tokens, history);

		assertEquals(result.size(), 3);
		assertEquals(result.get(0).text, "1");
		assertEquals(result.get(0).type, Tokenize.TokenType.NUMBER);
		assertEquals(result.get(1).text, "+");
		assertEquals(result.get(1).type, Tokenize.TokenType.OPERATOR);
		assertEquals(result.get(2).text, "2");
		assertEquals(result.get(2).type, Tokenize.TokenType.NUMBER);
	}

	@Test
	public void testEvalSpecialConstants() throws Exception {
		Settings settings = new Settings();
		Currency currency = new Currency(settings);
		EvalSpecial evalSpecial = new EvalSpecial(currency);
		
		// create history
		History history = new History();
		
		// create token list
		List<Token> tokens = toTokens("1+PI");
		
		// eval
		List<Token> result = evalSpecial.eval(tokens, history);

		assertEquals(result.size(), 3);
		assertEquals(result.get(0).text, "1");
		assertEquals(result.get(0).type, Tokenize.TokenType.NUMBER);
		assertEquals(result.get(1).text, "+");
		assertEquals(result.get(1).type, Tokenize.TokenType.OPERATOR);
		assertEquals(result.get(2).text, "3.14159265");
		assertEquals(result.get(2).type, Tokenize.TokenType.NUMBER);
	
	}
	
	@Test
	public void testEvalSpecialHistory() throws Exception {
		Settings settings = new Settings();
		Currency currency = new Currency(settings);
		EvalSpecial evalSpecial = new EvalSpecial(currency);
		
		// create history
		History history = new History();
		history.Add("1+1", new BigDecimal("2"));
		history.Add("A*3", new BigDecimal("6"));
		
		// create token list
		List<Token> tokens = toTokens("A+B");
		
		// eval
		List<Token> result = evalSpecial.eval(tokens, history);

		assertEquals(result.size(), 3);
		assertEquals(result.get(0).text, "2");
		assertEquals(result.get(0).type, Tokenize.TokenType.NUMBER);
		assertEquals(result.get(1).text, "+");
		assertEquals(result.get(1).type, Tokenize.TokenType.OPERATOR);
		assertEquals(result.get(2).text, "6");
		assertEquals(result.get(2).type, Tokenize.TokenType.NUMBER);
	}
	
	@Test(expected=KeyException.class)
	public void testEvalSpecialFail() throws Exception {
		Settings settings = new Settings();
		Currency currency = new Currency(settings);
		EvalSpecial evalSpecial = new EvalSpecial(currency);
		
		// create history
		History history = new History();
		history.Add("1+1", new BigDecimal("2"));
		history.Add("A*3", new BigDecimal("6"));
		
		// create token list
		List<Token> tokens = toTokens("A+C");
		
		// eval
		evalSpecial.eval(tokens, history);
	}
	
	@Test
	public void testCurrency() throws Exception {
		Settings settings = new Settings();
		Currency currency = new Currency(settings);
		EvalSpecial evalSpecial = new EvalSpecial(currency);

		// create history
		History history = new History();
		
		// create token list
		List<Token> tokens = toTokens("[m 1.0 GBP MYR]");
		
		// eval
		List<Token> result = evalSpecial.eval(tokens, history);

		assertEquals(result.size(), 1);
		assertEquals(result.get(0).text, "4.90");
		assertEquals(result.get(0).type, Tokenize.TokenType.NUMBER);
	}
	
	@Test
	public void testCalendar() throws Exception {
		Settings settings = new Settings();
		Currency currency = new Currency(settings);
		EvalSpecial evalSpecial = new EvalSpecial(currency);

		// create history
		History history = new History();
		
		// create token list
		List<Token> tokens = toTokens("[d weeks 2000/1/1 2000/1/15]");
		
		// eval
		List<Token> result = evalSpecial.eval(tokens, history);

		assertEquals(result.size(), 1);
		assertEquals(result.get(0).text, "2");
		assertEquals(result.get(0).type, Tokenize.TokenType.NUMBER);
	}
	
	@Test
	public void testCalendar2() throws Exception {
		Settings settings = new Settings();
		Currency currency = new Currency(settings);
		EvalSpecial evalSpecial = new EvalSpecial(currency);

		// create history
		History history = new History();
		
		// create token list
		List<Token> tokens = toTokens("[d days 2000/1/1 2000/1/15]");
		
		// eval
		List<Token> result = evalSpecial.eval(tokens, history);

		assertEquals(result.size(), 1);
		assertEquals(result.get(0).text, "14");
		assertEquals(result.get(0).type, Tokenize.TokenType.NUMBER);
	}
	
	public void helperCalendar(String input, String expectedAnswer) throws Exception {
		
		Settings settings = new Settings();
		Currency currency = new Currency(settings);
		EvalSpecial evalSpecial = new EvalSpecial(currency);

		// create history
		History history = new History();
		
		// create token list
		List<Token> tokens = toTokens(input);
		
		// eval
		List<Token> result = evalSpecial.eval(tokens, history);

		assertEquals(result.size(), 1);
		assertEquals(result.get(0).text, expectedAnswer);
		assertEquals(result.get(0).type, Tokenize.TokenType.NUMBER);
	}
	
	@Test
	public void testCalendarLots() throws Exception {
		helperCalendar("[d days 2000/1/1 2000/1/1]","0");
		helperCalendar("[d days 2000/1/1 2000/1/2]","1");
		helperCalendar("[d days 2000/1/1 2000/2/1]","31");
		helperCalendar("[d days 2001/1/1 2002/1/1]","365");
		
		helperCalendar("[d weeks 2001/1/1 2002/1/1]","52");
		helperCalendar("[d weeks 2000/1/1 2000/1/1]","0");
		helperCalendar("[d weeks 2000/1/1 2000/1/7]","0");
		helperCalendar("[d weeks 2000/1/1 2000/1/8]","1");
		helperCalendar("[d weeks 2000/1/1 2000/2/1]","4");
		
		helperCalendar("[d months 2001/1/1 2002/1/1]","12");
		helperCalendar("[d months 2000/1/1 2000/1/1]","0");
		helperCalendar("[d months 2000/1/1 2000/1/31]","0");
		helperCalendar("[d months 2000/1/1 2000/2/1]","1");
		helperCalendar("[d months 2000/1/1 2010/1/1]","120");
		
		helperCalendar("[d years 2000/1/1 2000/1/1]","0");
		helperCalendar("[d years 2000/1/1 2000/12/31]","0");
		helperCalendar("[d years 2000/1/1 2001/1/1]","1");
		helperCalendar("[d years 2000/1/1 2010/1/1]","10");
	}
}
