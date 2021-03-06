package com.beautyteam.smartkettle.Fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;


import com.beautyteam.smartkettle.Database.DevicesContract;
import com.beautyteam.smartkettle.Database.SmartContentProvider;
import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.R;


import java.util.Date;

/**
 * Created by Admin on 30.10.2014.
 */


public class AddTaskFragment extends Fragment implements  View.OnClickListener{
    private LinearLayout linearLayout;
    private Spinner spinner;
    private String spinnerSelected;
    private MainActivity mCallback;
    private Fragment self;
    private TimePicker timePicker;

    // Проверка на существование
    private static final String[] DEVICE_PROJECTION = {DevicesContract.DevicesEntry._ID, DevicesContract.DevicesEntry.COLUMN_NAME_TITLE };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_task, null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (MainActivity) activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ((MainActivity)getActivity()).lockDrawer();
        super.onViewCreated(view, savedInstanceState);

        self = this;
        ((MainActivity)getActivity()).invisibleActionBarButton(); // Чтобы нельзя было породить много слоев фрагментов

        linearLayout = (LinearLayout)view.findViewById(R.id.addTaskLayout);
        Button okBtn = (Button) linearLayout.findViewById(R.id.addTaskOkBtn);

        okBtn.setOnClickListener(this);
        spinner =(Spinner) linearLayout.findViewById(R.id.addTaskSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Cursor cursor = getActivity().getContentResolver().query(
                SmartContentProvider.DEVICE_CONTENT_URI,
                DEVICE_PROJECTION,
                null,
                null,
                null
        );
        if (cursor.getCount() > 0){
            String[] from = new String[]{ DevicesContract.DevicesEntry.COLUMN_NAME_TITLE };
            int[] to = new int[]{ android.R.id.text1 };
            SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item,
                    cursor, from, to);
            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(mAdapter);
        }
        timePicker = (TimePicker) linearLayout.findViewById(R.id.addTaskTime);
        timePicker.setIs24HourView(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).visiableActionBarButton();// Отключаем запрет на клики по кнопкам
        ((MainActivity)getActivity()).unLockDrawer();
    }

    @Override
    public void onClick(View v) {   // spinnerselected - это название устройства или id? должна ли быть температура?
        switch (v.getId()) {
            case R.id.addTaskOkBtn:
                DatePicker datePicker = (DatePicker) linearLayout.findViewById(R.id.addTaskDate);
                Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                mCallback.addTask(date, spinnerSelected);
                break;
        }
    }
}
