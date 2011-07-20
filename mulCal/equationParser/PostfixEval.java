package mulCal.equationParser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Stack;

import mulCal.equationParser.Tokenize.Token;
import mulCal.equationParser.Tokenize.TokenType;

//http://andreinc.net/2010/10/05/converting-infix-to-rpn-shunting-yard-algorithm/
//http://stackoverflow.com/questions/114586/smart-design-of-a-math-parser
//http://stackoverflow.com/questions/28256/equation-expression-parser-with-precedence
//http://www.softwaremonkey.org/Code/MathEval
//http://scriptasylum.com/tutorials/infix_postfix/algorithms/postfix-evaluation/index.htm
//http://www.javapractices.com/topic/TopicAction.do?Id=87

public class PostfixEval {

	public static BigDecimal eval(List<Token> tokens) throws Exception {
		Stack<BigDecimal> stack = new Stack<BigDecimal>();
		for (Token token : tokens) {
			if (token.type == TokenType.NUMBER) {
				stack.push(new BigDecimal(token.text));
			} else if (token.type == TokenType.FUNCTION ) {
				// pop stack as value
				// eval token.text ( value ) as result
				// push result to stack
				BigDecimal value = stack.pop();
				BigDecimal result = EvaluateFunction(token.text, value);
				stack.push(result);
			} else if (token.type == TokenType.OPERATOR) {
				BigDecimal value1 = stack.pop();
				BigDecimal value2 = stack.pop();
				BigDecimal result = EvaluateOperator(token.text, value2, value1);
				stack.push(result);
			} else {
				throw new Exception();
			}
		}
		if (stack.size() != 1) {
			throw new Exception("");
		}
		return stack.pop();
	}

	public static BigDecimal EvaluateOperator(String text, BigDecimal value1,
			BigDecimal value2) throws Exception {
		if (text.equals("+")) {
			return value1.add(value2);
		} else if (text.equals("-")) {
			return value1.subtract(value2);
		} else if (text.equals("/")) {
			return value1.divide(value2);
		} else if (text.equals("*")) {
			return value1.multiply(value2);
		} else if (text.equals("%")) {
			return value1.remainder(value2);
		} else if (text.equals("^")) {
			return new BigDecimal(Math.pow(value1.doubleValue(),value2.doubleValue()));
		} else {
			throw new Exception();
		}
	}

	public static BigDecimal EvaluateFunction(String text, BigDecimal value) throws Exception {
		if (text.equals("nul")) {
			return value;
		} else if (text.equals("sin")) {
			return new BigDecimal(Math.sin(value.doubleValue()));
		} else if (text.equals("cos")) {
			return new BigDecimal(Math.cos(value.doubleValue()));
		} else if (text.equals("tan")) {
			return new BigDecimal(Math.tan(value.doubleValue()));
		} else if (text.equals("exp")) {
			return new BigDecimal(Math.exp(value.doubleValue()));
		} else if (text.equals("log")) {
			return new BigDecimal(Math.log(value.doubleValue()));
		} else if (text.equals("sqrt")) {
			return new BigDecimal(Math.sqrt(value.doubleValue()));
		} else if (text.equals("signum")) {
			return new BigDecimal(value.signum());
		} else if (text.equals("abs")) {
			return value.abs();
		} else if (text.equals("-")) {
			return value.negate();
		} else {
			throw new Exception();
		}
	}
}
