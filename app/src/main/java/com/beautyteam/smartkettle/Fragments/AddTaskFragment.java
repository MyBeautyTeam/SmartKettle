package com.beautyteam.smartkettle.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.R;

/**
 * Created by Admin on 30.10.2014.
 */
public class AddTaskFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_task, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).disableActionBarButton(); // Чтобы нельзя было породить много слоев фрагментов
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).enableActionBarButton();// Отключаем запрет на клики по кнопкам
    }
}
