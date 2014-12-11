package com.beautyteam.smartkettle.Fragments.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beautyteam.smartkettle.Database.DevicesContract;
import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.R;

public class DevicesListCursorAdapter extends CursorAdapter {
    private static final String LOG_TAG = "DevicesListCursorAdapterLogs";
    private static final int DEVICE_IMAGE = R.drawable.ic_kettle;
    private static ViewHolder viewHolder;
    private LayoutInflater inflater;

    public DevicesListCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewHolder = new ViewHolder();
    }

    @Override
    public Object getItem(int position) {
        Cursor cursor = getCursor();
        Device device = null;
        if (cursor.moveToPosition(position)) {
            device = new Device(cursor.getString(cursor.getColumnIndex(DevicesContract.DevicesEntry.COLUMN_NAME_TITLE)),
                                cursor.getString(cursor.getColumnIndex(DevicesContract.DevicesEntry.COLUMN_NAME_SUMMARY)),
                                cursor.getString(cursor.getColumnIndex(DevicesContract.DevicesEntry.COLUMN_NAME_DESCRIPTION)),
                                DEVICE_IMAGE,
                                cursor.getInt(cursor.getColumnIndex(DevicesContract.DevicesEntry.COLUMN_NAME_DEVICES_ID)));
        }
        return device;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d(LOG_TAG, "DevicesListCursorAdapter bindView");
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
        viewHolder.image.setImageResource(device.getTypeId());
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d(LOG_TAG, "DevicesListCursorAdapter newView");
        return inflater.from(context).inflate(R.layout.devices_list_item, parent, false);
    }

    private class ViewHolder {
        public TextView name;
        public TextView summary;
        public ImageView image;
    }
}
