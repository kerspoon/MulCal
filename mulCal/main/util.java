package mulCal.main;

/*
import java.math.BigDecimal;
import org.w3c.dom.Document;
import org.w3c.dom.Element;*/

public class util {

	/**
	 * currencyConvert connects to xe.com to convert between two currencies
	 * 
	 * b = Util.currencyConvert(BigDecimal(1000), CurrencyKey.GBP, CurrencyKey.USD)
	 */
/*	static BigDecimal currencyConvert (BigDecimal amount, CurrencyKey fromKey, CurrencyKey toKey) {

		String url = formatter.format(
			"http://www.xe.com/ucc/convert.cgi?Amount=%s&From=%s&To=%s",
			amount.toPlainString(),
			fromKey.toString(),
			toKey.toString());

		Document doc = Jsoup.connect(url).get();
		Element src = doc.select("td.rate");
		assert src.size() == 2 : "Error Parsing Page"

		// converts "1,000.00 USD" -> "1000.00" -> BigDecimal
		String tmp = src.get(1).text().replaceAll(",", "")
		return BigDecimal(tmp.substring(0, tmp.size()-toKey.toString().length()-1));
	}*/
	
//
//	# PositiveInteger 'num', String 'rep' -> String
//	def base10toN(num, num_rep=string.digits+string.ascii_lowercase):
//	    """Change 'num' to a base-n number using the specidief representation
//	for instance: base10toN(num,"0123456789ABCDEF") is convert to hex
//	for instance: base10toN(num,"ABCDEFGHIJKLMNOP") is also hex with a diff look
//	for instance: base10toN(num,"01") convert to binary
//	it can convert up to 10+26+26 = 62 using lower-ascii, upper-acsii, and digits
//	"""
//	base = len(num_rep)
//	    new_num_string = ''
//	    current = num
//	    while current != 0:
//	        remainder = current % base
//	        if 36 > remainder:
//	            remainder_string = num_rep[remainder]
//	        new_num_string = remainder_string + new_num_string
//	        current = current / base
//	    return new_num_string
	    
	public static String Base10toN(Integer num, String rep) {
		char[] repArr = rep.toCharArray();
		Integer base = rep.length();
		Integer current = num;
		String output = "";
		
		
		if (current == 0) {
			return Character.toString(repArr[0]);
		}
		while (current != 0) {
			Integer remainder = current % base;
			String next;
			if (36 > remainder) {
				next = Character.toString(repArr[remainder]);
			}
			else {
				next = "!";
				assert false;
			}
			output = next + output;
	        current = current / base;
		}
		return output;
	}
	
	public static String removeQuotes(String txt) {
		return txt.substring(1, txt.length()-1);
	}
	
}
