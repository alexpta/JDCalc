package com.alexpta.jdcalc;

import java.util.Calendar;
import java.util.Date;


public class JDCalculator {

	public long getJDN(int year, int month, int day, boolean bc, boolean jc) {
		if(bc) {
			year = -(year - 1);
		}
    	long a = (14 - month)/12;
    	long y = year + 4800 - a;
    	long m = month + 12 * a - 3;
    	//System.out.println("a=" + a + ", y=" + y + ", m=" + m);
    	long jdn = day + (153 * m + 2) / 5 + 365 * y + y / 4 - (jc ? 0 : y / 100) + (jc ? 0 : y / 400) - (jc ? 32083 : 32045);
    	return jdn;
    }

	public long getJDN(Date date, boolean bc, boolean jc) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH);
    	int day = cal.get(Calendar.DATE);
    	 return getJDN(year, month, day, bc, jc);
		
	}

	public long getJDN(Date date) {
		return getJDN(date, false, false);
	}
}
