package mulCal.equationParser;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mulCal.equationParser.Tokenize.Token;
import mulCal.equationParser.Tokenize.TokenType;
import mulCal.history.History;

public class EvalSpecial {
	
	public static final Map<String, BigDecimal> constants;
    static {
        Map<String, BigDecimal> tmpMap = new HashMap<String, BigDecimal>();
        tmpMap.put("PI", new BigDecimal("3.14159265"));
        tmpMap.put("E", new BigDecimal("2.71828183"));
        constants = Collections.unmodifiableMap(tmpMap);
    }
    
	public static List<Token> evalSpecial(List<Token> tokens, History history, Map<String, BigDecimal> constants) throws Exception {
		List<Token> result = new LinkedList<Token>();
		for (Token tok : tokens) {
			if (tok.type == TokenType.VARIABLE) {
				if (constants.containsKey(tok.text)) {
					result.add(new Token(TokenType.NUMBER, constants.get(tok.text).toString()));
				} else {
					result.add(new Token(TokenType.NUMBER, history.Get(tok.text).result.toString()));
				}
			} else if (tok.type == TokenType.SPECIAL) {
				result.add(evalSpecialForm(tok, tokens, history, constants));
			} else {
				result.add(tok);
			}
		}
		return result;
	}

	private static Token evalSpecialForm(Token tok, List<Token> tokens,
			History history, Map<String, BigDecimal> constants2) throws Exception {
		// TODO: implement when we have specials (calendar or currency)
		throw new Exception(); // shouldn't really do this
	}
}
