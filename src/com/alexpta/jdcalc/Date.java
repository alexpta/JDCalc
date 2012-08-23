package com.alexpta.jdcalc;

public class Date {

	public static final int BC = 0;
	public static final int AD = 1;
	
	private int year;
	private int month;
	private int day;
	private int era;
	
	public Date(int year, int month, int day) {
		this();
		this.year = year;
		this.month = month;
		this.day = day;
	};
	
	public Date() {
		era = AD; 
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getEra() {
		return era;
	}

	public void setEra(int era) {
		this.era = era;
	}
}
