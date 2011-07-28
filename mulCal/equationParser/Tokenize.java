package mulCal.equationParser;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *  '-' is a bitch; 
 *  	it can be part of a number "-4", 
 *  	an operator "10 - 3", 
 *  	or a unary function "-(5+2)".
 */

public class Tokenize {
	public static enum TokenType { NUMBER, FUNCTION, OPERATOR, VARIABLE, SPECIAL, OPENBRACKET, CLOSEBRACKET }
	private static String asciiUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static class Token {
		public TokenType type;
		public String text;
		public Token(TokenType type, String text){
			this.type = type;
			this.text = text;
		}
		
	}
	
	public static String asString(List<Token> tokens) {
		String result = "";
		for (Token token : tokens) {
			result += token.text + " ";
		}
		return result;
	}
	
	public static List<Token> toTokens(String input) {
		List<Token> tokens = new LinkedList<Token>();
		input = input.trim();
		while (input.length() != 0) {
			Token token;
			if (input.charAt(0) == '[') {
				token = new Token(TokenType.SPECIAL, readSpecial(input));
			} else if ('(' == input.charAt(0)) {
				token = new Token(TokenType.OPENBRACKET, "(");
			} else if (')' == input.charAt(0)) {
				token = new Token(TokenType.CLOSEBRACKET, ")");
			} else if (startWithNumber(input)) {
				token = new Token(TokenType.NUMBER, readNumber(input));
			} else if (asciiUpperCase.contains(Character.toString(input.charAt(0)))) {
				token = new Token(TokenType.VARIABLE, readVariable(input));
			} else {
				if (tokens.isEmpty() || nextIsFunction(tokens.get(tokens.size() - 1))) {
					token = new Token(TokenType.FUNCTION, readFunction(input));
				} else {
					token = new Token(TokenType.OPERATOR, readFunction(input));
				}
			}
			tokens.add(token);
			input = input.substring(token.text.length());
			input = input.trim();
		}
		return tokens;
	}
			
	public static boolean nextIsFunction(Token lastToken) {
		if (lastToken.type == TokenType.OPENBRACKET || 
				lastToken.type == TokenType.OPERATOR ||
				lastToken.type == TokenType.FUNCTION) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean startWithNumber(String input) {
		// TODO add scientific notation
	    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	    Matcher matcher = pattern.matcher(input);
		return matcher.lookingAt();
	}

	public static String readFunction(String input) {
	    Pattern pattern = Pattern.compile("[a-z]+|[!%*/+]|\\^|\\-");
	    Matcher matcher = pattern.matcher(input);
	    matcher.lookingAt();
		return matcher.group();
	}

	public static String readVariable(String input) {
	    Pattern pattern = Pattern.compile("[A-Z]*");
	    Matcher matcher = pattern.matcher(input);
	    matcher.lookingAt();
		return matcher.group();
	}

	public static String readNumber(String input) {
		// TODO add scientific notation
	    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	    Matcher matcher = pattern.matcher(input);
	    matcher.lookingAt();
		return matcher.group();
	}

	public static String readSpecial(String input) {
	    Pattern pattern = Pattern.compile("\\[[^\\]]*\\]");
	    Matcher matcher = pattern.matcher(input);
	    matcher.lookingAt();
		return matcher.group();
	}
}
