package com.alexpta.jdcalc;

import java.util.Calendar;

import com.alexpta.jdcalc.R;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private static final String LOG_TAG = "JDCALC";
	
	private DatePicker datePicker;
	private EditText outView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outView = (EditText)findViewById(R.id.outView);
        datePicker = (DatePicker)findViewById(R.id.datePicker1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private long getJDN(int year, int month, int day) {
    	long a = (14 - month)/12;
    	long y = year + 4800 - a;
    	long m = month + 12 * a - 3;
    	//System.out.println("a=" + a + ", y=" + y + ", m=" + m);
    	long jdn = day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045;
    	return jdn;
    }

    public void setToday(View view) {
    	Calendar cal = Calendar.getInstance();
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH);
    	int day = cal.get(Calendar.DATE);
    	datePicker.updateDate(year, month, day);
    }
    
    public void calculateJDN(View view) {
    	int year = datePicker.getYear();
    	int month = datePicker.getMonth() + 1;
    	int day = datePicker.getDayOfMonth();
    	Log.d(LOG_TAG, year + "/" + month + "/" + day);
    	long jdn = getJDN(year, month, day);
    	outView.setText("" + jdn);
    }
    
}
