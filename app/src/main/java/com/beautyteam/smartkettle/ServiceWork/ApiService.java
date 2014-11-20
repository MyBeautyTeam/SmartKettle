package com.beautyteam.smartkettle.ServiceWork;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

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
        Processor processor = new Processor();
        try {
            processor.request(intent, network);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}