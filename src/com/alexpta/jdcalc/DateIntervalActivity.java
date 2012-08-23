package com.alexpta.jdcalc;

import java.util.Calendar;

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

	private EditText outTxt;
	private JDCalculator jdcalc;
	private CheckBox bcFrom;
	private CheckBox bcTo;
	private CheckBox jcFrom;
	private CheckBox jcTo;
	private EditText fromYear;
	private EditText fromMonth;
	private EditText fromDay;
	private EditText toYear;
	private EditText toMonth;
	private EditText toDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_interval);
        fromYear = (EditText)findViewById(R.id.fromYearTxt);
        fromMonth = (EditText)findViewById(R.id.fromMonthTxt);
        fromDay = (EditText)findViewById(R.id.fromDayTxt);
        toYear = (EditText)findViewById(R.id.toYearTxt);
        toMonth = (EditText)findViewById(R.id.toMonthTxt);
        toDay = (EditText)findViewById(R.id.toDayTxt);
        outTxt = (EditText)findViewById(R.id.outText);
        jdcalc = new JDCalculator();
        bcFrom = (CheckBox)findViewById(R.id.bcFrom);
        bcTo = (CheckBox)findViewById(R.id.bcTo);
        jcFrom = (CheckBox)findViewById(R.id.jcFrom);
        jcTo = (CheckBox)findViewById(R.id.jcTo);
        // set today date
		Calendar cal = Calendar.getInstance();
    	fromYear.setText("" + cal.get(Calendar.YEAR));
    	fromDay.setText("" + cal.get(Calendar.DATE));
    	fromMonth.setText("" + (cal.get(Calendar.MONTH) + 1));
    	toYear.setText("" + cal.get(Calendar.YEAR));
    	toDay.setText("" + cal.get(Calendar.DATE));
    	toMonth.setText("" + (cal.get(Calendar.MONTH) + 1));
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
    		int year = Integer.parseInt(fromYear.getText().toString());
    		int month = Integer.parseInt(fromMonth.getText().toString());
    		int day = Integer.parseInt(fromDay.getText().toString());
        	jdn0 = jdcalc.getJDN(year, month, day, bcFrom.isChecked(), jcFrom.isChecked());
    	}
    	catch(NumberFormatException exc) {
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
    		int year = Integer.parseInt(toYear.getText().toString());
    		int month = Integer.parseInt(toMonth.getText().toString());
    		int day = Integer.parseInt(toDay.getText().toString());
        	jdn1 = jdcalc.getJDN(year, month, day, bcTo.isChecked(), jcTo.isChecked());
        	long diff = Math.abs(jdn1 - jdn0);
        	outTxt.setText("" + diff);
    	}
    	catch(NumberFormatException exc) {
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
		    	fromYear.setText("" + year);
		    	fromDay.setText("" + day);
		    	fromMonth.setText("" + (month + 1));
			}
		});
        newFragment.show(getSupportFragmentManager(), "datePicker");    	
    }

    public void showToDatePicker(View view) {
    	DatePickerFragment newFragment = new DatePickerFragment();
    	newFragment.setClient(new DatePickerClient() {
			
			@Override
			public void setDate(int year, int month, int day) {
		    	toYear.setText("" + year);
		    	toDay.setText("" + day);
		    	toMonth.setText("" + (month + 1));
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
