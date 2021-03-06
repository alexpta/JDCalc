package com.alexpta.jdcalc;

import java.util.Calendar;


public class JDCalculator {
	
	public long getJDN(int year, int month, int day, boolean bc, boolean jc) {
		if(bc) {
			year = -(year - 1);
		}
    	long a = (14 - month)/12;
    	long y = year + 4800 - a;
    	long m = month + 12 * a - 3;
        System.out.println("JDCalculator.getJDN: a=" + a + ", y=" + y + ", m=" + m);
    	long jdn = day + (153 * m + 2) / 5 + 365 * y + y / 4 - (jc ? 0 : y / 100) + (jc ? 0 : y / 400) - (jc ? 32083 : 32045);
    	return jdn;
    }

	public long getJDN(java.util.Date date, boolean bc, boolean jc) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH) + 1;
    	int day = cal.get(Calendar.DATE);
    	//System.out.println(year + "/" + month + "/" + day);
    	return getJDN(year, month, day, bc, jc);
		
	}

	public long getJDN(java.util.Date date) {
		return getJDN(date, false, false);
	}
	
	public Date getDate(long JDN, boolean isJC) {
		int year, month, day;
		if(isJC) {
			long c = JDN + 32082;
			long d = (4 * c  + 3) / 1461;
			long e = c - 1461 * d / 4;
			long m = (5 * e + 2) / 153;
			System.out.println("JDCalculator.getDate: c=" + c + ", d=" + d + ", e=" + e + ", m=" + m);
			day = (int)e - (int)((153 * m + 2) / 5) + 1;
			month = (int)m + 3 - 12 * ((int)m / 10);
			year = (int)d - 4800 + (int)m / 10;
		}
		else { // gregorian calendar
			long a = JDN + 32044;
			long b = (4 * a + 3) / 146097;
			long c = a - (146097 * b / 4);
			long d = (4 * c  + 3) / 1461;
			long e = c - 1461 * d / 4;
			long m = (5 * e + 2) / 153;
			System.out.println("JDCalculator.getDate: a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e=" + e + ", m=" + m);
			day = (int)e - (int)((153 * m + 2) / 5) + 1;
			month = (int)m + 3 - 12 * ((int)m / 10);
			year = (int)(100 * b + d - 4800 + m / 10);
		}
		System.out.println(year + "/" + month + "/" + day);
		Date date = new Date(year, month, day);
		if(year <= 0) {
			date.setEra(Date.BC);
			date.setYear(Math.abs(year) + 1);
		}
    	return date;
	}
}
