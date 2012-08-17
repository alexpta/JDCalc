package com.alexpta.jdcalc;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.alexpta.android.dialogs.DatePickerClient;
import com.alexpta.android.dialogs.DatePickerFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class DateIntervalActivity extends FragmentActivity {
	
	private static final String TAG = "JDCALC.DateIntervalActivity";

	private EditText fromTxt;
	private EditText toTxt;
	private EditText outTxt;
	private DateFormat df;
	private JDCalculator jdcalc;
	private CheckBox bcFrom;
	private CheckBox bcTo;
	private CheckBox jcFrom;
	private CheckBox jcTo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_interval);
        fromTxt = (EditText)findViewById(R.id.fromTxt);
        toTxt = (EditText)findViewById(R.id.toTxt);
        outTxt = (EditText)findViewById(R.id.outText);
        df = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        jdcalc = new JDCalculator();
        bcFrom = (CheckBox)findViewById(R.id.bcFrom);
        bcTo = (CheckBox)findViewById(R.id.bcTo);
        jcFrom = (CheckBox)findViewById(R.id.jcFrom);
        jcTo = (CheckBox)findViewById(R.id.jcTo);
        // set today date
		Calendar cal = Calendar.getInstance();
    	fromTxt.setText(df.format(cal.getTime()));
    	toTxt.setText(df.format(cal.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.date_interval, menu);
        return true;
    }
    
    public void subtract(View view) {
    	long jdn0 = 0;
    	long jdn1 = 0;
		try {
    		Date date = df.parse(fromTxt.getText().toString());
        	jdn0 = jdcalc.getJDN(date, bcFrom.isChecked(), jcFrom.isChecked());
    	}
    	catch(ParseException exc) {
    		Log.d(TAG, "invalid date!!!");
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(R.string.invalid_from_date)
    		       .setCancelable(true)
    		       .setTitle(R.string.error_dlg_title)
    		       .setNeutralButton("OK", new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		                dialog.cancel();
    		           }
    		       });
    		AlertDialog alert = builder.create();
    		alert.show();
    		return;
    	}
    	
		try {
    		Date date = df.parse(toTxt.getText().toString());
        	jdn1 = jdcalc.getJDN(date, bcTo.isChecked(), jcTo.isChecked());
        	long diff = Math.abs(jdn1 - jdn0);
        	outTxt.setText("" + diff);
    	}
    	catch(ParseException exc) {
    		Log.d(TAG, "invalid date!!!");
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(R.string.invalid_to_date)
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
    
    public void showFromDatePicker(View view) {
    	DatePickerFragment newFragment = new DatePickerFragment();
    	newFragment.setClient(new DatePickerClient() {
			
			@Override
			public void setDate(int year, int month, int day) {
				Calendar cal = Calendar.getInstance();
		    	cal.set(Calendar.YEAR, year);
		    	cal.set(Calendar.MONTH, month);
		    	cal.set(Calendar.DATE, day);
		    	String date = df.format(cal.getTime());
		    	fromTxt.setText(date);
			}
		});
        newFragment.show(getSupportFragmentManager(), "datePicker");    	
    }

    public void showToDatePicker(View view) {
    	DatePickerFragment newFragment = new DatePickerFragment();
    	newFragment.setClient(new DatePickerClient() {
			
			@Override
			public void setDate(int year, int month, int day) {
				Calendar cal = Calendar.getInstance();
		    	cal.set(Calendar.YEAR, year);
		    	cal.set(Calendar.MONTH, month);
		    	cal.set(Calendar.DATE, day);
		    	String date = df.format(cal.getTime());
		    	toTxt.setText(date);
			}
		});
        newFragment.show(getSupportFragmentManager(), "datePicker");    	
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
            case R.id.g2j_menu:
            	Intent intent3 = new Intent(this, Gregorian2JulianActivity.class);
            	startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
}
