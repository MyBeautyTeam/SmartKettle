package com.beautyteam.smartkettle.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beautyteam.smartkettle.R;

/**
 * Created by Admin on 29.10.2014.
 */
public class DeviceInfoMainContentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device_info_main_content, null);
    }
}