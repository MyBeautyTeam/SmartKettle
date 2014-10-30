package com.beautyteam.smartkettle.network;

import android.content.Intent;

import com.beautyteam.smartkettle.LoginActivity;
import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.Mechanics.News;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by asus on 29.10.2014.
 */
public class ApiHelper {
    private String url;
    private JSONObject urlparametres;
    private JSONObject json;
    private String longDescription;
    private String shortDescription;
    private String title;
    private int type;
    private int id;
    private int idBegin;
    private int idEnd;
    private String eventDate;
    private String eventDateBegin;
    private String eventDateEnd;
    private String longDescriptionEnd;
    private String shortDescriptionEnd;

    public JSONObject request(Intent intent, Network network) throws JSONException, IOException {
       urlparametres = new JSONObject();
        if (intent != null) {
            String action = intent.getAction();
            if (action == "LOGIN") {
                String login = intent.getStringExtra(LoginActivity.LOGIN);
                String password = intent.getStringExtra(LoginActivity.PASS);
                url = "api/owners/login/";
                urlparametres.put("username",login);
                urlparametres.put("password",password);
                json = new JSONObject(network.urlConnectionPost(url,urlparametres.toString()));
            }
            if (action == "REGISTER") {
                String login = intent.getStringExtra(LoginActivity.LOGIN);
                String password = intent.getStringExtra(LoginActivity.PASS);
                url = "api/owners/register/";
                urlparametres.put("username",login);
                urlparametres.put("password",password);
                json = new JSONObject(network.urlConnectionPost(url,urlparametres.toString()));
            }
            if (action == "ADDING_DEVICES") {
                int idOwner = intent.getIntExtra(MainActivity.OWNER, 0);
                int idDevice = intent.getIntExtra(MainActivity.ID_DEVICE, 0);
                String nameDevice = intent.getStringExtra(MainActivity.NAME_DEVICE);
                url = "api/devices/add";
                urlparametres.put("owner", idOwner);
                urlparametres.put("device", idDevice);
                urlparametres.put("title", nameDevice);
                json = new JSONObject(network.urlConnectionPost(url,urlparametres.toString()));

            }
            if (action == "REMOVE_DEVICE") {
                int idOwner = intent.getIntExtra(MainActivity.OWNER, 0);
                int idDevice = intent.getIntExtra(MainActivity.ID_DEVICE, 0);
                url = "api/devices/remove/owner=" + idOwner + "&&device=" + idDevice;

            }
            if (action == "ADDING_EVENTS") {
                int idOwner = intent.getIntExtra(MainActivity.OWNER, 0);
                int idDevice = intent.getIntExtra(MainActivity.ID_DEVICE, 0);
                String eventDateBegin = intent.getStringExtra(MainActivity.EVENT_DATE_BEGIN);
                int temperature = intent.getIntExtra(MainActivity.TEMPERATURE, 0);
                url = "api/events/add";
                urlparametres.put("owner", idOwner);
                urlparametres.put("device", idDevice);
                urlparametres.put("event_date_begin", eventDateBegin);
                urlparametres.put("temperature",temperature);
                json = new JSONObject(network.urlConnectionPost(url,urlparametres.toString()));
            }
            if (action == "ADDING_MORE_EVENTS_INFO") {
                int idOwner = intent.getIntExtra(MainActivity.OWNER, 0);
                int idPage = intent.getIntExtra(MainActivity.ID_PAGE, 0);
                url = "api/events/more/owner=" + idOwner + "&&page=" + idPage;
                json = new JSONObject(network.urlConnectionGet(url));
            }
            if (action == "ENDED_EVENTS") {
                int idOwner = intent.getIntExtra(MainActivity.OWNER, 0);
                int idDevice = intent.getIntExtra(MainActivity.ID_DEVICE, 0);
                int idEvent = intent.getIntExtra(MainActivity.ID_EVENT, 0);
                url = "api/events/ended/owner=" + idOwner + "&&device=" + idDevice + "&&event=" + idEvent;
                json = new JSONObject(network.urlConnectionGet(url));
            }
            if (action == "ADDING_MORE_DEVICES_INFO") {
                int idOwner = intent.getIntExtra(MainActivity.OWNER, 0);
                int idDevice = intent.getIntExtra(MainActivity.ID_DEVICE, 0);
                int idPage = intent.getIntExtra(MainActivity.ID_PAGE, 0);
                url = "api/devices/about/more/owner=" + idOwner + "&&device=" + idDevice + "&&page=" + idPage;
                json = new JSONObject(network.urlConnectionGet(url));
            }
        }
    return json;
    }

    public ArrayList newsParser(JSONObject json) throws JSONException {
        ArrayList newsArray = new ArrayList();
        for (int i = 0; i< json.length(); i++ ) {
            JSONObject begin = json.getJSONObject("begin");
            JSONObject end = json.getJSONObject("end");
            for (int j = 0; j < begin.length(); j++) {
                longDescription = begin.getJSONObject("j").getString("long_news");
                shortDescription = begin.getJSONObject("j").getString("short_news");
                idBegin = begin.getJSONObject("j").getInt("id");
                eventDate = begin.getJSONObject("j").getString("event_date");
                eventDateBegin = begin.getJSONObject("j").getString("event_date_begin");
                longDescriptionEnd = end.getJSONObject("j").getString("long_news");
                shortDescriptionEnd = end.getJSONObject("j").getString("short_news");
                idEnd = end.getJSONObject("j").getInt("id");
                eventDateEnd = end.getJSONObject("j").getString("event_date_ens");
            }
        }
        return newsArray;
    }

    public ArrayList deviceParser(JSONObject json) {
        ArrayList<Device> deviceArray = new ArrayList<Device>();
        for (int i = 0; i< json.length(); i++ ) {
            try {
                longDescription = json.getJSONObject("i").getString("description");
                shortDescription = json.getJSONObject("i").getString("summary");
                id = json.getJSONObject("i").getInt("id");
                title = json.getJSONObject("i").getString("title");
                type = json.getJSONObject("i").getInt("type");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Device device = new Device(title, shortDescription, longDescription, type, id);
            deviceArray.set(i, device);
        }
        return deviceArray;
    }
}
