package com.beautyteam.smartkettle.network;

import android.content.Intent;

import com.beautyteam.smartkettle.LoginActivity;
import com.beautyteam.smartkettle.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by asus on 29.10.2014.
 */
public class Processor {
    private String url = "http://191.238.100.176/";
    private JSONObject urlparametres;
    private JSONObject json = null;
    String action = "";

    public void request(Intent intent, Network network) throws JSONException, IOException {
        urlparametres = new JSONObject();
        if (intent != null) {
            action = intent.getAction();
            if (JsonParser.ACTION_LOGIN.equals(action)) {
                String login = intent.getStringExtra(LoginActivity.LOGIN);
                String password = intent.getStringExtra(LoginActivity.PASS);
                url += "api/owners/login/";
                urlparametres.put("username", login);
                urlparametres.put("password", password);
                json = new JSONObject(network.urlConnectionPost(url, urlparametres.toString()));
            } else if (JsonParser.ACTION_REGISTER.equals(action)) {
                String login = intent.getStringExtra(LoginActivity.LOGIN);
                String password = intent.getStringExtra(LoginActivity.PASS);
                url += "api/owners/register/";
                urlparametres.put("username", login);
                urlparametres.put("password", password);
                json = new JSONObject(network.urlConnectionPost(url, urlparametres.toString()));

            } else if (JsonParser.ACTION_ADDING_DEVICE.equals(action)) {
                int idOwner = intent.getIntExtra(MainActivity.OWNER, 0);
                int idDevice = intent.getIntExtra(MainActivity.ID_DEVICE, 0);
                String nameDevice = intent.getStringExtra(MainActivity.NAME_DEVICE);
                url += "api/devices/add";
                urlparametres.put("owner", idOwner);
                urlparametres.put("device", idDevice);
                urlparametres.put("title", nameDevice);
                json = new JSONObject(network.urlConnectionPost(url, urlparametres.toString()));
            } else if (JsonParser.ACTION_REMOVE_DEVICE.equals(action)) {
                int idOwner = intent.getIntExtra(MainActivity.OWNER, 0);
                int idDevice = intent.getIntExtra(MainActivity.ID_DEVICE, 0);
                url += "api/devices/remove/owner=" + idOwner + "&&device=" + idDevice;
            } else if (JsonParser.ACTION_ADDING_EVENTS.equals(action)) {
                int idOwner = intent.getIntExtra(MainActivity.OWNER, 0);
                int idDevice = intent.getIntExtra(MainActivity.ID_DEVICE, 0);
                String eventDateBegin = intent.getStringExtra(MainActivity.EVENT_DATE_BEGIN);
                int temperature = intent.getIntExtra(MainActivity.TEMPERATURE, 0);
                url += "api/events/add";
                urlparametres.put("owner", idOwner);
                urlparametres.put("device", idDevice);
                urlparametres.put("event_date_begin", eventDateBegin);
                urlparametres.put("temperature", temperature);
                json = new JSONObject(network.urlConnectionPost(url, urlparametres.toString()));
            } else if (JsonParser.ACTION_ADDING_MORE_EVENTS_INFO.equals(action)) {
                int idOwner = intent.getIntExtra(MainActivity.OWNER, 0);
                int idPage = intent.getIntExtra(MainActivity.ID_PAGE, 0);
                url += "api/events/more/owner=" + idOwner + "&&page=" + idPage;
                json = new JSONObject(network.urlConnectionGet(url));
            } else if (JsonParser.ACTION_ENDED_EVENTS.equals(action)) {
                int idOwner = intent.getIntExtra(MainActivity.OWNER, 0);
                int idDevice = intent.getIntExtra(MainActivity.ID_DEVICE, 0);
                int idEvent = intent.getIntExtra(MainActivity.ID_EVENT, 0);
                url += "api/events/ended/owner=" + idOwner + "&&device=" + idDevice + "&&event=" + idEvent;
                json = new JSONObject(network.urlConnectionGet(url));
            } else if (JsonParser.ACTION_ADDING_MORE_DEVICES_INFO.equals(action)) {
                int idOwner = intent.getIntExtra(MainActivity.OWNER, 0);
                int idDevice = intent.getIntExtra(MainActivity.ID_DEVICE, 0);
                int idPage = intent.getIntExtra(MainActivity.ID_PAGE, 0);
                url += "api/devices/about/more/owner=" + idOwner + "&&device=" + idDevice + "&&page=" + idPage;
                json = new JSONObject(network.urlConnectionGet(url));
            }
        }
        JsonParser jsonParser = new JsonParser();
        jsonParser.jsonToContentProvider(action, json);
    }

}