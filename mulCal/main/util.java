package mulCal.main;


public class util {

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
