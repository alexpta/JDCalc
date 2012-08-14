package com.alexpta.jdcalc;

import java.util.Calendar;
import java.util.Date;


public class JDCalculator {

	public long getJDN(int year, int month, int day) {
    	long a = (14 - month)/12;
    	long y = year + 4800 - a;
    	long m = month + 12 * a - 3;
    	//System.out.println("a=" + a + ", y=" + y + ", m=" + m);
    	long jdn = day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045;
    	return jdn;
    }

	public long getJDN(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH);
    	int day = cal.get(Calendar.DATE);
    	 return getJDN(year, month, day);
		
	}
}