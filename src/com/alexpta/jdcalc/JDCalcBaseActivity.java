package com.alexpta.jdcalc;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class JDCalcBaseActivity extends FragmentActivity {

	private JDCalculator jdcalc;
	private DateValidator validator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jdcalc = new JDCalculator();
        validator = new DateValidator();
    }

	public JDCalculator getJDCalc() {
		return jdcalc;
	}

	public DateValidator getDateValidator() {
		return validator;
	}

}
