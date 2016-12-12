package com.danielacedo.manageproductrecycler;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Daniel on 2/11/16.
 */

public class AccountSettings_Activity extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.account_settings);
    }
}
