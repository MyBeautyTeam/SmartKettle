package com.beautyteam.smartkettle.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_task, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).disableActionBarButton(); // Чтобы нельзя было породить много слоев фрагментов
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
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).enableActionBarButton();// Отключаем запрет на клики по кнопкам
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (MainActivity) activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addTaskOkBtn:
                DatePicker datePicker = (DatePicker) linearLayout.findViewById(R.id.addTaskDate);
                TimePicker timePicker = (TimePicker) linearLayout.findViewById(R.id.addTaskTime);
                Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                mCallback.addTask(date, spinnerSelected);
                break;
        }
    }
}
