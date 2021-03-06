/**
 * 
 */
package mulCal.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * @author james
 *
 */
public class Calendar {
	
	private GregorianCalendar fromDate;
	private GregorianCalendar toDate;
	
	public Calendar() {
		fromDate = (GregorianCalendar) GregorianCalendar.getInstance();
		toDate   = (GregorianCalendar) GregorianCalendar.getInstance();
	}

	/**
	 * @return the fromDate
	 */
	public java.util.Calendar getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(GregorianCalendar fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public java.util.Calendar getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(GregorianCalendar toDate) {
		this.toDate = toDate;
	}

	/**
	 * @param fromDate the fromDate to set as "yyyy/MM/dd"
	 * @throws ParseException 
	 */
	public void setFromDateString(String fromString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		this.fromDate.setTime(sdf.parse(fromString));
	}

	/**
	 * @param toDate the toDate to set as "yyyy/MM/dd"
	 * @throws ParseException 
	 */
	public void setToDateString(String toString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		this.toDate.setTime(sdf.parse(toString));
	}
	
	// Use one of the constants from Calendar, e.g. DATE, WEEK_OF_YEAR,  
	//  or MONTH, as the calUnit variable.  Supply two Date objects.  
	//  This method returns the number of days, weeks, months, etc.  
	//  between the two dates.  In other words it returns the result of  
	//  subtracting two dates as the number of days, weeks, months, etc.  
	public long getDateDiff( int calUnit ) {  
		// make sure d1 < d2, else swap them  
		if( fromDate.after(toDate) ) {    
			GregorianCalendar temp = fromDate;
			fromDate = toDate;
			toDate = temp;
		}
		
		// add one day, week, year, etc.
	    for( long i=1; ; i++ ) {
	    	fromDate.add( calUnit, 1 );   
	    	if( fromDate.after(toDate) ) {
	    		return i-1;
	    	}
	    }
	}
}
