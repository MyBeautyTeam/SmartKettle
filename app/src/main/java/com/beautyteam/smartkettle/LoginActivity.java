package com.beautyteam.smartkettle;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import com.google.android.gcm.GCMRegistrar;

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
    public static String SENDER_ID = "1028632665916";

    private EditText loginEditText;
    private EditText passEditText;
    private TextView errorMessage;
    private Button okBtn;
    private ImageView loadImage;
    private Animation infinityRotate;
    private AppResultsReceiver mReceiver;
    Bundle savedInstanceState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
/*после отладки преференссв убрать это
        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(loginIntent);
        LoginActivity.this.finish();
 после отладки преференссв убрать это*/

        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);

        // Достаем идентификатор регистрации
        final String regId = GCMRegistrar.getRegistrationId(this);
        //GCMRegistrar.unregister(getBaseContext());
        if (regId.isEmpty()) { // Если отсутствует, то регистрируемся
            GCMRegistrar.register(this, SENDER_ID);
        } else {
            Log.d("GCM", "Already registered: " + regId);
        }
        // ===============

        loginEditText = (EditText)findViewById(R.id.loginEditLoginAct);
        passEditText = (EditText)findViewById(R.id.passEditLoginAct);
        errorMessage = (TextView)findViewById(R.id.errorMessageLoginAct);
        okBtn = (Button)findViewById(R.id.okBtnLoginAct);
        loadImage = (ImageView)findViewById(R.id.loadImageLoginAct);

        infinityRotate = AnimationUtils.loadAnimation(this, R.anim.rotate_infinity);
        loadImage.setVisibility(View.INVISIBLE);


        okBtn.setOnClickListener(this);
        errorMessage.setVisibility(View.INVISIBLE);

        if (savedInstanceState != null) {
            mReceiver = savedInstanceState.getParcelable(RECEIVER);
            if (!savedInstanceState.getBoolean("loginEditText")) {
                loadImage.setVisibility(View.VISIBLE);
                loadImage.startAnimation(infinityRotate);
                loginEditText.setEnabled(false);
                passEditText.setEnabled(false);
                okBtn.setEnabled(false);
            }
        }
        else {
            mReceiver = new AppResultsReceiver(new Handler());
        }
        mReceiver.setReceiver(this);

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
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RECEIVER, mReceiver);
        outState.putBoolean("loginEditText", loginEditText.isEnabled());
        Log.d("activity", "turn");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {

        loadImage.setVisibility(View.VISIBLE);
        loadImage.startAnimation(infinityRotate);
        loginEditText.setEnabled(false);
        passEditText.setEnabled(false);
        okBtn.setEnabled(false);
        //===========================Service
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