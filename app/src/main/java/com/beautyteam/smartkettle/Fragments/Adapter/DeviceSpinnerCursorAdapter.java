package com.beautyteam.smartkettle.Fragments.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.beautyteam.smartkettle.Database.DevicesContract;
import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.R;

class DevicesSpinnerCursorAdapter extends CursorAdapter {
    private static final String LOG_TAG = "DevicesSpinnerCursorAdapterLogs";
    private LayoutInflater inflater;
    private Spinner spinner;

    public DevicesSpinnerCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        Cursor cursor = getCursor();
        if (cursor.moveToPosition(position)) {
            return cursor.getInt(cursor.getColumnIndex(DevicesContract.DevicesEntry.COLUMN_NAME_DEVICES_ID));
        }
        return -1;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d(LOG_TAG, "DevicesListCursorAdapter bindView");
        /*adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) view.findViewById(R.id.spiner_from);
        spinnerFrom.setAdapter(adapterFrom);
        setFrom(getFrom());
        viewHolder.name = (TextView) view.findViewById(R.id.deviceListName);
        viewHolder.summary = (TextView) view.findViewById(R.id.deviceListDescript);
        viewHolder.image = (ImageView) view.findViewById(R.id.deviceListIcon);
        view.setTag(viewHolder);

        Device device = new Device(cursor.getString(cursor.getColumnIndex(DevicesContract.DevicesEntry.COLUMN_NAME_TITLE)),
                cursor.getString(cursor.getColumnIndex(DevicesContract.DevicesEntry.COLUMN_NAME_SUMMARY)),
                cursor.getString(cursor.getColumnIndex(DevicesContract.DevicesEntry.COLUMN_NAME_DESCRIPTION)),
                DEVICE_IMAGE,
                cursor.getInt(cursor.getColumnIndex(DevicesContract.DevicesEntry.COLUMN_NAME_DEVICES_ID)));
        viewHolder.name.setText(device.getTitle());
        viewHolder.summary.setText(device.getSummary());
        viewHolder.image.setImageResource(device.getTypeId());*/
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d(LOG_TAG, "DevicesSpinnerCursorAdapter newView");
        return inflater.from(context).inflate(R.layout.devices_list_item, parent, false);
    }
}