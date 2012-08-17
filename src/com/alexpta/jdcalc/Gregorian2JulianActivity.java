package com.alexpta.jdcalc;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.alexpta.android.dialogs.DatePickerClient;
import com.alexpta.android.dialogs.DatePickerFragment;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.support.v4.app.FragmentActivity;

public class Gregorian2JulianActivity extends FragmentActivity {

	private static final String TAG = "JDCALC.Gregorian2JulianActivity";
	
	private EditText gDateTxt;
	private EditText jDateTxt;
	private DateFormat df;
	private JDCalculator jdcalc;
	protected CheckBox bcGChkBox;
	protected CheckBox bcJChkBox;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gregorian2julian);
        df = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        jdcalc = new JDCalculator();
        gDateTxt = (EditText)findViewById(R.id.gdateTxt);
        jDateTxt = (EditText)findViewById(R.id.jdateTxt);
        bcGChkBox = (CheckBox)findViewById(R.id.bcGChkBox);
        bcJChkBox = (CheckBox)findViewById(R.id.bcJChkBox);
		Calendar cal = Calendar.getInstance();
    	gDateTxt.setText(df.format(cal.getTime()));
    	g2jCalc();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_gregorian2julian, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.jdn_calc:
            	Intent intent = new Intent(this, MainActivity.class);
            	startActivity(intent);
                return true;
            case R.id.weekday_menu:
            	Intent intent2 = new Intent(this, WeekDayActivity.class);
            	startActivity(intent2);
                return true;
            case R.id.date_interval:
            	Intent intent3 = new Intent(this, DateIntervalActivity.class);
            	startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void showGDatePicker(View view) {
    	DatePickerFragment newFragment = new DatePickerFragment();
    	newFragment.setClient(new DatePickerClient() {
			
			@Override
			public void setDate(int year, int month, int day) {
				Calendar cal = Calendar.getInstance();
		    	cal.set(Calendar.YEAR, year);
		    	cal.set(Calendar.MONTH, month);
		    	cal.set(Calendar.DATE, day);
		    	String date = df.format(cal.getTime());
		    	gDateTxt.setText(date);
		    	g2jCalc();
			}
		});
        newFragment.show(getSupportFragmentManager(), "datePicker");    	
    }

    public void showJDatePicker(View view) {
    	DatePickerFragment newFragment = new DatePickerFragment();
    	newFragment.setClient(new DatePickerClient() {
			
			@Override
			public void setDate(int year, int month, int day) {
				Calendar cal = Calendar.getInstance();
		    	cal.set(Calendar.YEAR, year);
		    	cal.set(Calendar.MONTH, month);
		    	cal.set(Calendar.DATE, day);
		    	String date = df.format(cal.getTime());
		    	jDateTxt.setText(date);
		    	j2gCalc();
			}
		});
        newFragment.show(getSupportFragmentManager(), "datePicker");    	
    }
    
    public void calculate(View view) {
    	g2jCalc();
    }
    
    public void reverseCalculate(View view) {
    	j2gCalc();
    }
    
    public void g2jCalc() {
		try {
    		Date date = df.parse(gDateTxt.getText().toString());
        	long jdn = jdcalc.getJDN(date, bcGChkBox.isChecked(), false);
        	Calendar cal = jdcalc.getDate(jdn, true);
			Log.d(TAG, cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DATE));
			Log.d(TAG, "BC? " + (GregorianCalendar.BC == cal.get(Calendar.ERA)));
			bcJChkBox.setChecked(GregorianCalendar.BC == cal.get(Calendar.ERA));
			jDateTxt.setText(df.format(cal.getTime()));
    	}
    	catch(ParseException exc) {
    		Log.d(TAG, "invalid gregorian date!!!");
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(R.string.invalid_gregorian_date)
    		       .setCancelable(true)
    		       .setTitle(R.string.error_dlg_title)
    		       .setNeutralButton("OK", new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		                dialog.cancel();
    		           }
    		       });
    		AlertDialog alert = builder.create();
    		alert.show();
    	}
    }
    
    public void j2gCalc() {
		try {
    		Date date = df.parse(jDateTxt.getText().toString());
        	long jdn = jdcalc.getJDN(date, bcJChkBox.isChecked(), true);
        	Calendar cal = jdcalc.getDate(jdn, false);
			Log.d(TAG, cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DATE));
			Log.d(TAG, "BC? " + (GregorianCalendar.BC == cal.get(Calendar.ERA)));
			bcGChkBox.setChecked(GregorianCalendar.BC == cal.get(Calendar.ERA));
			gDateTxt.setText(df.format(cal.getTime()));
    	}
    	catch(ParseException exc) {
    		Log.d(TAG, "invalid julian date!!!");
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(R.string.invalid_julian_date)
    		       .setCancelable(true)
    		       .setTitle(R.string.error_dlg_title)
    		       .setNeutralButton("OK", new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		                dialog.cancel();
    		           }
    		       });
    		AlertDialog alert = builder.create();
    		alert.show();
    	}
    }
}
