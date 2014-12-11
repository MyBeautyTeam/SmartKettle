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
 * Created by asus on 11.12.2014.
 */
public class EmptyFragment extends Fragment{
        private LinearLayout linearLayout;
        private Spinner spinner;
        private String spinnerSelected;
        private MainActivity mCallback;
        private Fragment self;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_empty, null);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            mCallback = (MainActivity) activity;
        }

        @Override
        public void onPause() {
            super.onPause();
            ((MainActivity)getActivity()).enableActionBarButton();// Отключаем запрет на клики по кнопкам
        }
}
