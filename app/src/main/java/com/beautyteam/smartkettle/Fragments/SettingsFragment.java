package com.beautyteam.smartkettle.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.beautyteam.smartkettle.R;

import java.util.HashMap;

/**
 * Created by Admin on 26.10.2014.
 */
public class SettingsFragment extends Fragment {

    public static String[] settingsName = {"Настройка1", "Настройка2", "Настройка3"};

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
    private CompoundButton.OnCheckedChangeListener mCallback;


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
    }
}
