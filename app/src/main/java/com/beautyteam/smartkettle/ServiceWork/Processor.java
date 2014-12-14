package com.beautyteam.smartkettle.ServiceWork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.beautyteam.smartkettle.LoginActivity.*;
import static com.beautyteam.smartkettle.MainActivity.*;
import static com.beautyteam.smartkettle.ServiceWork.JsonParser.*;
import static com.beautyteam.smartkettle.ServiceWork.JsonParser.ACTION_ADDING_MORE_DEVICES_INFO;
import static com.beautyteam.smartkettle.ServiceWork.JsonParser.ACTION_ADDING_MORE_EVENTS_INFO;

/**
 * Created by asus on 29.10.2014.
 */
public class Processor {
    private String url = "http://beautyteam.cloudapp.net/";
    private Context context;

    public Processor(Context _context) {
        context = _context;
    }

    public void request(Intent intent, Network network) throws JSONException, IOException {
        JSONObject urlparametres;
        int idOwner;
        int idEvent;
        int idDevice;
        int idPage;
        JSONObject json = null;
        String action = "";
        urlparametres = new JSONObject();
        if (intent != null) {
            action = intent.getAction();
            if (ACTION_LOGIN.equals(action) || ACTION_REGISTER.equals(action)) {
                String login = intent.getStringExtra(LOGIN);
                String password = intent.getStringExtra(PASS);
                if (ACTION_LOGIN.equals(action))
                    url += "api/owners/login/";
                else
                    url += "api/owners/register/";
                urlparametres.put("username", login);
                urlparametres.put("password", password);
                json = new JSONObject(network.urlConnectionPost(url, urlparametres.toString()));

            } else if (ACTION_ADDING_DEVICE.equals(action)) {
                idOwner = intent.getIntExtra(OWNER, 0);
                idDevice = intent.getIntExtra(ID_DEVICE, 0);
                String title = intent.getStringExtra(DEVICE_TITLE);
                url += "api/devices/add/";
                urlparametres.put("owner", idOwner);
                urlparametres.put("title", title);
                urlparametres.put("device", idDevice);
                json = new JSONObject(network.urlConnectionPost(url, urlparametres.toString()));

            } else if (ACTION_REMOVE_DEVICE.equals(action)) {
                idOwner = intent.getIntExtra(OWNER, 0);
                idDevice = intent.getIntExtra(ID_DEVICE, 0);
                url += "api/devices/remove/?owner=" + idOwner + "&device=" + idDevice;
                json = new JSONObject(network.urlConnectionGet(url));

            } else if (ACTION_ADDING_EVENTS.equals(action)) {
                idOwner = intent.getIntExtra(OWNER, 0);
                idDevice = intent.getIntExtra(ID_DEVICE, 0);
                String eventDateBegin = intent.getStringExtra(EVENT_DATE_BEGIN);
                int temperature = intent.getIntExtra(TEMPERATURE, 0);
                url += "api/events/add/";
                urlparametres.put("owner", idOwner);
                urlparametres.put("device", idDevice);
                urlparametres.put("event_date_begin", eventDateBegin);
                urlparametres.put("temperature", temperature);
                json = new JSONObject(network.urlConnectionPost(url, urlparametres.toString()));

            } else if (ACTION_ADDING_MORE_EVENTS_INFO.equals(action)) {
                idOwner = intent.getIntExtra(OWNER, 0);
                idPage = intent.getIntExtra(ID_PAGE, 0);
                url += "api/events/more/?owner=" + idOwner + "&page=" + idPage;
                json = new JSONObject(network.urlConnectionGet(url));

            } else if (ACTION_ENDED_EVENTS.equals(action)) {
                idOwner = intent.getIntExtra(OWNER, 0);
                idDevice = intent.getIntExtra(ID_DEVICE, 0);
                idEvent = intent.getIntExtra(ID_EVENT, 0);
                url += "api/events/ended/?owner=" + idOwner + "&device=" + idDevice + "&event=" + idEvent;
                json = new JSONObject(network.urlConnectionGet(url));

            } else if (ACTION_ADDING_MORE_DEVICES_INFO.equals(action)) {
                idOwner = intent.getIntExtra(OWNER, 0);
                idDevice = intent.getIntExtra(ID_DEVICE, 0);
                idPage = intent.getIntExtra(ID_PAGE, 0);
                url += "api/devices/about/more/?owner=" + idOwner + "&device=" + idDevice + "&page=" + idPage;
                json = new JSONObject(network.urlConnectionGet(url));
            }
        }
        if (sendReceiver((ResultReceiver) intent.getParcelableExtra(RECEIVER), json)) {
            JsonParser jsonParser = new JsonParser(context);
            jsonParser.jsonToContentProvider(action, json);
        }
    }

    public boolean sendReceiver(ResultReceiver receiver, JSONObject json) throws JSONException {
        int idOwner;
        final Bundle data = new Bundle();
        if (!json.toString().contains("error")) {
            if(json.toString().contains("owner_key")) {
                idOwner = json.getInt("owner_key");
                data.putInt("RECEIVER_DATA", idOwner);
            }
            receiver.send(1, data);
            Log.d("json", "id to activity");
            return true;
        }else {
            data.putString("ERROR",json.get("error").toString());
            receiver.send(STATUS_ERROR, data);
        }
        return false;
    }
}