package com.beautyteam.smartkettle.Fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beautyteam.smartkettle.Database.DevicesContract;
import com.beautyteam.smartkettle.Database.SmartContentProvider;
import com.beautyteam.smartkettle.Fragments.Adapter.DevicesListCursorAdapter;
import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.R;

public class DevicesFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private MainActivity mCallback;
    private ListView deviceList;

    private static final String[] PROJECTION = new String[] {
            DevicesContract.DevicesEntry._ID,
            DevicesContract.DevicesEntry.COLUMN_NAME_DEVICES_ID,
            DevicesContract.DevicesEntry.COLUMN_NAME_TYPE,
            DevicesContract.DevicesEntry.COLUMN_NAME_TITLE,
            DevicesContract.DevicesEntry.COLUMN_NAME_SUMMARY,
            DevicesContract.DevicesEntry.COLUMN_NAME_DESCRIPTION
    };
    private static final String SORT_ORDER = DevicesContract.DevicesEntry.COLUMN_NAME_TITLE;
    private static final int LOADER_ID = 1;
    private DevicesListCursorAdapter mAdapter;

    public static DevicesFragment getInstance() {
        DevicesFragment devicesFragment = new DevicesFragment();
        Bundle arguments = new Bundle();
        devicesFragment.setArguments(arguments);
        return devicesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID, null, this);
        return inflater.inflate(R.layout.fragment_devices, null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (MainActivity)activity;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        deviceList = (ListView) view.findViewById(R.id.devicesList);
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                mCallback.addDeviceDetailsFragment((Device)adapterView.getItemAtPosition(position));
            }
        });
    }
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), SmartContentProvider.DEVICE_CONTENT_URI, PROJECTION, null, null, SORT_ORDER);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_ID:
                mAdapter = new DevicesListCursorAdapter(getActivity(), cursor, 0);
                deviceList.setAdapter(mAdapter);
                break;
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
