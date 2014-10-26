package com.beautyteam.smartkettle.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.beautyteam.smartkettle.Fragments.Adapter.DevicesListAdapter;
import com.beautyteam.smartkettle.Fragments.Adapter.NewsListAdapter;
import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.Mechanics.News;
import com.beautyteam.smartkettle.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Admin on 26.10.2014.
 */
public class DevicesFragment extends Fragment {
    public static DevicesFragment getInstance() {
        DevicesFragment devicesFragment = new DevicesFragment();
        Bundle arguments = new Bundle();
        devicesFragment.setArguments(arguments);
        return devicesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_devices, null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView deviceList = (ListView) view.findViewById(R.id.devicesList);

        // =======================
        ArrayList<Device> arrayList = new ArrayList<Device>();
        arrayList.add(new Device("Чайник Tefal", "Прикольный, белый, симпотичный", R.drawable.ic_drawer));
        arrayList.add(new Device("Чайник Tefal", "Прикольный, белый, симпотичный", R.drawable.ic_drawer));
        arrayList.add(new Device("Чайник Tefal", "Прикольный, белый, симпотичный", R.drawable.ic_drawer));
        arrayList.add(new Device("Чайник Tefal", "Прикольный, белый, симпотичный", R.drawable.ic_drawer));
        arrayList.add(new Device("Чайник Samsung", "Большой, толстый, приятный", R.drawable.ic_drawer));
        arrayList.add(new Device("Чайник Samsung", "Большой, толстый, приятный", R.drawable.ic_drawer));
        arrayList.add(new Device("Чайник Samsung", "Большой, толстый, приятный", R.drawable.ic_drawer));
        arrayList.add(new Device("Чайник Samsung", "Большой, толстый, приятный", R.drawable.ic_drawer));
        // ======================
        deviceList.setAdapter(new DevicesListAdapter(getActivity(), arrayList));
    }






}
