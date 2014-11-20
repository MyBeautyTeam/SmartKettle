package com.beautyteam.smartkettle.ServiceWork;

import android.content.ContentValues;
import android.util.Log;

import com.beautyteam.smartkettle.LoginActivity;
import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.Mechanics.News;

import org.json.JSONException;
import org.json.JSONObject;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by asus on 10.11.2014.
 */
public class JsonParser {
    public static final String ACTION_LOGIN = "com.beautyteam.smartkettle.action.LOGIN";
    public static final String ACTION_REGISTER = "com.beautyteam.smartkettle.action.REGISTER";
    public static final String ACTION_ADDING_DEVICE = "com.beautyteam.smartkettle.action.ADDING_DEVICE";
    public static final String ACTION_REMOVE_DEVICE = "com.beautyteam.smartkettle.action.REMOVE_DEVICE";
    public static final String ACTION_ADDING_EVENTS= "com.beautyteam.smartkettle.action.ADDING_EVENTS";
    public static final String ACTION_ADDING_MORE_EVENTS_INFO = "com.beautyteam.smartkettle.action.ADDING_MORE_EVENTS_INFO";
    public static final String ACTION_ENDED_EVENTS = "com.beautyteam.smartkettle.action.ENDED_EVENTS";
    public static final String ACTION_ADDING_MORE_DEVICES_INFO= "com.beautyteam.smartkettle.action.ADDING_MORE_DEVICES_INFO";
    public static final String EXTRA_DEVICE = "com.beautyteam.smartkettle.extra.DEVICE";
    public static final String EXTRA_NEWS = "com.beautyteam.smartkettle.extra.NEWS";
    public static final String EXTRA_ID_OWNER = "com.beautyteam.smartkettle.extra.ID_OWNER";
    public static final String EXTRA_ERROR = "com.beautyteam.smartkettle.extra.ERROR";
    private ArrayList<News> newsBegin;
    private ArrayList<News> newsEnd;
    private ArrayList<News> historyEnd;
    private ArrayList<News> historyBegin;
    private ArrayList<Device> device;
    private JSONObject news;
    private JSONObject devices;
    private JSONObject history;

    public ArrayList newsParser(JSONObject json) throws JSONException {
        ArrayList<News> newsArray = new ArrayList<News>();
        for (int i = 0; i< json.length(); i++ ) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Log.d("json", json.getJSONObject(String.valueOf(i)).toString());
                newsArray.add(i, mapper.readValue(json.getJSONObject(String.valueOf(i)).toString(), News.class));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newsArray;
    }

    public ArrayList deviceParser(JSONObject json) {
        ArrayList<Device> deviceArray = new ArrayList<Device>();
        for (int i = 0; i< json.length(); i++ ) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Log.d("json", json.getJSONObject(String.valueOf(i)).toString());
                deviceArray.add(i, mapper.readValue(json.getJSONObject(String.valueOf(i)).toString(), Device.class));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return deviceArray;
    }
    
    public void sendingForDevice(JSONObject json, String action) throws JSONException {
        device =  deviceParser(json);  //parsing arraylist of devices without history
        ContentValues deviceValues = new ContentValues();
        for (int i = 0; i < device.size(); i++) {
            deviceValues.put("description", device.get(i).getDescription());
            deviceValues.put("type", device.get(i).getTypeId());
            deviceValues.put("id", device.get(i).getId());
            deviceValues.put("summary", device.get(i).getSummary());
            deviceValues.put("title", device.get(i).getTitle());
            toContentProvider(deviceValues, action); ///contentProvider
            deviceValues.clear();
        }
    }

    public void sendingForNewsEnd(JSONObject history, String action) throws JSONException {
        JSONObject jsonHistoryEnd = history.getJSONObject("end");
        historyEnd = newsParser(jsonHistoryEnd);
        ContentValues historyEndValues = new ContentValues();
        for (int i = 0; i < historyEnd.size(); i++) {
            historyEndValues.put("event_date_end", historyEnd.get(i).getEvent_date_end());
            historyEndValues.put("long_news", historyEnd.get(i).getLong_news());
            historyEndValues.put("id", historyEnd.get(i).getImageId());
            historyEndValues.put("short_news", historyEnd.get(i).getShort_news());
            toContentProvider(historyEndValues, action);
            historyEndValues.clear();
        }
    }

    public void sendingForNewsBegin(JSONObject history, String action) throws JSONException {
        JSONObject jsonHistoryBegin = history.getJSONObject("begin");
        historyBegin = newsParser(jsonHistoryBegin);
        ContentValues historyBeginValues = new ContentValues();
        for (int i = 0; i < historyBegin.size(); i++) {
            historyBeginValues.put("event_date", historyBegin.get(i).getEvent_date());
            historyBeginValues.put("event_date_begin", historyBegin.get(i).getEvent_date_begin());
            historyBeginValues.put("long_news", historyBegin.get(i).getLong_news());
            historyBeginValues.put("id", historyBegin.get(i).getImageId());
            historyBeginValues.put("short_news", historyBegin.get(i).getShort_news());
            toContentProvider(historyBeginValues, action);
            historyBeginValues.clear();
        }
    }

    public void jsonToContentProvider(String action, JSONObject json) throws JSONException {
        if (action.equals(ACTION_LOGIN)) {
            try {
                if (!json.toString().contains("error")) {
                    devices = json.getJSONObject("devices");
                    sendingForDevice(devices, action);
                    news = json.getJSONObject("news");
                    sendingForNewsBegin(news, action);
                    sendingForNewsEnd(news, action);
                    for (int i = 0; i < devices.length(); i++) {
                        history = devices.getJSONObject(String.valueOf(i)).getJSONObject("history");
                        sendingForNewsBegin(history, action);
                        sendingForNewsEnd(history, action);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (action.equals(ACTION_REGISTER)) {

        } else if (action.equals(ACTION_ADDING_DEVICE)) {
            if (!json.toString().contains("error")) {
                //devices = json.getJSONObject("devices");
                sendingForDevice(json, action);
                //sendingForNews(json.getJSONObject("news"), action);
                /*for (int i = 0; i < devices.length(); i++) {
                    history = devices.getJSONObject(String.valueOf(i)).getJSONObject("history");
                    sendingForNews(history, action);
                }*/
            }
            else {
                    Log.d("error"," error add device");
            }
        } else if (action.equals(ACTION_REMOVE_DEVICE)) {
            if (!json.toString().contains("error")) {
                  int num = json.getInt("success");
                  ContentValues contentValues = new ContentValues();
                    contentValues.put("success", num);
            }
        } else if (action.equals(ACTION_ADDING_EVENTS)) {
            if (!json.toString().contains("error")) {
                news = json.getJSONObject("news");
                sendingForNewsBegin(news,action);
                int idDevice = news.getJSONObject("devices").getInt("device_id");
                history = json.getJSONObject("history");
                sendingForNewsBegin(history, action);
            }

        } else if (action.equals(ACTION_ADDING_MORE_EVENTS_INFO)) {
            if (!json.toString().contains("error")) {
                history = json.getJSONObject("history");
                sendingForNewsBegin(history, action);
                sendingForNewsEnd(history, action);
            }

        } else if (action.equals(ACTION_ENDED_EVENTS)) {

        } else if (action.equals(ACTION_ADDING_MORE_DEVICES_INFO)) {
            if (!json.toString().contains("error")) {
                history = json.getJSONObject("history");
                sendingForNewsBegin(history, action);
                sendingForNewsEnd(history, action);
            }
        }
    }

     public void toContentProvider(ContentValues values, String action) {
        Log.d("Values", values.toString());
    }
}
