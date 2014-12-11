package com.beautyteam.smartkettle.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;

import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.beautyteam.smartkettle.R;

import java.util.HashMap;

/**
 * Created by Admin on 26.10.2014.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String KEY_ADVANCED_CHECKBOX_PREFERENCE = "music";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);


        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences_settings);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Context mContext = getActivity().getApplicationContext();
        if (key.equals(KEY_ADVANCED_CHECKBOX_PREFERENCE)) {
            AudioManager amanager;
            amanager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

            if (PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(key, true)) {
                //media
                amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                Log.i("STREAM_MUSIC", "Set to true");
            } else {
                amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                Log.i("STREAM_MUSIC", "Set to False");
            }
            Log.d("set", "settings changed");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
