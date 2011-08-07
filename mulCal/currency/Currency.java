/**
 * 
 */
package mulCal.currency;

import java.io.File;
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
        
	public Currency(Settings settings) {
		this.settings = settings;
		this.entry = new Hashtable<String, BigDecimal>();
		for (String currency : this.currencies) {
			try {
				this.entry.put(currency, new BigDecimal(this.settings.Get(currency)));
			} catch (KeyException e) {
				throw new RuntimeException("Programmer error: missing currency " + currency);
			}
		}
	}
	
	public BigDecimal convert(BigDecimal value, String from, String to) {
        // it would be quicker to calc a lookup table for [to]/[from]
        // but lets see if it's slow first
		try {
			return value.multiply(get(to).divide(get(from)));
		} catch (ArithmeticException e) {
			return value.multiply(get(to).divide(
					get(from), 
					20, 
					BigDecimal.ROUND_HALF_UP));
		}
	}
	
	public BigDecimal get(String id) {
		return entry.get(id);
	}

	/*
	 * tries to download new values from internet and 
	 * saves them to the cfg file.
	 * otherwise keep all the old values
	 */
	public void update() throws KeyException, IOException { 
		for (String currency : this.currencies) {
			BigDecimal value = internetConvert(new BigDecimal(1), "GBP", currency);
			entry.put(currency, value);
			settings.Set(currency, value.toPlainString());
			// TODO: save settings here. 
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

		String userAgent = "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.109 Safari/535.1";
		Document doc = Jsoup.connect(url).userAgent(userAgent).get();

		Elements src = doc.select("tr.CnvrsnTxt > td");
		assert src.size() == 3 : "Error Parsing Page";
    	
		// converts "1,000.00 USD" -> "1000.00" -> BigDecimal
		String tmp = src.get(2).text().replaceAll(",", "");
		return new BigDecimal(tmp.substring(0, tmp.length()-toKey.toString().length()-1));
		
	}

	/**
	 * currencyConvert connects to xe.com to convert between two currencies
	 * 
	 * b = internetConvert(BigDecimal(1000), CurrencyKey.GBP, CurrencyKey.USD)
	 * @throws IOException 
	 */
	static BigDecimal internetTestConvert (BigDecimal amount, String fromKey, String toKey) throws IOException {

		Document doc = Jsoup.parse(new File("currencyTestPage.html"), "utf-8");

		Elements src = doc.select("tr.CnvrsnTxt > td");
		assert src.size() == 3 : "Error Parsing Page";
    	
		// converts "1,000.00 USD" -> "1000.00" -> BigDecimal
		String tmp = src.get(2).text().replaceAll(",", "");
		return new BigDecimal(tmp.substring(0, tmp.length()-toKey.toString().length()-1));
	}
}
