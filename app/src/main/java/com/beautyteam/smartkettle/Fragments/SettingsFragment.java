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
            amanager =(AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);

            if (PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(key,true)) {
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

    /*public static String[] settingsName = {"Настройка1", "Настройка2", "Настройка3"};

    public static SettingsFragment getInstance(HashMap<String, Boolean> settingToValue){
        SettingsFragment settingsFragment = new SettingsFragment();
        Bundle arguments = new Bundle();
        // Ограниченное количество меню. Может нужна динамика.
        arguments.putBoolean(settingsName[0], settingToValue.get(settingsName[0]));
        arguments.putBoolean(settingsName[1], settingToValue.get(settingsName[1]));
        arguments.putBoolean(settingsName[2], settingToValue.get(settingsName[2]));
        settingsFragment.setArguments(arguments);
        return settingsFragment;
    }

    private CheckBox setting1;
    private CheckBox setting2;
    private CheckBox setting3;
    private CompoundButton.OnCheckedChangeListener mCall[back;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (CompoundButton.OnCheckedChangeListener)activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) { // Вроде и ничего, но пахнет костылем. Посоветоваться!
        setting1 =(CheckBox) view.findViewById(R.id.setting1);
        setting2 =(CheckBox) view.findViewById(R.id.setting2);
        setting3 =(CheckBox) view.findViewById(R.id.setting3);

        setting1.setChecked(getArguments().getBoolean(settingsName[0]));
        setting2.setChecked(getArguments().getBoolean(settingsName[1]));
        setting3.setChecked(getArguments().getBoolean(settingsName[2]));

        setting1.setOnCheckedChangeListener(mCallback);
        setting2.setOnCheckedChangeListener(mCallback);
        setting3.setOnCheckedChangeListener(mCallback);
    }*/
}
