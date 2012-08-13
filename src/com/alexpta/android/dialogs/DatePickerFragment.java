package com.alexpta.android.dialogs;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.support.v4.app.DialogFragment;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	
	private DatePickerClient client;
	
	public DatePickerFragment() {}
	
	public DatePickerClient getClient() {
		return client;
	}

	public void setClient(DatePickerClient client) {
		this.client = client;
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		client.setDate(year, month, day);
	}
}