package mulCal.equationParser;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mulCal.currency.Currency;
import mulCal.equationParser.Tokenize.Token;
import mulCal.equationParser.Tokenize.TokenType;
import mulCal.history.History;
import mulCal.settings.Settings;
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
	
	public EvalSpecial(Settings settings) {
		currency = new Currency(settings);
	}
    
	public List<Token> eval(List<Token> tokens, History history) throws KeyException {
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
	

	private Token evalSpecialForm(Token tok, List<Token> tokens, History history) throws KeyException {
		// TODO: implement when we have specials (calendar or currency)
		
		// it comes in two forms:
		//   [15 USD YEN] 
		//   [A USD YEN] 
		// parse the first as a number or variable then evalVariable if needed
		// make sure both currencies are in the list.
		// call convert
		// return the result as a token
		
		String[] parts = removeQuotes(tok.text).split(" +");
		
		if (parts.length != 3) {
			throw new RuntimeException("invalid currency conversion");
		}
		
		BigDecimal value;
		if(startWithNumber(parts[0])) {
			value = new BigDecimal(parts[0]);
		} else {
			value = new BigDecimal(evalVariable(
					new Token(TokenType.NUMBER, parts[0]), 
					history).text);
		}
		
		String from = parts[1];
		String to = parts[2];
		
		BigDecimal result = currency.convert(value, from, to);
		return new Token(TokenType.NUMBER, result.toPlainString());
	}
}
