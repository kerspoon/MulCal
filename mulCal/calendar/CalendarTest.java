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
	public void testDiffDay() {
		
		Calendar cal = new Calendar();
		cal.setFromDate(new GregorianCalendar(2000, 1, 1, 1, 1, 1));
		cal.setToDate(new GregorianCalendar(2000, 1, 11, 1, 1, 1));
		assertEquals(cal.getDateDiff(java.util.Calendar.DAY_OF_MONTH),10);
	}

	/**
	 * Test method for {@link mulCal.calendar.Calendar#getDateDiff(int)}.
	 */
	@Test
	public void testGetDateDay2() {
		
		Calendar cal = new Calendar();
		cal.setFromDate(new GregorianCalendar(2000, 1, 31, 1, 1, 1));
		cal.setToDate(new GregorianCalendar(2000, 2, 1, 1, 1, 1));
		assertEquals(cal.getDateDiff(java.util.Calendar.DAY_OF_MONTH),1);
	}
	
	/**
	 * Test method for {@link mulCal.calendar.Calendar#getDateDiff(int)}.
	 */
	@Test
	public void testGetDateMonth() {
		
		Calendar cal = new Calendar();
		cal.setFromDate(new GregorianCalendar(2000, 1, 1, 1, 1, 1));
		cal.setToDate(new GregorianCalendar(2000, 4, 1, 1, 1, 1));
		assertEquals(cal.getDateDiff(java.util.Calendar.MONTH),3);
	}
	
	/**
	 * Test method for {@link mulCal.calendar.Calendar#getDateDiff(int)}.
	 */
	@Test
	public void testGetDateMonth2() {
		
		Calendar cal = new Calendar();
		cal.setFromDate(new GregorianCalendar(2000, 1, 1, 1, 1, 1));
		cal.setToDate(new GregorianCalendar(2001, 4, 1, 1, 1, 1));
		assertEquals(cal.getDateDiff(java.util.Calendar.MONTH),15);
	}
}
