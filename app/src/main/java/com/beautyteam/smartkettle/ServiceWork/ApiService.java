package com.beautyteam.smartkettle.ServiceWork;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.beautyteam.smartkettle.LoginActivity;
import com.beautyteam.smartkettle.Mechanics.Device;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class ApiService extends IntentService {
    private String action;
    private Network network;
    private ArrayList news;
    private ArrayList<Device> device;
    private int idOwner;

    public ApiService() {
        super("MyIntentService");
    }

    public void onCreate() {
        super.onCreate();
        Log.d("service","oncreate service");
        network = new Network();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Processor processor = new Processor(getBaseContext());
        try {
            processor.request(intent, network);
        } catch (JSONException e) {
            e.printStackTrace();
            ResultReceiver receiver = intent.getParcelableExtra(LoginActivity.RECEIVER);
            final Bundle data = new Bundle();
            data.putString("ERROR","Невозможно подключиться к интернету");
            receiver.send(LoginActivity.STATUS_ERROR, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}