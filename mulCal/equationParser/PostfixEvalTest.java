package mulCal.equationParser;

import static junit.framework.Assert.assertEquals;
import static mulCal.equationParser.InfixToRPN.toRPN;
import static mulCal.equationParser.PostfixEval.eval;
import static mulCal.equationParser.Tokenize.toTokens;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;


public class PostfixEvalTest {
	@Test
	public void testEvalOperators() throws Exception {
		assertEquals(new BigDecimal("3"), eval(toRPN(toTokens("1 + 2"))));
		assertEquals(new BigDecimal("7"), eval(toRPN(toTokens("1 + 2 * 3"))));
		assertEquals(new BigDecimal("9"), eval(toRPN(toTokens("(1 + 2) * 3"))));
		assertEquals(new BigDecimal("23"), eval(toRPN(toTokens("10 * 2 + 3"))));
		assertEquals(new BigDecimal("23"), eval(toRPN(toTokens("(10 * 2) + 3"))));
		assertEquals(new BigDecimal("50"), eval(toRPN(toTokens("10 * (2 + 3)"))));
		assertEquals(new BigDecimal("1"), eval(toRPN(toTokens("2 - 1"))));
		assertEquals(new BigDecimal("0.5"), eval(toRPN(toTokens("5 / 10"))));
		assertEquals(new BigDecimal("1"), eval(toRPN(toTokens("10 % 3"))));
	}
	
	@Test
	public void testEvalFunctions() throws Exception {
		assertEquals(new BigDecimal("3"), eval(toRPN(toTokens("1 + nul 2"))));
		assertEquals(new BigDecimal("-1"), eval(toRPN(toTokens("-abs-1"))));
		assertTrue(eval(toRPN(toTokens("sin 3.14159265"))).doubleValue() < 0.0001);
		assertTrue(eval(toRPN(toTokens("sin(3.14159265)"))).doubleValue() < 0.0001);
	}
}
