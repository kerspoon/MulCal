package mulCal.history;

import static mulCal.main.util.Base10toN;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.TreeMap;

import lib.au.com.bytecode.opencsv.CSVReader;
import lib.au.com.bytecode.opencsv.CSVWriter;
import mulCal.equationParser.EvalSpecial;
import mulCal.util.KeyException;


/*
	---- CSV Example ----
	
	a, "7.23"                                  , 7.23   , "pounds per hour" 
	b, "a * 8"                                 , 57.84  , "daily wage" 
	c, "(b/8)/3"                               , 2.41   , "" 
	d, "GBP_USD b"                             , 76.93  , "daily USD" 
	e, "d * [days 2010/2/4 2010/2/11]"         , 846.20 , "final value" 
	
 */


public class History {
	
	public static class HistoryItem {
		public String id;
		public String equation;
		public BigDecimal result;
		public String comment;
		
		public HistoryItem(String id, String equation, BigDecimal result) {
			this.id = id;
			this.equation = equation;
			this.result = result;
			this.comment = "";
		}

		public boolean SanityCheck() {
			// all not null
			// id,equation not zero length
			// TODO: id,equation,comment only contain valid chars (no ") 
			// OPTIONAL: eval(equation) == result
			
			if (this.id == null || 
					this.equation == null || 
					this.result == null || 
					this.comment == null ||
					this.id.length() == 0 ||
					this.equation.length() == 0) {
				return false;
			}
			return true;
		}
	}

	private static String asciiUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private TreeMap<String, HistoryItem> items;
	private int currentID;
	
	public History() {
		this.currentID = 0;
		this.items = new TreeMap<String, HistoryItem>();
	}

	public void Load(String fileName) throws IOException {
		// e, "d * [days 2010/2/4 2010/2/11]"         , 846.20 , "final value" 

		CSVReader reader = new CSVReader(new FileReader(fileName));
		try {
			String [] line;
			while ((line = reader.readNext()) != null) {
				// nextLine[] is an array of values from the line
				if (line.length != 4) {
					throw new IllegalArgumentException("error with row insertion");
				}
				String id = line[0];
				String equation = line[1];
				BigDecimal result = new BigDecimal(line[2]);
				HistoryItem row = new HistoryItem(id, equation, result);
				row.comment = line[3];
				if (this.InsertOK(row)) {
					this.items.put(row.id, row);
				}
				else {
					throw new IllegalArgumentException("error with row insertion");
				}
			}
		} finally {
			reader.close();
		}
	}

	public void Save(String fileName) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(fileName), ',');
		try {
		    for (HistoryItem item : this.items.values()) {
		    	String[] entries = new String[4];
		    	item.SanityCheck();
		    	entries[0] = item.id;
		    	entries[1] = item.equation;
		    	entries[2] = item.result.toPlainString();
		    	entries[3] = item.comment;
			    writer.writeNext(entries);
			}
		} finally {
		    writer.close();
		}
	}
	
	public void Clear() {
		this.items.clear();
	}

	
	public void SetComment(String id, String comment) throws KeyException {
		if (this.items.containsKey(id))	{
			HistoryItem item = this.items.get(id);
			item.comment = comment;
			this.items.put(item.id, item);
		} else {
			throw new KeyException();
		}
	}
	
	public String Add(String equation, BigDecimal result) {
		String id = this.NewID();
		HistoryItem item = new HistoryItem(id, equation, result);

		if (this.InsertOK(item)) {
			this.items.put(id, item);
			return id;
		} else {
			throw new IllegalArgumentException("error with row insertion");
		}
	}

	public Collection<HistoryItem> getAll() {
		return this.items.values();
	}
	
	public HistoryItem get(String id) throws KeyException {
		if (this.items.containsKey(id))	{
			return this.items.get(id);
		} else {
			throw new KeyException(id);
		}
	}

	public boolean ContainsID(String id) {
		return this.items.containsKey(id);
	}
	
	public int size() {
		return this.items.size();
	}

	private String NewID() {
		// could check that it doesn't match any keywords
		// but if we ensure functions all start in lowercase we are ok
		// returns one at a time: 'A', 'B', 'C', ..., 'BA', 'BB', ..., 'ZZZZ...'
		// make sure we don't match any constants
		String result;
		do {
			result = Base10toN(this.currentID, History.asciiUpperCase);
			this.currentID += 1;
		} while (EvalSpecial.constants.containsKey(result));
		return result;
	}
	
	private boolean InsertOK(HistoryItem row) {
		if (row.SanityCheck() == false) {
			return false;
		}
		if (this.items.containsKey(row.id)) {
			return false;
		}
		return true;
	}
	
	public String lookupIdFromRow(int rowIndex) {
		int count=0;
		for (String id : items.navigableKeySet()) {
			if (rowIndex == count)
				return id;
			count+=1;
		}
		throw new RuntimeException("key error");
	}
}
