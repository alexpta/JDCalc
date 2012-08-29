package com.alexpta.jdcalc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class WeekDayActivity extends MainActivity {
	
	private static final String TAG = "JDCALC.WeekDayActivity";

    @Override
    protected void init() {
    	setContentView(R.layout.activity_week_day);
    }

//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        
//    }
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_week_day, menu);
        return true;
    }
    
	protected void calculate() {
		try {
    		int year = Integer.parseInt(yearTxt.getText().toString());
    		int month = Integer.parseInt(monthTxt.getText().toString());
    		int day = Integer.parseInt(dayTxt.getText().toString());
        	long jdn = jdcalc.getJDN(year, month, day, bcChkBox.isChecked(), jcChkBox.isChecked());
        	int weekday = (int)(jdn % 7);
        	String dayNumber = null;
        	Resources res = getResources();
        	switch(weekday) {
        	case 0:
        		dayNumber = res.getString(R.string.monday);
        		break;
        	case 1:
        		dayNumber = res.getString(R.string.tuesday);
        		break;
        	case 2:
        		dayNumber = res.getString(R.string.wednesday);
        		break;
        	case 3:
        		dayNumber = res.getString(R.string.thursday);
        		break;
        	case 4:
        		dayNumber = res.getString(R.string.friday);
        		break;
        	case 5:
        		dayNumber = res.getString(R.string.saturday);
        		break;
        	case 6:
        		dayNumber = res.getString(R.string.sunday);
        		break;
        	}
        	outView.setText(dayNumber);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.jdn_calc:
            	Intent intent = new Intent(this, MainActivity.class);
            	startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
	}
   
}
