package com.beautyteam.smartkettle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.os.Handler;

import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.network.ApiService;
import com.beautyteam.smartkettle.network.JsonParser;
import com.beautyteam.smartkettle.network.Processor;
import com.beautyteam.smartkettle.network.ServiceHelper;

import java.util.ArrayList;

/**
 * Created by Admin on 28.10.2014.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    public final static String LOGIN = "LOGIN";
    public final static String PASS = "PASS";
    public final static String LOGIN_PREF = "LOGIN_PREF";
    EditText loginEditText;
    EditText passEditText;
    TextView errorMessage;
    Button okBtn;
    ImageView loadImage;
    private final String LOG = "LogService";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginEditText = (EditText)findViewById(R.id.loginEditLoginAct);
        passEditText = (EditText)findViewById(R.id.passEditLoginAct);
        errorMessage = (TextView)findViewById(R.id.errorMessageLoginAct);
        okBtn = (Button)findViewById(R.id.okBtnLoginAct);
        loadImage = (ImageView)findViewById(R.id.loadImageLoginAct);

        Animation infinityRotate = AnimationUtils.loadAnimation(this, R.anim.rotate_infinity);
        loadImage.startAnimation(infinityRotate);

        okBtn.setOnClickListener(this);
        errorMessage.setVisibility(View.INVISIBLE);

        SharedPreferences sPref = getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
        if (sPref.getString(LOGIN, null) == null) { // Проверяем, есть ли данные о логине и пароле
            loadImage.setVisibility(View.INVISIBLE);
        } else {
            // Если данные есть - пробуем подключится
            loginEditText.setText(sPref.getString(LOGIN, ""));
            passEditText.setText(sPref.getString(PASS, ""));
            onClick(null); // Вынести в отдельную функцию, когда все станет по-уму

            /*loginEditText.setEnabled(false);
            passEditText.setEnabled(false);
            okBtn.setEnabled(false);
            loadImage.setVisibility(View.VISIBLE);

            loginEditText.setText(sPref.getString(LOGIN, ""));
            passEditText.setText(sPref.getString(PASS, ""));*/

            // TODO - отправить интент на авторизацию
            //============================ DEBUG
            //============================
        }

    }

    @Override
    public void onClick(View view) {
        loadImage.setVisibility(View.VISIBLE);
        loginEditText.setEnabled(false);
        passEditText.setEnabled(false);
        okBtn.setEnabled(false);
        //===========================Service
        Intent intent = new Intent(LoginActivity.this, ServiceHelper.class);
        intent.setAction(JsonParser.ACTION_LOGIN);
        intent.putExtra(LOGIN, loginEditText.getText().toString());
        intent.putExtra(PASS, passEditText.getText().toString());
        ServiceHelper serviceHelper = new ServiceHelper(LoginActivity.this);
        serviceHelper.serviceStarter(intent);

        //============================ DEBUG
        // SharedPref оставить. Проверка, возможно, в onReceive
        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loginEditText.getText().toString().equals("login")&&
                        (passEditText.getText().toString().equals("pass"))) {
                    SharedPreferences sPref = getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sPref.edit();
                    editor.putString(LoginActivity.LOGIN, loginEditText.getText().toString());
                    editor.putString(LoginActivity.PASS, passEditText.getText().toString());
                    editor.commit();
                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(loginIntent);
                    LoginActivity.this.finish();
                } else {
                    loadImage.setVisibility(View.INVISIBLE);
                    errorMessage.setVisibility(View.VISIBLE);
                    loginEditText.setEnabled(true);
                    passEditText.setEnabled(true);
                    okBtn.setEnabled(true);
                }
            }
        }, 4000);
        //============================
    */
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG, "onDestroy");
        super.onDestroy();
    }

   /* public class Receiver extends BroadcastReceiver {
        public static final String ACTION = "login.received";

        @Override
        public void onReceive(Context context, Intent intent) {
            int idOwner = 0;
            ArrayList<Device> deviceArray = new ArrayList<Device>();
            if (intent.getStringExtra(JsonParser.EXTRA_ERROR).isEmpty() ) {
                idOwner = intent.getIntExtra(JsonParser.EXTRA_ID_OWNER, 0);
                deviceArray = intent.getParcelableArrayListExtra(JsonParser.EXTRA_DEVICE);
                //ArrayList newsArray = intent.getStringArrayListExtra(ApiService.EXTRA_NEWS);
                Device device = deviceArray.get(0);
                Log.d(LOG, "id="+idOwner);
                Log.d(LOG, "long="+deviceArray.get(0).getDescription());
                SharedPreferences sPref = getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = sPref.edit();
                editor.putString(LoginActivity.LOGIN, loginEditText.getText().toString());
                editor.putString(LoginActivity.PASS, passEditText.getText().toString());
                editor.commit();
                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(loginIntent);
                LoginActivity.this.finish();
            }
            else {
                loadImage.setVisibility(View.INVISIBLE);
                errorMessage.setVisibility(View.VISIBLE);
                loginEditText.setEnabled(true);
                passEditText.setEnabled(true);
                okBtn.setEnabled(true);

            }

        }
    }*/

}