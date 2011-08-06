/**
 * 
 */
package mulCal.currency;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Hashtable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import mulCal.settings.Settings;
import mulCal.util.KeyException;

/**
 * @author james
 *
 */
public class Currency {

	private Settings settings;
	private Hashtable<String, BigDecimal> entry;
	private String[] currencies = {"GBP", "USD", "AUD", "EUR", "THB", "MYR"};
        
	public Currency(Settings settings) throws KeyException {
		this.settings = settings;
		this.entry = new Hashtable<String, BigDecimal>();
		for (String currency : this.currencies) {
			this.entry.put(currency, new BigDecimal(this.settings.Get(currency)));
		}
	}
	
	public BigDecimal convert(BigDecimal value, String from, String to) {
        // it would be quicker to calc a lookup table for [to]/[from]
        // but lets see if it's slow first
		return value.multiply(get(from).divide(get(to)));
	}
	
	public BigDecimal get(String id) {
		return entry.get(id);
	}

	/*
	 * tries to download new values from internet and 
	 * saves them to the cfg file.
	 * otherwise keep all the old values
	 */
	public void update() { 
		try {
			Hashtable<String, BigDecimal> newEntry = new Hashtable<String, BigDecimal>();
			for (String currency : this.currencies) {
				BigDecimal value = internetConvert(new BigDecimal(1), "GBP", currency);
				newEntry.put(currency, value);
				settings.Set(currency, value.toPlainString());
				// TODO: save settings here. 
			}
		} catch (Exception e) {
			// do nothing
		}
	}

	/**
	 * currencyConvert connects to xe.com to convert between two currencies
	 * 
	 * b = internetConvert(BigDecimal(1000), CurrencyKey.GBP, CurrencyKey.USD)
	 * @throws IOException 
	 */
	static BigDecimal internetConvert (BigDecimal amount, String fromKey, String toKey) throws IOException {

		String url = String.format(
			"http://www.xe.com/ucc/convert.cgi?Amount=%s&From=%s&To=%s",
			amount.toPlainString(),
			fromKey,
			toKey);

		Document doc = Jsoup.connect(url).get();
		Elements src = doc.select("td.rate");
		assert src.size() == 2 : "Error Parsing Page";

		// converts "1,000.00 USD" -> "1000.00" -> BigDecimal
		String tmp = src.get(1).text().replaceAll(",", "");
		return new BigDecimal(tmp.substring(0, tmp.length()-toKey.toString().length()-1));
	}
	
}
