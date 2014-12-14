package com.beautyteam.smartkettle.Fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beautyteam.smartkettle.Database.NewsContract;
import com.beautyteam.smartkettle.Database.SmartContentProvider;
import com.beautyteam.smartkettle.Fragments.Adapter.NewsListCursorAdapter;
import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.R;
import com.beautyteam.smartkettle.Views.ButtonView;
import com.beautyteam.smartkettle.Views.DeviceInfoView;
import com.beautyteam.smartkettle.Views.OnDoubleClickListener;


public class DeviceInfoFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private final static String NAME = "name";
    private final static String DESCRIPTION = "summary";
    private final static String IMAGE = "image";
    private final static String ID = "id";
    private final static String LANDSCAPE = "landscape";

    MainActivity mCallback;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int id;
    private TextView name;
    private TextView description;
    private ImageView image;
    private DeviceInfoView deviceInfoView;
//    private View mainContentView;
    private ButtonView removeBtn;
//    private String orientation;
    private ListView deviceInfoList;

    private static final String[] PROJECTION = new String[] {
            NewsContract.NewsEntry._ID,
            NewsContract.NewsEntry.COLUMN_NAME_NEWS_ID,
            NewsContract.NewsEntry.COLUMN_NAME_DEVICE,
            NewsContract.NewsEntry.COLUMN_NAME_SHORT_NEWS,
            NewsContract.NewsEntry.COLUMN_NAME_LONG_NEWS,
            NewsContract.NewsEntry.COLUMN_NAME_EVENT_DATE
    };
    private static final String SELECTION = NewsContract.NewsEntry.COLUMN_NAME_DEVICE + " = ?";
    private static final int LOADER_ID = 2;
    private NewsListCursorAdapter mAdapter;

    public static DeviceInfoFragment getInstance(Device device) { // Пока не используется
        DeviceInfoFragment deviceInfoFragment = new DeviceInfoFragment();
        Bundle arguments = new Bundle();
        arguments.putString(NAME, device.getTitle());
        arguments.putString(DESCRIPTION, device.getDescription());
        arguments.putInt(IMAGE, device.getTypeId());
        arguments.putInt(ID, device.getId());
        deviceInfoFragment.setArguments(arguments);
        return deviceInfoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID, null, this);
        View fragmentView = inflater.inflate(R.layout.fragment_device_info, null);

        deviceInfoView = (DeviceInfoView)fragmentView.findViewById(R.id.deviceInfoBlock);

        LinearLayout content =(LinearLayout) inflater.inflate(R.layout.device_info_view, null);
        removeBtn = (ButtonView) inflater.inflate(R.layout.device_info_button, null);
//*
        removeBtn.setOnDoubleClickListener(new OnDoubleClickListener() {
            @Override
            public void doubleClick() {
                //mCallback.removeDevice(id);
                Toast.makeText(getActivity(), "Device will be removed", Toast.LENGTH_LONG).show();
            }
        });


        deviceInfoView.addView(content);
        deviceInfoView.addView(removeBtn);

        return fragmentView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (MainActivity)activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        deviceInfoList = (ListView) view.findViewById(R.id.deviceInfoList);


        name = (TextView)view.findViewById(R.id.deviceInfoName);
        description = (TextView)view.findViewById(R.id.deviceInfoDescript);
        image = (ImageView)view.findViewById(R.id.deviceInfoImage);
        //mainContentView = view.findViewById(R.id.deviceInfoContent);
        //removeBtn = (Button) view.findViewById(R.id.deviceInfoRemoveBtn);
        id = getArguments().getInt(ID);
        //removeBtn.setOnClickListener(new View.OnClickListener() {
        /*    @Override
            public void onClick(View v) {
                mCallback.removeDevice(id);
                Toast.makeText(getActivity(),"Device will be removed", Toast.LENGTH_LONG).show();
            }
        });
        */

        ((MainActivity) getActivity()).invisibleActionBarButton();// Отключаем клики по кнопкам


        name.setText(getArguments().getString(NAME));
        description.setText(getArguments().getString(DESCRIPTION));
        image.setImageResource(getArguments().getInt(IMAGE));

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.deviceInfoRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCallback.refreshDeviceInfo(id);
            }
        });
//        Button newsBtn = (Button)LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news_footer, null);
//        deviceInfoList.addFooterView(newsBtn);
    }

    private void setRemoveBtnParams(Boolean isVisiable, float weight) {
        if  (isVisiable) {
            removeBtn.setVisibility(View.VISIBLE);
        } else {
            removeBtn.setVisibility(View.INVISIBLE);
        }
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, weight);
        removeBtn.setLayoutParams(param);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).visiableActionBarButton();// Отключаем запрет на клики по кнопкам
        ((MainActivity)getActivity()).unLockDrawer();
        Log.d("FRAGMENT", "info pause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("FRAGMENT", "info Destroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("FRAGMENT", "info resume");
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] selectionArgs = { String.valueOf(getArguments().getInt(ID)) };
        return new CursorLoader(getActivity(), SmartContentProvider.NEWS_CONTENT_URI, PROJECTION, SELECTION, selectionArgs, null); // selection
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_ID:
                mAdapter = new NewsListCursorAdapter(getActivity(), cursor, 0);
                deviceInfoList.setAdapter(mAdapter);
                break;
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
