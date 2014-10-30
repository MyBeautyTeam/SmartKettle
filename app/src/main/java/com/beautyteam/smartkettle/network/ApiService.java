package com.beautyteam.smartkettle.network;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.beautyteam.smartkettle.LoginActivity;
import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.Mechanics.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ApiService extends IntentService {

    public static final String ACTION_LOGIN = "com.beautyteam.smartkettle.action.LOGIN";
    private static final String ACTION_BAZ = "com.beautyteam.smartkettle.action.BAZ";
    public static final String EXTRA_DEVICE = "com.beautyteam.smartkettle.extra.DEVICE";
    public static final String EXTRA_NEWS = "com.beautyteam.smartkettle.extra.NEWS";
    public static final String EXTRA_ID_OWNER = "com.beautyteam.smartkettle.extra.ID_OWNER";
    public static final String EXTRA_ERROR = "com.beautyteam.smartkettle.extra.ERROR";
    private String action;
    private JSONObject json;
    private JSONObject history;
    private Network network;
    private ArrayList news;
    private ArrayList<Device> device;
    private int idOwner;

    public ApiService() {
        super("MyIntentService");
    }

    public void onCreate() {
        super.onCreate();
        network = new Network();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        action = intent.getAction();
        ApiHelper apiHelper = new ApiHelper();
        try {
            json = apiHelper.request(intent, network);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intentResponse = new Intent();
        intentResponse.setAction(action);
        intentResponse.addCategory(Intent.CATEGORY_DEFAULT);

        if (action.equals("LOGIN")) {
            try {
                if (json.getString("error") != null) {
                    idOwner = json.getJSONObject("i").getInt("owner_key");
                    history = json.getJSONObject("i").getJSONObject("history");
                    //news = new ArrayList( apiHelper.newsParser(history));
                    device =  apiHelper.deviceParser(json);
                    intentResponse.putExtra(EXTRA_ID_OWNER, idOwner);
                    intentResponse.putExtra(EXTRA_DEVICE, device);
                    //intentResponse.putExtra(EXTRA_PARAM2, news);
                }
                //else
                    //intentResponse.putExtra(EXTRA_ERROR,)
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (action.equals("REGISTER")) {

        }
        if (action.equals("ADDING_DEVICES")) {


        }
        if (action .equals("REMOVE_DEVICE")) {

        }
        if (action.equals("ADDING_EVENTS")) {

        }
        if (action.equals("ADDING_MORE_EVENTS_INFO")) {
            try {
                if (json.getString("error") == null) {
                    news = apiHelper.newsParser(json);
                    intentResponse.putExtra(EXTRA_NEWS, news);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (action.equals("ENDED_EVENTS")) {

        }
        if (action.equals("ADDING_MORE_DEVICES_INFO")) {

        }
    sendBroadcast(intentResponse);
    }

}