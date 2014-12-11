package com.beautyteam.smartkettle.Fragments;


import android.util.Log;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.beautyteam.smartkettle.LoginActivity;
import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.R;

import java.util.HashMap;

public class AddDeviceFragment extends Fragment implements  View.OnClickListener {
    private MainActivity mCallback;
    private EditText keyEditText;
    private EditText titleEditText;
    private Fragment self;
    private Animation infinityRotate;
    private ImageView loadImage;
    private TextView errorMessage;
    private Button submitButton;
    private Button resetButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_device, null);
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
        keyEditText = (EditText)view.findViewById(R.id.deviceAddKey);
        titleEditText = (EditText)view.findViewById(R.id.deviceAddTitle);
        errorMessage = (TextView)view.findViewById(R.id.deviceAddErrorMessage);
        submitButton = (Button) view.findViewById(R.id.deviceAddButtonsSubmit);
        resetButton = (Button) view.findViewById(R.id.deviceAddButtonsReset);
        loadImage = (ImageView) view.findViewById(R.id.deviceAddLoadImage);

        infinityRotate = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_infinity);
        loadImage.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
        submitButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }



    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).enableActionBarButton();// Отключаем запрет на клики по кнопкам
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deviceAddButtonsSubmit:
                loadImage.setVisibility(View.VISIBLE);
                loadImage.startAnimation(infinityRotate);
                keyEditText.setEnabled(false);
                titleEditText.setEnabled(false);
                submitButton.setEnabled(false);
                resetButton.setEnabled(false);
                HashMap<String, String> params =  new HashMap<String, String>();
                params.put("key", keyEditText.getText().toString());
                params.put("title", titleEditText.getText().toString());
                String message = mCallback.registerDevice(params);
                if (message.equals("success")) {
                    mCallback.removeFragment(self);
                }
                else {
                    loadImage.clearAnimation();
                    loadImage.setVisibility(View.INVISIBLE);
                    errorMessage.setText(message);
                    errorMessage.setVisibility(View.VISIBLE);
                    keyEditText.setEnabled(true);
                    titleEditText.setEnabled(true);
                    submitButton.setEnabled(true);
                    resetButton.setEnabled(true);
                }
                break;
            case R.id.deviceAddButtonsReset:
                keyEditText.setText("");
                titleEditText.setText("");
                break;
        }
    }
}
