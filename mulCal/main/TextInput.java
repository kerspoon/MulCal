package mulCal.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mulCal.history.History.HistoryItem;
import mulCal.main.Main;
import mulCal.util.KeyException;


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
	        
	        if (key.length() == 0) {
	        	return;
	        } else if ("quit".startsWith(key)) {
	        	// todo: quit properly
	        	quitting = true;
	        } else if ("help".startsWith(key)) {
	        	System.out.println("");
	        	System.out.println("---- MulCal ----");
	        	System.out.println("");
	        	System.out.println("by James Brooks (kerspoon)");
	        	System.out.println("");
	        	System.out.println("---- Usage ----");
	        	System.out.println("");
	            System.out.println("q[uit]                   -- exit the program");
	            System.out.println("h[elp]                   -- display this message");
	            System.out.println("s[ave]     <filename>    -- save history to csv file for excel import");
	            System.out.println("r[eset]                  -- clear history");
	            System.out.println("l[oad]     <filename>    -- replace history with csv file");
	            System.out.println("e[quation] <equation>    -- evaluate equation and add to history");
	            System.out.println("c[omment]  <id> <string> -- add comment to history item");
	            System.out.println("p[rint]    <id?>         -- show history item; black for all");
	            System.out.println("u[pdate]                 -- download new currency conversion values");
	            System.out.println("f[rom]     <date>        -- update the from date (and selects it)");
	            System.out.println("t[ill]     <date>        -- update the till date (and selects it)");
	            System.out.println("d[ays]     <number>      -- update selected date to give correct days");
	            System.out.println("w[eeks]    <number>      -- update selected date to give correct weeks");
	            System.out.println("m[onths]   <number>      -- update selected date to give correct months");
	            System.out.println("y[ears]    <number>      -- update selected date to give correct years");
	        	System.out.println("");
	        	System.out.println("---- Example ----");
	        	System.out.println("");
	        	System.out.println("    > e 1+1");
	        	System.out.println("    A ::= 1+1 = 2");
	        	System.out.println("    > e A*2");
	        	System.out.println("    B ::= A*2 = 4");
	        	System.out.println("    > e cos PI");
	        	System.out.println("    D ::= cos PI = -1");
	        	System.out.println("    > q");
	        	System.out.println("    Done.");
	        	System.out.println("");
	        	System.out.println("--------");
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
					// e.printStackTrace();
	        		System.out.println("Equation Failed: " + e.getMessage());
				}
	        } else if ("comment".startsWith(key)) {
	    	    Pattern pattern2 = Pattern.compile("\\A\\s*(\\S*)\\s*(.*)");
	    	    Matcher matcher2 = pattern2.matcher(val);
	    	    
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
	        } else if ("print".startsWith(key)) {
	        	if (val.isEmpty()) {
	        		Collection<HistoryItem> history = main.getHistory();
	        		if (history.isEmpty()) {
		        		System.out.println("No history items.");
	        		} else {
		        		for (HistoryItem item : history) {
			        		System.out.format("%s ::= %s = %s\t\"%s\"%n",
			        				item.id,
			        				item.equation,
			        				item.result,
			        				item.comment);
						}
	        		}
	        	} else {
	        		try {
						HistoryItem item = main.getHistoryItem(val);
		        		System.out.format("%s ::= %s = %s\t\"%s\"%n",
		        				item.id,
		        				item.equation,
		        				item.result,
		        				item.comment);
					} catch (KeyException e) {
		        		System.out.println("Print Failed.");
					}
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
    	System.out.println("MulCal: A command line calcluator by James Brooks (kerspoon)");
    	System.out.println("h for help, q for quit");
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
	        System.out.println("Error reading from stream");
    	}
        System.out.println("Done.");
	}
}
