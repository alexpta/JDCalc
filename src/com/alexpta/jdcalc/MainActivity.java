package com.alexpta.jdcalc;

import java.util.Calendar;

import com.alexpta.android.dialogs.DatePickerClient;
import com.alexpta.android.dialogs.DatePickerFragment;
import com.alexpta.jdcalc.R;

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

public class MainActivity extends FragmentActivity implements DatePickerClient {
	
	private static final String TAG = "JDCALC.MainActivity";
	
	protected EditText outView;
	protected JDCalculator jdcalc;
	protected CheckBox bcChkBox;
	protected CheckBox jcChkBox;
	protected EditText yearTxt;
	protected EditText monthTxt;
	protected EditText dayTxt;
	protected DateValidator validator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        outView = (EditText)findViewById(R.id.outText);
        yearTxt = (EditText)findViewById(R.id.yearTxt);
        monthTxt = (EditText)findViewById(R.id.monthTxt);
        dayTxt = (EditText)findViewById(R.id.dayTxt);
        bcChkBox = (CheckBox)findViewById(R.id.bcChkBox);
        jcChkBox = (CheckBox)findViewById(R.id.jcChkBox);
        jdcalc = new JDCalculator();
        validator = new DateValidator();
        today();
    }
    
    protected void init() {
    	setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void setToday(View view) {
    	today();
    }

	private void today() {
		Calendar cal = Calendar.getInstance();
    	yearTxt.setText("" + cal.get(Calendar.YEAR));
    	dayTxt.setText("" + cal.get(Calendar.DATE));
    	monthTxt.setText("" + (cal.get(Calendar.MONTH) + 1));
    	calculate();
	}
    
    public void calculateJDN(View view) {
    	calculate();
    }

	protected void calculate() {
		try {
    		int year = Integer.parseInt(yearTxt.getText().toString());
    		int month = Integer.parseInt(monthTxt.getText().toString());
    		int day = Integer.parseInt(dayTxt.getText().toString());
    		if(validator.validate(year, month, day)) {
    			long jdn = jdcalc.getJDN(year, month, day, bcChkBox.isChecked(), jcChkBox.isChecked());
    			outView.setText("" + jdn);
    		}
    		else {
    			throw new NumberFormatException();
    		}
    	}
    	catch(NumberFormatException exc) {
    		Log.d(TAG, "invalid date!!!");
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(R.string.invalid_date)
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
    
    public void showDatePicker(View view) {
    	DatePickerFragment newFragment = new DatePickerFragment();
    	Bundle bundle = new Bundle();
    	try {
    		bundle.putInt(DatePickerFragment.YEAR_PARAM, Integer.parseInt(yearTxt.getText().toString()));
    		bundle.putInt(DatePickerFragment.MONTH_PARAM, Integer.parseInt(monthTxt.getText().toString()) - 1);
    		bundle.putInt(DatePickerFragment.DAY_PARAM, Integer.parseInt(dayTxt.getText().toString()));
    	}
    	catch(NumberFormatException exc) {
    		// ignore
    	}
    	newFragment.setArguments(bundle);
    	newFragment.setClient(this);
        newFragment.show(getSupportFragmentManager(), "datePicker");    	
    }

	@Override
	public void setDate(int year, int month, int day) {
    	yearTxt.setText("" + year);
    	dayTxt.setText("" + day);
    	monthTxt.setText("" + (month + 1));
		calculate();
	}
	
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.date_interval:
            	Intent intent = new Intent(this, DateIntervalActivity.class);
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
    
    public void reverseCalculate(View view) {
		try {
			long jdn = Long.parseLong(outView.getText().toString());
			if(jdn < 0) {
				throw new NumberFormatException();
			}
			Date date = jdcalc.getDate(jdn, jcChkBox.isChecked());
			Log.d(TAG, date.getYear() + "/" + date.getMonth() + "/" + date.getDay());
			Log.d(TAG, "BC? " + (Date.BC == date.getEra()));
			bcChkBox.setChecked(Date.BC == date.getEra());
			yearTxt.setText("" + date.getYear());
			monthTxt.setText("" + date.getMonth());
			dayTxt.setText("" + date.getDay());
    	}
    	catch(NumberFormatException exc) {
    		Log.d(TAG, "invalid JDN!!!");
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(R.string.invalid_date)
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
