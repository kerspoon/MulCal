package mulCal.equationParser;


import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mulCal.calendar.Calendar;
import mulCal.currency.Currency;
import mulCal.equationParser.Tokenize.Token;
import mulCal.equationParser.Tokenize.TokenType;
import mulCal.history.History;
import mulCal.util.KeyException;

import static mulCal.equationParser.Tokenize.startWithNumber;
import static mulCal.main.util.removeQuotes;

public class EvalSpecial {
	
	public static final Map<String, BigDecimal> constants;
    static {
        Map<String, BigDecimal> tmpMap = new HashMap<String, BigDecimal>();
        tmpMap.put("PI", new BigDecimal("3.14159265"));
        tmpMap.put("E", new BigDecimal("2.71828183"));
        constants = Collections.unmodifiableMap(tmpMap);
    }

	private Currency currency;
	
	public EvalSpecial(Currency currency) {
		this.currency = currency;
	}
    
	public List<Token> eval(List<Token> tokens, History history) throws KeyException, ParseException {
		List<Token> result = new LinkedList<Token>();
		for (Token tok : tokens) {
			if (tok.type == TokenType.VARIABLE) {
				result.add(evalVariable(tok, history));
			} else if (tok.type == TokenType.SPECIAL) {
				result.add(evalSpecialForm(tok, tokens, history));
			} else {
				result.add(tok);
			}
		}
		return result;
	}

	private Token evalVariable(Token tok, History history) throws KeyException {
		if (constants.containsKey(tok.text)) {
			return new Token(TokenType.NUMBER, constants.get(tok.text).toString());
		} else {
			return new Token(TokenType.NUMBER, history.get(tok.text).result.toString());
		}
	}
	

	private Token evalSpecialForm(Token tok, List<Token> tokens, History history) throws KeyException, ParseException {
		
		// it comes in these forms:
		//   [m 15 USD YEN] 
		//   [m A USD YEN] 
		//   [d weeks 2010/2/4 2010/2/11] 
		// parse the first as a number or variable then evalVariable if needed
		// make sure both currencies are in the list.
		// call convert
		// return the result as a token
		
		String[] parts = removeQuotes(tok.text).split(" +");
		
		if (parts[0].equals("m")) {
			if (parts.length != 4) {
				throw new RuntimeException("invalid currency conversion");
			}
			
			BigDecimal value;
			if(startWithNumber(parts[1])) {
				value = new BigDecimal(parts[1]);
			} else {
				value = new BigDecimal(evalVariable(
						new Token(TokenType.NUMBER, parts[1]), 
						history).text);
			}
			
			String from = parts[2];
			String to = parts[3];
			
			BigDecimal result = currency.convert(value, from, to);
			return new Token(TokenType.NUMBER, result.toPlainString());
		} else if (parts[0].equals("d")) {
			//   [d weeks 2010/2/4 2010/11/29] 
			
			String range = parts[1].toLowerCase();
			String from = parts[2];
			String to = parts[3];

			Calendar cal = new Calendar();
			
			cal.setFromDateString(from);
			cal.setToDateString(to);
			
			long result;
			if (range.equals("days")) {
				result = cal.getDateDiff(java.util.Calendar.DAY_OF_MONTH);
			} else if (range.equals("weeks")) {
				result = cal.getDateDiff(java.util.Calendar.WEEK_OF_YEAR);
			} else if (range.equals("months")) {
				result = cal.getDateDiff(java.util.Calendar.MONTH);
			} else if (range.equals("years")) {
				result = cal.getDateDiff(java.util.Calendar.YEAR);
			} else {
				throw new RuntimeException("invalid calendar range: ".concat(range));
			}
			return new Token(TokenType.NUMBER, String.format("%d", result));
		} else {
			throw new RuntimeException("invalid special form: ".concat(parts[0]));
		}
	}
}
