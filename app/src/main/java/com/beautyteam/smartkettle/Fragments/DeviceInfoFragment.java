package com.beautyteam.smartkettle.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beautyteam.smartkettle.Fragments.Adapter.NewsListAdapter;
import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.Mechanics.News;
import com.beautyteam.smartkettle.R;

import java.util.ArrayList;

/**
 * Created by Admin on 26.10.2014.
 */
public class DeviceInfoFragment extends Fragment {
    private final static String NAME = "name";
    private final static String DESCRIPTION = "description";
    private final static String IMAGE = "image";

    MainActivity mCallback;
    private SwipeRefreshLayout swipeRefreshLayout;

    private TextView name;
    private TextView description;
    private ImageView image;

    public static DeviceInfoFragment getInstance(Device device) { // Пока не используется
        DeviceInfoFragment deviceInfoFragment = new DeviceInfoFragment();
        Bundle arguments = new Bundle();
        arguments.putString(NAME, device.getName());
        arguments.putString(DESCRIPTION, device.getLongDescription());
        arguments.putInt(IMAGE, device.getImageId());
        deviceInfoFragment.setArguments(arguments);
        return deviceInfoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device_info, null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (MainActivity)activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView deviceInfoList = (ListView) view.findViewById(R.id.deviceInfoList);

        name = (TextView)view.findViewById(R.id.deviceInfoName);
        description = (TextView)view.findViewById(R.id.deviceInfoDescript);
        image = (ImageView)view.findViewById(R.id.deviceInfoImage);

        name.setText(getArguments().getString(NAME));
        description.setText(getArguments().getString(DESCRIPTION));
        image.setImageResource(getArguments().getInt(IMAGE));


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.deviceInfoRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCallback.refreshDeviceInfo();
            }
        });
        ArrayList<News> arrayList = new ArrayList<News>();
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:05:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:10:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:17:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 12:01:50", R.drawable.ic_drawer));

        Button newsBtn = (Button)LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news_footer, null);
        deviceInfoList.addFooterView(newsBtn);
        deviceInfoList.setAdapter(new NewsListAdapter(getActivity(), arrayList));
    }

}
