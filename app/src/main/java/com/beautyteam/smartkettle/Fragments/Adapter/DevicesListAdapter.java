package com.beautyteam.smartkettle.Fragments.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.beautyteam.smartkettle.Instruments.SwipeDetector;
import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.R;
import java.util.ArrayList;
/**
 * Created by Admin on 26.10.2014.
 */
public class DevicesListAdapter extends BaseAdapter{
    private ArrayList<Device> devicesArrayList;
    private Context context;
    private LayoutInflater inflater;


    public DevicesListAdapter(Context _context, ArrayList<Device> _devicesArrayList ) {
        context = _context;
        devicesArrayList = _devicesArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return devicesArrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return devicesArrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.from(context).inflate(R.layout.devices_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.deviceListName);
            viewHolder.description = (TextView) convertView.findViewById(R.id.deviceListDescript);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.deviceListIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Device device = (Device)getItem(position);
        viewHolder.name.setText(device.getName());
        viewHolder.description.setText(device.getShortDescription());
        viewHolder.image.setImageResource(device.getImageId());
        return convertView;
    }
    private class ViewHolder {
        public TextView name;
        public TextView description;
        public ImageView image;
    }
}