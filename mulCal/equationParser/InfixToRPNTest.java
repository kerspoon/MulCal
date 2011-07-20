package mulCal.equationParser;

import static junit.framework.Assert.assertEquals;
import static mulCal.equationParser.InfixToRPN.toRPN;
import static mulCal.equationParser.Tokenize.asString;
import static mulCal.equationParser.Tokenize.toTokens;

import java.util.List;

import mulCal.equationParser.Tokenize.Token;

import org.junit.Test;


public class InfixToRPNTest {

	@Test
	public void testBasic() throws Exception {
		List<Token> tokens = toRPN(toTokens("1 + sin 2"));
		assertEquals(tokens.size(), 4);
		assertEquals(tokens.get(0).text, "1");
		assertEquals(tokens.get(0).type, Tokenize.TokenType.NUMBER);
		assertEquals(tokens.get(1).text, "2");
		assertEquals(tokens.get(1).type, Tokenize.TokenType.NUMBER);
		assertEquals(tokens.get(2).text, "sin");
		assertEquals(tokens.get(2).type, Tokenize.TokenType.FUNCTION);
		assertEquals(tokens.get(3).text, "+");
		assertEquals(tokens.get(3).type, Tokenize.TokenType.OPERATOR);
		assertEquals(asString(tokens), "1 2 sin + ");
	}

	@Test
	public void testLots() throws Exception {
		assertEquals(asString(toRPN(toTokens("1 + 2"))), "1 2 + ");
		assertEquals(asString(toRPN(toTokens("2 * 3"))), "2 3 * ");
		assertEquals(asString(toRPN(toTokens("(1)"))), "1 ");
		assertEquals(asString(toRPN(toTokens("1 + - 2"))), "1 2 - + ");
		assertEquals(asString(toRPN(toTokens("1 +-- 2"))), "1 2 - - + ");
		assertEquals(asString(toRPN(toTokens("1 + sin sin 2"))), "1 2 sin sin + ");
		assertEquals(asString(toRPN(toTokens("1 + (sin 5)"))), "1 5 sin + ");
		assertEquals(asString(toRPN(toTokens("1 + 2 * 3"))), "1 2 3 * + ");
		assertEquals(asString(toRPN(toTokens("1 * 2 + 3"))), "1 2 * 3 + ");
		assertEquals(asString(toRPN(toTokens("(1 + 2) * 3"))), "1 2 + 3 * ");
		assertEquals(asString(toRPN(toTokens("(1) + (2)"))), "1 2 + ");
		// assertEquals(asString(toRPN(toTokens(""))), "");
	}
}
