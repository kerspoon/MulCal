package mulCal.main;

import static mulCal.equationParser.PostfixEval.eval;
import static mulCal.equationParser.Tokenize.toTokens;
import static mulCal.equationParser.EvalSpecial.evalSpecial;
import static mulCal.equationParser.InfixToRPN.toRPN;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import mulCal.equationParser.EvalSpecial;
import mulCal.equationParser.Tokenize.Token;
import mulCal.history.History;
import mulCal.util.KeyException;

public class Main {

	private History history;
	private String lastId;
	
	public Main() {
		history = new History();
	}
	
	public boolean save(String val) {
		try {
			history.Save(val);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public void reset(String val) {
		history.Clear();
	}
	
	public boolean load(String val) {
		try {
			history.Load(val);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public BigDecimal equation(String eqn) throws Exception {
		List<Token> basicResult = evalSpecial(toTokens(eqn), history, EvalSpecial.constants);
		BigDecimal result = eval(toRPN(basicResult));
		this.lastId = history.Add(eqn, result);
		return result;
	}
	
	public String getLastID(){
		return this.lastId;
	}
	
	public boolean comment(String id, String comment) {
		try {
			history.SetComment(id, comment);
			return true;
		} catch (KeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void update(String val) {
		// TODO not implemented
		throw new RuntimeException();
	}
	
	public void from(String val) {
		// TODO not implemented
		throw new RuntimeException();
	}
	
	public void till(String val) {
		// TODO not implemented
		throw new RuntimeException();
	}
	
	public void days(String val) {
		// TODO not implemented
		throw new RuntimeException();
	}
	
	public void weeks(String val) {
		// TODO not implemented
		throw new RuntimeException();
	}
	
	public void months(String val) {
		// TODO not implemented
		throw new RuntimeException();
	}
	
	public void years(String val) {
		// TODO not implemented
		throw new RuntimeException();
	}
}
