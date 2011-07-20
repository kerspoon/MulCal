package mulCal.equationParser;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static mulCal.equationParser.Tokenize.readFunction;
import static mulCal.equationParser.Tokenize.readNumber;
import static mulCal.equationParser.Tokenize.readSpecial;
import static mulCal.equationParser.Tokenize.startWithNumber;
import static mulCal.equationParser.Tokenize.toTokens;

import java.util.List;

import mulCal.equationParser.Tokenize.Token;

import org.junit.Test;

public class TokenizeTest {
	
	@Test
	public void testIsNumber() throws Exception {
		assertTrue(startWithNumber("0"));
		assertTrue(startWithNumber("1"));
		assertTrue(startWithNumber("10"));
		assertTrue(startWithNumber("01234567890"));
		assertFalse(startWithNumber("+0"));
		assertFalse(startWithNumber("+1"));
		assertFalse(startWithNumber("+10"));
		assertFalse(startWithNumber("+0001"));
		assertFalse(startWithNumber("+01234567890"));
		assertTrue(startWithNumber("-0"));
		assertTrue(startWithNumber("-1"));
		assertTrue(startWithNumber("-10"));
		assertTrue(startWithNumber("-0001"));
		assertTrue(startWithNumber("-01234567890"));
		assertFalse(startWithNumber(""));
		assertFalse(startWithNumber("--1"));
		assertFalse(startWithNumber("_5"));
		assertFalse(startWithNumber("asdf"));
		assertFalse(startWithNumber("a5"));
		assertFalse(startWithNumber("a5a"));
		assertFalse(startWithNumber("+a1"));
		assertFalse(startWithNumber("-a3"));
		assertTrue(startWithNumber("5a"));
		assertTrue(startWithNumber("5a5"));
		assertFalse(startWithNumber("+9a"));
		assertTrue(startWithNumber("-9a"));
		assertTrue(startWithNumber("-10+"));
		assertTrue(startWithNumber("-10.1200"));
		assertFalse(startWithNumber("+945.00750"));
		assertTrue(startWithNumber("945.00750"));
		assertTrue(startWithNumber("0.0"));
		assertFalse(startWithNumber(".00750"));
		assertFalse(startWithNumber("+.00750"));
		assertFalse(startWithNumber("-.7878"));
		assertFalse(startWithNumber("-"));
		assertFalse(startWithNumber("+"));
		assertFalse(startWithNumber("."));
		assertTrue(startWithNumber("0.")); // the '.' wont be in the match
	}
	
	@Test
	public void testReadNumber() throws Exception {
		assertEquals(readNumber("0"), "0");
		assertEquals(readNumber("1"), "1");
		assertEquals(readNumber("10"), "10");
		assertEquals(readNumber("01234567890"), "01234567890");
		assertEquals(readNumber("-0"), "-0");
		assertEquals(readNumber("-1"), "-1");
		assertEquals(readNumber("-10"), "-10");
		assertEquals(readNumber("-0001"), "-0001");
		assertEquals(readNumber("-01234567890"), "-01234567890");
		assertEquals(readNumber("5a"), "5");
		assertEquals(readNumber("5a5"), "5");
		assertEquals(readNumber("-9a"), "-9");
		assertEquals(readNumber("-10+"), "-10");
		assertEquals(readNumber("-10.1200"), "-10.1200");
		assertEquals(readNumber("945.00750"), "945.00750");
		assertEquals(readNumber("0.0"), "0.0");
		assertEquals(readNumber("0."), "0");
	}
	
	@Test(expected=IllegalStateException.class)
	public void testReadNumberFail() throws Exception {
		readNumber("-.7878");
	}
	@Test(expected=IllegalStateException.class)
	public void testReadNumberFail2() throws Exception {
		readNumber("+7878");
	}
	
	@Test
	public void testReadFunction() throws Exception {
		assertEquals(readFunction("a"), "a");
		assertEquals(readFunction("bc"), "bc");
		assertEquals(readFunction("asbc"), "asbc");
		assertEquals(readFunction("+"), "+");
		assertEquals(readFunction("-"), "-");
		assertEquals(readFunction("/"), "/");
		assertEquals(readFunction("*"), "*");
		assertEquals(readFunction("!"), "!");
		assertEquals(readFunction("^"), "^");
		assertEquals(readFunction("%"), "%");
		assertEquals(readFunction("+-/*!^%"), "+");
		assertEquals(readFunction("b-c"), "b");
		assertEquals(readFunction("-c"), "-");
	}
	
	@Test(expected=IllegalStateException.class)
	public void testReadFunctionFail() throws Exception {
		readFunction("7+");
	}
	
	@Test(expected=IllegalStateException.class)
	public void testReadFunctionFail2() throws Exception {
		readFunction("E*");
	}
	
	@Test
	public void testReadSpecial() throws Exception {
		assertEquals(readSpecial("[123 456]789"), "[123 456]");
		assertEquals(readSpecial("[asd%!£$1325]!%"), "[asd%!£$1325]");
		assertEquals(readSpecial("[one]two]"), "[one]");
		assertEquals(readSpecial("[one][two]"), "[one]");
		assertEquals(readSpecial("[o[n[e][two]"), "[o[n[e]");
	}
	
	@Test(expected=IllegalStateException.class)
	public void testReadSpecialFail() throws Exception {
		readSpecial("[");
	}
	
	@Test(expected=IllegalStateException.class)
	public void testReadSpecialFail2() throws Exception {
		readSpecial("]");
	}
	
	@Test(expected=IllegalStateException.class)
	public void testReadSpecialFail3() throws Exception {
		readSpecial("-[one]");
	}
	
	@Test
	public void testToTokens() throws Exception {
		List<Token> tokens = toTokens("");
		assertTrue(tokens.isEmpty());
	}
	
	@Test
	public void testToTokens2() throws Exception {
		List<Token> tokens = toTokens("1");
		assertEquals(tokens.size(), 1);
		assertEquals(tokens.get(0).text, "1");
		assertEquals(tokens.get(0).type, Tokenize.TokenType.NUMBER);
	}
	
	@Test
	public void testToTokens3() throws Exception {
		List<Token> tokens = toTokens("1+2");
		assertEquals(tokens.size(), 3);
		assertEquals(tokens.get(0).text, "1");
		assertEquals(tokens.get(0).type, Tokenize.TokenType.NUMBER);
		assertEquals(tokens.get(1).text, "+");
		assertEquals(tokens.get(1).type, Tokenize.TokenType.OPERATOR);
		assertEquals(tokens.get(2).text, "2");
		assertEquals(tokens.get(2).type, Tokenize.TokenType.NUMBER);
	}
	
	@Test
	public void testToTokens4() throws Exception {
		List<Token> tokens = toTokens(")(");
		assertEquals(tokens.size(), 2);
		assertEquals(tokens.get(0).text, ")");
		assertEquals(tokens.get(0).type, Tokenize.TokenType.CLOSEBRACKET);
		assertEquals(tokens.get(1).text, "(");
		assertEquals(tokens.get(1).type, Tokenize.TokenType.OPENBRACKET);
	}
	
	@Test
	public void testToTokens5() throws Exception {
		List<Token> tokens = toTokens("( 8  *     [+(12) / 96 - 4 hello)] (");
		assertEquals(tokens.size(), 5);
		assertEquals(tokens.get(0).text, "(");
		assertEquals(tokens.get(0).type, Tokenize.TokenType.OPENBRACKET);
		assertEquals(tokens.get(1).text, "8");
		assertEquals(tokens.get(1).type, Tokenize.TokenType.NUMBER);
		assertEquals(tokens.get(2).text, "*");
		assertEquals(tokens.get(2).type, Tokenize.TokenType.OPERATOR);
		assertEquals(tokens.get(3).text, "[+(12) / 96 - 4 hello)]");
		assertEquals(tokens.get(3).type, Tokenize.TokenType.SPECIAL);
		assertEquals(tokens.get(4).text, "(");
		assertEquals(tokens.get(4).type, Tokenize.TokenType.OPENBRACKET);
	}
	
	@Test
	public void testToTokens6() throws Exception {
		List<Token> tokens = toTokens("1+B");
		assertEquals(tokens.size(), 3);
		assertEquals(tokens.get(0).text, "1");
		assertEquals(tokens.get(0).type, Tokenize.TokenType.NUMBER);
		assertEquals(tokens.get(1).text, "+");
		assertEquals(tokens.get(1).type, Tokenize.TokenType.OPERATOR);
		assertEquals(tokens.get(2).text, "B");
		assertEquals(tokens.get(2).type, Tokenize.TokenType.VARIABLE);
	}
	
	@Test
	public void testToTokens7() throws Exception {
		List<Token> tokens = toTokens("4 / sin cos X");
		assertEquals(tokens.size(), 5);
		assertEquals(tokens.get(0).text, "4");
		assertEquals(tokens.get(0).type, Tokenize.TokenType.NUMBER);
		assertEquals(tokens.get(1).text, "/");
		assertEquals(tokens.get(1).type, Tokenize.TokenType.OPERATOR);
		assertEquals(tokens.get(2).text, "sin");
		assertEquals(tokens.get(2).type, Tokenize.TokenType.FUNCTION);
		assertEquals(tokens.get(3).text, "cos");
		assertEquals(tokens.get(3).type, Tokenize.TokenType.FUNCTION);
		assertEquals(tokens.get(4).text, "X");
		assertEquals(tokens.get(4).type, Tokenize.TokenType.VARIABLE);
	}
	
	public boolean containsFunction(List<Token> tokens) {
		for (Token token : tokens) {
			if (token.type == Tokenize.TokenType.FUNCTION) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void testNextIsFunction() throws Exception {
	assertTrue(containsFunction(toTokens("1 + sin x")));
	assertTrue(containsFunction(toTokens("1 + sin (x + y)")));
	assertTrue(containsFunction(toTokens("sin x")));
	assertTrue(containsFunction(toTokens("- 4")));
	assertTrue(containsFunction(toTokens("-(5+2)")));
	assertTrue(containsFunction(toTokens("sin sin x")));
	assertTrue(containsFunction(toTokens("1 + sin sin x")));
	assertTrue(containsFunction(toTokens("1 + (sin x)")));

	assertFalse(containsFunction(toTokens("-4")));
	assertFalse(containsFunction(toTokens("1+1")));
	assertFalse(containsFunction(toTokens("A+B")));
	assertFalse(containsFunction(toTokens("A + B * C")));
	assertFalse(containsFunction(toTokens("(1) + (2)")));

	assertTrue(containsFunction(toTokens("(1 - - -4)"))); // a binary operators, unary functions, and negative number
	assertTrue(containsFunction(toTokens("(1 +--- -4)"))); // a mess (with proper syntax)
		
	}
}
