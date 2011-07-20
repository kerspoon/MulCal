package mulCal.equationParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import mulCal.equationParser.Tokenize.Token;

public class InfixToRPN {

    // Associativity constants for operators
    private static final int LEFT_ASSOC = 0;
    private static final int RIGHT_ASSOC = 1;
    
    // Supported operators
    private static final Map<String, int[]> Operators = new HashMap<String, int[]>();
    static {
        // Map<"token", []{precendence, associativity}>
        Operators.put("+", new int[] { 0, LEFT_ASSOC });
        Operators.put("-", new int[] { 0, LEFT_ASSOC });
        Operators.put("*", new int[] { 5, LEFT_ASSOC });
        Operators.put("/", new int[] { 5, LEFT_ASSOC });
        Operators.put("%", new int[] { 5, LEFT_ASSOC });
        Operators.put("^", new int[] { 10, RIGHT_ASSOC });
    }
    
	public static List<Token> toRPN(List<Token> tokens) throws Exception {
		
		// http://en.wikipedia.org/wiki/Shunting-yard_algorithm
		
		List<Token> out = new ArrayList<Token>();
        Stack<Token> stack = new Stack<Token>();
        
		for (Token token : tokens) {
			if (token.type == Tokenize.TokenType.NUMBER) {
				// If the token is a number, then add it to the output queue.
				out.add(token);
			} else if (token.type == Tokenize.TokenType.FUNCTION) {
				// If the token is a function token, then push it onto the stack.
				stack.push(token);
			} else if (token.type == Tokenize.TokenType.OPERATOR) {
				// If the token is an operator, o1, then:
				// 	while there is an operator token, o2, at the top of the stack, and
				// 			either o1 is left-associative and its precedence is less than or equal to that of o2,
				// 			or o1 is right-associative and its precedence is less than that of o2,
				// 		pop o2 off the stack, onto the output queue;
				// 	push o1 onto the stack.
				while (!stack.empty() && stack.peek().type == Tokenize.TokenType.OPERATOR && 
						((leftAssociative(token) && precidence(token) <= precidence(stack.peek())) || 
								(!leftAssociative(token) && precidence(token) < precidence(stack.peek())))) { 
					out.add(stack.pop());
				}
				stack.push(token);
			} else if (token.type == Tokenize.TokenType.OPENBRACKET) {
				// If the token is a left parenthesis, then push it onto the stack.
				stack.push(token);
			} else if (token.type == Tokenize.TokenType.CLOSEBRACKET) {
				// If the token is a right parenthesis:
				//     Until the token at the top of the stack is a left parenthesis, pop operators off the stack onto the output queue.
				//     Pop the left parenthesis from the stack, but not onto the output queue.
				//     If the token at the top of the stack is a function token, pop it onto the output queue.
				//     If the stack runs out without finding a left parenthesis, then there are mismatched parentheses.

				while (!stack.empty() && stack.peek().type != Tokenize.TokenType.OPENBRACKET) {
					out.add(stack.pop());
				}
				if (!stack.empty() && stack.peek().type == Tokenize.TokenType.OPENBRACKET) {
					stack.pop();
				}
				else {
					throw new Exception("mismatched parentheses");
				}
				if (!stack.empty() && stack.peek().type == Tokenize.TokenType.FUNCTION) {
					out.add(stack.pop());
				}
			} else {
				throw new Exception();
			}
		}

		// When there are no more tokens to read:
		// 	While there are still operator tokens in the stack:
		// 		If the operator token on the top of the stack is a parenthesis, 
		// 			then there are mismatched parentheses.
		// 		Pop the operator onto the output queue.
        while (!stack.empty()) {
        	if (stack.peek().type == Tokenize.TokenType.OPENBRACKET || 
        			stack.peek().type == Tokenize.TokenType.CLOSEBRACKET )
        	{
        		throw new Exception("mismatched parentheses");
        	}
            out.add(stack.pop());
        }
        
		return out;
	}
	
    private static int precidence(Token token) {
        if (!isOperator(token)) {
            throw new IllegalArgumentException("Invalid token: " + token.text);
        }
		return Operators.get(token.text)[0];
	}

	private static boolean leftAssociative(Token token) {
        if (!isOperator(token)) {
            throw new IllegalArgumentException("Invalid token: " + token.text);
        }
        if (Operators.get(token.text)[1] == LEFT_ASSOC) {
            return true;
        }
        return false;
    }

	private static boolean isOperator(Token token) {
		return Operators.containsKey(token.text);
	}

}
