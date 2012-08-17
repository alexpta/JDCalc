package com.alexpta.jdcalc;

import java.text.DateFormat;
import java.util.Calendar;

import com.alexpta.android.dialogs.DatePickerClient;
import com.alexpta.android.dialogs.DatePickerFragment;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.FragmentActivity;

public class Gregorian2JulianActivity extends FragmentActivity {

	private EditText gDateTxt;
	private EditText jDateTxt;
	private DateFormat df;
	private JDCalculator jdcalc;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gregorian2julian);
        df = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        jdcalc = new JDCalculator();
        gDateTxt = (EditText)findViewById(R.id.gdateTxt);
        jDateTxt = (EditText)findViewById(R.id.jdateTxt);
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
			}
		});
        newFragment.show(getSupportFragmentManager(), "datePicker");    	
    }
    
    public void calculate(View view) {
    	
    }
    
    public void reverseCalculate(View view) {
    	
    }
}
