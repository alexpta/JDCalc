package com.alexpta.jdcalc;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

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
	
	private EditText outView;
	private DateFormat df;
	private EditText dateTxt;
	private JDCalculator jdcalc;
	private CheckBox bcChkBox;
	private CheckBox jcChkBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outView = (EditText)findViewById(R.id.outText);
        dateTxt = (EditText)findViewById(R.id.dateTxt);
        bcChkBox = (CheckBox)findViewById(R.id.bcChkBox);
        jcChkBox = (CheckBox)findViewById(R.id.jcChkBox);
        df = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        jdcalc = new JDCalculator();
        today();
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
    	dateTxt.setText(df.format(cal.getTime()));
    	calculate();
	}
    
    public void calculateJDN(View view) {
    	calculate();
    }

	private void calculate() {
		try {
    		Date date = df.parse(dateTxt.getText().toString());
        	long jdn = jdcalc.getJDN(date, bcChkBox.isChecked(), jcChkBox.isChecked());
        	outView.setText("" + jdn);
    	}
    	catch(ParseException exc) {
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
    	newFragment.setClient(this);
        newFragment.show(getSupportFragmentManager(), "datePicker");    	
    }

	@Override
	public void setDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.YEAR, year);
    	cal.set(Calendar.MONTH, month);
    	cal.set(Calendar.DATE, day);
    	String date = df.format(cal.getTime());
    	dateTxt.setText(date);
		calculate();
	}
	
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.date_interval:
            	Intent intent = new Intent(this, DateIntervalActivity.class);
            	startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
