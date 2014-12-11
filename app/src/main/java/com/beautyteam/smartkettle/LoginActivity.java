package com.beautyteam.smartkettle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beautyteam.smartkettle.ServiceWork.AppResultsReceiver;
import com.beautyteam.smartkettle.ServiceWork.JsonParser;
import com.beautyteam.smartkettle.ServiceWork.ServiceHelper;

/**
 * Created by Admin on 28.10.2014.
 */
public class LoginActivity extends Activity implements View.OnClickListener, AppResultsReceiver.Receiver {
    public final static String LOGIN = "LOGIN";
    public final static String PASS = "PASS";
    public final static String ID_OWNER = "OWNER";
    public final static String LOGIN_PREF = "LOGIN_PREF";
    public final static int STATUS_FINISHED = 1;
    public final static int STATUS_ERROR = 0;
    public final static String RECEIVER_DATA = "RECEIVER_DATA";
    public final static String RECEIVER = "RECEIVER";
    private final String LOG = "LogService";

    private EditText loginEditText;
    private EditText passEditText;
    private TextView errorMessage;
    private Button okBtn;
    private ImageView loadImage;
    private Animation infinityRotate;
    private AppResultsReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().hide();

        loginEditText = (EditText)findViewById(R.id.loginEditLoginAct);
        passEditText = (EditText)findViewById(R.id.passEditLoginAct);
        errorMessage = (TextView)findViewById(R.id.errorMessageLoginAct);
        okBtn = (Button)findViewById(R.id.okBtnLoginAct);
        loadImage = (ImageView)findViewById(R.id.loadImageLoginAct);


        infinityRotate = AnimationUtils.loadAnimation(this, R.anim.rotate_infinity);
        loadImage.setVisibility(View.INVISIBLE);


        okBtn.setOnClickListener(this);
        errorMessage.setVisibility(View.INVISIBLE);

        SharedPreferences sPref = getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
        if (sPref.getString(LOGIN, null) == null) { // Проверяем, есть ли данные о логине и пароле
            loadImage.setVisibility(View.INVISIBLE);
        } else {
            // Если данные есть - пробуем подключится
            loginEditText.setText(sPref.getString(LOGIN, ""));
            passEditText.setText(sPref.getString(PASS, ""));
            loadImage.setVisibility(View.VISIBLE);
            loadImage.startAnimation(infinityRotate);
            onClick(null); // Вынести в отдельную функцию, когда все станет по-уму
        }

    }

    @Override
    public void onClick(View view) {
        loadImage.setVisibility(View.VISIBLE);
        loadImage.startAnimation(infinityRotate);
        loginEditText.setEnabled(false);
        passEditText.setEnabled(false);
        okBtn.setEnabled(false);
        //===========================Service
        mReceiver = new AppResultsReceiver(new Handler());
        mReceiver.setReceiver(this);
        ServiceHelper serviceHelper = new ServiceHelper(LoginActivity.this);
        serviceHelper.login(loginEditText.getText().toString(),passEditText.getText().toString(), mReceiver);
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new AppResultsReceiver(new Handler());
        mReceiver.setReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mReceiver.setReceiver(null);
    }


    @Override
    public void onReceiveResult(int resultCode, Bundle data) {
        Log.d(LOG, "onReceiver");
        switch (resultCode) {
            case STATUS_FINISHED :
                Log.d(LOG, "id in receiver");
                int idOwner = data.getInt(RECEIVER_DATA);
                SharedPreferences sPref = getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = sPref.edit();
                editor.putString(LoginActivity.LOGIN, loginEditText.getText().toString());
                editor.putString(LoginActivity.PASS, passEditText.getText().toString());
                editor.putInt(LoginActivity.ID_OWNER, idOwner);
                editor.commit();
                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                //loginIntent.putExtra(MainActivity.OWNER,idOwner);
                LoginActivity.this.startActivity(loginIntent);
                LoginActivity.this.finish();
                break;
            case STATUS_ERROR :
                String errorString = data.getString("ERROR");
                loadImage.clearAnimation();
                loadImage.setVisibility(View.INVISIBLE);
                Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();
                loginEditText.setEnabled(true);
                passEditText.setEnabled(true);
                okBtn.setEnabled(true);
                break;
        }
    }
}