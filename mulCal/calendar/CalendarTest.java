/**
 * 
 */
package mulCal.calendar;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * @author james
 *
 */
public class CalendarTest {

	/**
	 * Test method for {@link mulCal.calendar.Calendar#getDateDiff(int)}.
	 */
	@Test
	public void testDateDiffDay() {
		
		Calendar cal = new Calendar();
		cal.setFromDate(new GregorianCalendar(2000, 1, 1, 1, 1, 1));
		cal.setToDate(new GregorianCalendar(2000, 1, 11, 1, 1, 1));
		assertEquals(cal.getDateDiff(java.util.Calendar.DAY_OF_MONTH),10);
	}

	/**
	 * Test method for {@link mulCal.calendar.Calendar#getDateDiff(int)}.
	 */
	@Test
	public void testDateDiffDay2() {
		
		Calendar cal = new Calendar();
		cal.setFromDate(new GregorianCalendar(2000, 1, 31, 1, 1, 1));
		cal.setToDate(new GregorianCalendar(2000, 2, 1, 1, 1, 1));
		assertEquals(cal.getDateDiff(java.util.Calendar.DAY_OF_MONTH),1);
	}
	
	/**
	 * Test method for {@link mulCal.calendar.Calendar#getDateDiff(int)}.
	 */
	@Test
	public void testDateDiffMonth() {
		
		Calendar cal = new Calendar();
		cal.setFromDate(new GregorianCalendar(2000, 1, 1, 1, 1, 1));
		cal.setToDate(new GregorianCalendar(2000, 4, 1, 1, 1, 1));
		assertEquals(cal.getDateDiff(java.util.Calendar.MONTH),3);
	}
	
	/**
	 * Test method for {@link mulCal.calendar.Calendar#getDateDiff(int)}.
	 */
	@Test
	public void testDateDiffMonth2() {
		
		Calendar cal = new Calendar();
		cal.setFromDate(new GregorianCalendar(2000, 1, 1, 1, 1, 1));
		cal.setToDate(new GregorianCalendar(2001, 4, 1, 1, 1, 1));
		assertEquals(cal.getDateDiff(java.util.Calendar.MONTH),15);
	}
	
	/**
	 * Test method for {@link mulCal.calendar.Calendar#setFromDateString(String)}.
	 * @throws Exception 
	 */
	@Test
	public void testSetString() throws Exception {
		Calendar cal = new Calendar();
		String fromString = "2000/06/29";
		cal.setFromDateString(fromString);
		String s = String.format("%1$tY/%1$tm/%1$te", cal.getFromDate());
		assertEquals(s,fromString);
	}
}
