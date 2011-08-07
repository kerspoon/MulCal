package mulCal.equationParser;

import static junit.framework.Assert.assertEquals;
import static mulCal.equationParser.Tokenize.toTokens;
import java.math.BigDecimal;
import java.util.List;

import mulCal.equationParser.Tokenize.Token;
import mulCal.history.History;
import mulCal.settings.Settings;
import mulCal.util.KeyException;

import org.junit.Test;

public class EvalSpecialTest {

	@Test
	public void testEvalSpecial() throws Exception  {
		Settings settings = new Settings();
		EvalSpecial evalSpecial = new EvalSpecial(settings);
		
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
		EvalSpecial evalSpecial = new EvalSpecial(settings);
		
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
		EvalSpecial evalSpecial = new EvalSpecial(settings);
		
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
		EvalSpecial evalSpecial = new EvalSpecial(settings);
		
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
		EvalSpecial evalSpecial = new EvalSpecial(settings);

		// create history
		History history = new History();
		
		// create token list
		List<Token> tokens = toTokens("[1.0 GBP MYR]");
		
		// eval
		List<Token> result = evalSpecial.eval(tokens, history);

		assertEquals(result.size(), 1);
		assertEquals(result.get(0).text, "4.90");
		assertEquals(result.get(0).type, Tokenize.TokenType.NUMBER);
	}
}
