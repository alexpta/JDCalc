package com.alexpta.jdcalc;

import java.util.Calendar;

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
	
	private JDCalculator jdcalc;
	private CheckBox bcGChkBox;
	private CheckBox bcJChkBox;
	private EditText gYear;
	private EditText gMonth;
	private EditText gDay;
	private EditText jYear;
	private EditText jMonth;
	private EditText jDay;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gregorian2julian);
        jdcalc = new JDCalculator();
        gYear = (EditText)findViewById(R.id.gYearTxt);
        gMonth = (EditText)findViewById(R.id.gMonthTxt);
        gDay = (EditText)findViewById(R.id.gDayTxt);
        jYear = (EditText)findViewById(R.id.jYearTxt);
        jMonth = (EditText)findViewById(R.id.jMonthTxt);
        jDay = (EditText)findViewById(R.id.jDayTxt);
        bcGChkBox = (CheckBox)findViewById(R.id.bcGChkBox);
        bcJChkBox = (CheckBox)findViewById(R.id.bcJChkBox);
		Calendar cal = Calendar.getInstance();
    	gYear.setText("" + cal.get(Calendar.YEAR));
    	gDay.setText("" + cal.get(Calendar.DATE));
    	gMonth.setText("" + (cal.get(Calendar.MONTH) + 1));
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
		    	gYear.setText("" + year);
		    	gDay.setText("" + day);
		    	gMonth.setText("" + (month + 1));
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
		    	jYear.setText("" + year);
		    	jDay.setText("" + day);
		    	jMonth.setText("" + (month + 1));
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
    		int year = Integer.parseInt(gYear.getText().toString());
    		int month = Integer.parseInt(gMonth.getText().toString());
    		int day = Integer.parseInt(gDay.getText().toString());
        	long jdn = jdcalc.getJDN(year, month, day, bcGChkBox.isChecked(), false);
        	Date date = jdcalc.getDate(jdn, true);
			Log.d(TAG, date.getYear() + "/" + date.getMonth() + "/" + date.getDay());
			Log.d(TAG, "BC? " + (Date.BC == date.getEra()));
			bcJChkBox.setChecked(Date.BC == date.getEra());
			jYear.setText("" + date.getYear());
			jMonth.setText("" + date.getMonth());
			jDay.setText("" + date.getDay());
    	}
    	catch(NumberFormatException exc) {
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
    		int year = Integer.parseInt(jYear.getText().toString());
    		int month = Integer.parseInt(jMonth.getText().toString());
    		int day = Integer.parseInt(jDay.getText().toString());
        	long jdn = jdcalc.getJDN(year, month, day, bcJChkBox.isChecked(), true);
        	Date date = jdcalc.getDate(jdn, false);
			Log.d(TAG, date.getYear() + "/" + date.getMonth() + "/" + date.getDay());
			Log.d(TAG, "BC? " + (Date.BC == date.getEra()));
			bcGChkBox.setChecked(Date.BC == date.getEra());
			gYear.setText("" + date.getYear());
			gMonth.setText("" + date.getMonth());
			gDay.setText("" + date.getDay());
    	}
    	catch(NumberFormatException exc) {
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
