package mulCal.main;

import static mulCal.equationParser.PostfixEval.eval;
import static mulCal.equationParser.Tokenize.toTokens;
import static mulCal.equationParser.InfixToRPN.toRPN;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import mulCal.currency.Currency;
import mulCal.equationParser.EvalSpecial;
import mulCal.equationParser.Tokenize.Token;
import mulCal.history.History;
import mulCal.history.History.HistoryItem;
import mulCal.settings.Settings;
import mulCal.util.KeyException;

public class Main {

	public History history;
	private EvalSpecial evalSpecial;
	private String lastId;
	private Settings settings;
	private Currency currency;
	
	public Main() {
		history = new History();
		settings = new Settings();
		currency = new Currency(settings);
		evalSpecial = new EvalSpecial(currency);
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
		history.reset();
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
		List<Token> basicResult = evalSpecial.eval(toTokens(eqn), history);
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
			return false;
		}
	}
	
	public boolean update(String val) {
		try {
			currency.update();
			return true;
		} catch (Exception e) {
			return false;
		}
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

	public Collection<HistoryItem> getHistory() {
		return history.getAll();
	}

	public HistoryItem getHistoryItem(String val) throws KeyException {
		return history.get(val);
	}
}
