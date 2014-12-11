package com.beautyteam.smartkettle.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.R;

import java.util.HashMap;

/**
 * Created by Admin on 30.10.2014.
 */
public class AddTaskFragment extends Fragment{
    private MainActivity mCallback;
    private Fragment self;
    private Spinner deviceSpiner;
    private Button submitButton;
    private EditText temperatureEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        super.onViewCreated(view, savedInstanceState);

        self = this;
        ((MainActivity)getActivity()).disableActionBarButton(); // Чтобы нельзя было породить много слоев фрагментов
        deviceSpiner = (Spinner)view.findViewById(R.id.taskAddTitle);
        temperatureEditText = (EditText)view.findViewById(R.id.taskAddTemperature);
        submitButton = (Button) view.findViewById(R.id.deviceAddButtonsSubmit);

        //submitButton.setOnClickListener(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).enableActionBarButton();// Отключаем запрет на клики по кнопкам
    }


}
