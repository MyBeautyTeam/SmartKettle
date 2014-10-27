package com.beautyteam.smartkettle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.os.Handler;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEditText = (EditText)findViewById(R.id.loginEditLoginAct);
        passEditText = (EditText)findViewById(R.id.passEditLoginAct);
        errorMessage = (TextView)findViewById(R.id.errorMessageLoginAct);
        okBtn = (Button)findViewById(R.id.okBtnLoginAct);
        loadImage = (ImageView)findViewById(R.id.loadImageLoginAct);

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
        // TODO - отправить интент на авторизацию

        //============================ DEBUG
        // SharedPref оставить. Проверка, возможно, в onReceive
        Handler handler = new Handler();
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
        }, 2000);
        //============================
    }


}