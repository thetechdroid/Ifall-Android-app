package com.vishwanath.android.ifallproject;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity {

	public void onCreate(Bundle b) {
		super.onCreate(b);
		addPreferencesFromResource(R.xml.settings);
	}

}
