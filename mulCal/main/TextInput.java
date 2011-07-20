package mulCal.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mulCal.main.Main;


// http://www.exampledepot.com/egs/java.io/ReadFromStdIn.html 

public class TextInput {

	private Main main;
	private boolean quitting;
	
	public TextInput(){
		main = new Main();
		quitting = false;
	}
	
	public void process(String text) {
		// <spaces> <keyword> <spaces> <rest of line>

	    Pattern pattern = Pattern.compile("\\A\\s*(\\S*)\\s*(.*)");
	    Matcher matcher = pattern.matcher(text);
	    
	    if (matcher.find() && matcher.groupCount() == 2){
	        // System.out.format("I found the text \"%s\"%n", matcher.group(0));
	        String key = matcher.group(1);
	        String val = matcher.group(2);
	        
	        if ("quit".startsWith(key)) {
	        	// todo: quit properly
	        	quitting = true;
	        } else if ("save".startsWith(key)) {
	        	if (main.save(val)) {
	        		System.out.println("Save OK.");
	        	} else {
	        		System.out.println("Save Failed");
	        	}
	        } else if ("reset".startsWith(key)) {
	        	main.reset(val);
	        } else if ("load".startsWith(key)) {
	        	if (main.load(val)) {
	        		System.out.println("Load OK.");
	        	} else {
	        		System.out.println("Load Failed.");
	        	}
	        } else if ("equation".startsWith(key)) {
	        	try {
					BigDecimal result = main.equation(val);
					String id = main.getLastID();
	        		System.out.format("%s ::= %s = %s%n", id, val, result.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
	        		System.out.println("Equation Failed.");
				}
	        } else if ("comment".startsWith(key)) {
	    	    Pattern pattern2 = Pattern.compile("\\A\\s*(\\S*)\\s*(.*)");
	    	    Matcher matcher2 = pattern2.matcher(text);
	    	    
	    	    if (matcher2.find() && matcher2.groupCount() == 2){
		        	String id = matcher2.group(1);
		        	String comment = matcher2.group(2);
		        	if (main.comment(id, comment)) {
		        		System.out.println("Comment OK.");
		        	} else {
		        		System.out.println("Comment Failed.");
		        	}
	    	    } else {
	        		System.out.println("Comment Failed.");
	    	    }
	        } else if ("update".startsWith(key)) {
	        	main.update(val);
	        } else if ("from".startsWith(key)) {
	        	main.from(val);
	        } else if ("till".startsWith(key)) {
	        	main.till(val);
	        } else if ("days".startsWith(key)) {
	        	main.days(val);
	        } else if ("weeks".startsWith(key)) {
	        	main.weeks(val);
	        } else if ("months".startsWith(key)) {
	        	main.months(val);
	        } else if ("years".startsWith(key)) {
	        	main.years(val);
	        } else {
	        	System.out.println("unexpected keyword in input");
	        }
	    } else {
        	System.out.println("bad input string");
	    }
	}

    public static void main(String[] args){
    	try {
    		TextInput ti = new TextInput();
    	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    	    String str = "";
    	    while (!ti.quitting) {
    	        System.out.print("> ");
    	        str = in.readLine();
    	        ti.process(str);
    	    }
    	} catch (IOException e) {
	        System.out.println("Error reasing from stream");
    	}
        System.out.println("Done.");
	}
}
