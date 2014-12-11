package com.beautyteam.smartkettle;

import android.app.Activity;
import android.os.Bundle;

import com.beautyteam.smartkettle.Fragments.SettingsFragment;

/**
 * Created by asus on 11.12.2014.
 */
public class SettingsActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Display the fragment as the main content.
            getFragmentManager().beginTransaction().replace(android.R.id.content,
                    new SettingsFragment()).commit();
        }
}
