package com.beautyteam.smartkettle.ServiceWork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.beautyteam.smartkettle.Database.DevicesContract;
import com.beautyteam.smartkettle.Database.NewsContract;
import com.beautyteam.smartkettle.Database.SmartContentProvider;
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
import java.util.Iterator;

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
    private Context context;

    // Проверка на существование
    private static final String[] DEVICE_PROJECTION = { DevicesContract.DevicesEntry.COLUMN_NAME_DEVICES_ID };
    private static final String DEVICE_SELECTION = String.valueOf(DevicesContract.DevicesEntry.COLUMN_NAME_DEVICES_ID) + " = ?";
    private static final String[] NEWS_PROJECTION = { NewsContract.NewsEntry.COLUMN_NAME_NEWS_ID };
    private static final String NEWS_SELECTION = String.valueOf(NewsContract.NewsEntry.COLUMN_NAME_NEWS_ID) + " = ?";

    public JsonParser(Context _context) {
        context = _context;
    }

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
        ArrayList<Device> device =  deviceParser(json);
        Device dev;
        ContentValues deviceValues = new ContentValues();
        Iterator <Device> itr = device.iterator();
        while(itr.hasNext()) {
            dev = itr.next();
            deviceValues.put(DevicesContract.DevicesEntry.COLUMN_NAME_DESCRIPTION, dev.getDescription());
            deviceValues.put(DevicesContract.DevicesEntry.COLUMN_NAME_TYPE, dev.getTypeId());
            deviceValues.put(DevicesContract.DevicesEntry.COLUMN_NAME_DEVICES_ID, dev.getId());
            deviceValues.put(DevicesContract.DevicesEntry.COLUMN_NAME_SUMMARY, dev.getSummary());
            deviceValues.put(DevicesContract.DevicesEntry.COLUMN_NAME_TITLE, dev.getTitle());

            Cursor cursor = context.getContentResolver().query(
                    SmartContentProvider.DEVICE_CONTENT_URI,
                    DEVICE_PROJECTION,
                    DEVICE_SELECTION,
                    new String[]{String.valueOf(dev.getId())},
                    null
            );
            if (cursor.getCount() == 0) {
                context.getContentResolver().insert(SmartContentProvider.DEVICE_CONTENT_URI, deviceValues);
            }
            cursor.close();
            deviceValues.clear();
        }
    }

    public void sendingForNewsEnd(JSONObject history, String action) throws JSONException {
        ArrayList<News> historyEnd;
        News news;
        JSONObject jsonHistoryEnd = history.getJSONObject("end");
        historyEnd = newsParser(jsonHistoryEnd);
        ContentValues historyEndValues = new ContentValues();
        Iterator <News> itr = historyEnd.iterator();
        while(itr.hasNext()) {
            news = itr.next();
            historyEndValues.put(NewsContract.NewsEntry.COLUMN_NAME_NEWS_ID, news.getId());
            historyEndValues.put(NewsContract.NewsEntry.COLUMN_NAME_DEVICE, news.getDeviceId());
            historyEndValues.put(NewsContract.NewsEntry.COLUMN_NAME_SHORT_NEWS, /*historyEnd.get(i).getShortNews()*/"Ваш чайник вскипел :)");
            historyEndValues.put(NewsContract.NewsEntry.COLUMN_NAME_LONG_NEWS, /*historyEnd.get(i).getLongNews()*/"Ваш чайник вскипел :)");
            historyEndValues.put(NewsContract.NewsEntry.COLUMN_NAME_EVENT_DATE, news.getEventDate());
            Cursor cursor = context.getContentResolver().query(
                    SmartContentProvider.NEWS_CONTENT_URI,
                    NEWS_PROJECTION,
                    NEWS_SELECTION,
                    new String[]{String.valueOf(news.getId())},
                    null
            );
            if (cursor.getCount() == 0) {
                context.getContentResolver().insert(SmartContentProvider.NEWS_CONTENT_URI, historyEndValues);
            }
            cursor.close();
            historyEndValues.clear();
        }
    }

    public void sendingForNewsBegin(JSONObject history, String action) throws JSONException {
        ArrayList<News> historyBegin;
        News news;
        JSONObject jsonHistoryBegin = history.getJSONObject("begin");
        historyBegin = newsParser(jsonHistoryBegin);
        ContentValues historyBeginValues = new ContentValues();
        Iterator <News> itr = historyBegin.iterator();
        while(itr.hasNext()) {
            news = itr.next();
            historyBeginValues.put(NewsContract.NewsEntry.COLUMN_NAME_NEWS_ID, news.getId());
            historyBeginValues.put(NewsContract.NewsEntry.COLUMN_NAME_DEVICE, news.getDeviceId());
            historyBeginValues.put(NewsContract.NewsEntry.COLUMN_NAME_EVENT_DATE_BEGIN, news.getEventDateBegin());
            historyBeginValues.put(NewsContract.NewsEntry.COLUMN_NAME_SHORT_NEWS, /*historyBegin.get(i).getShortNews()*/"Вы поставили чайник :)");
            historyBeginValues.put(NewsContract.NewsEntry.COLUMN_NAME_LONG_NEWS, /*historyBegin.get(i).getLongNews()*/"Вы поставили чайник :)");
            historyBeginValues.put(NewsContract.NewsEntry.COLUMN_NAME_EVENT_DATE, news.getEventDate());
            Cursor cursor = context.getContentResolver().query(
                    SmartContentProvider.NEWS_CONTENT_URI,
                    NEWS_PROJECTION,
                    NEWS_SELECTION,
                    new String[]{String.valueOf(news.getId())},
                    null
            );
            if (cursor.getCount() == 0) {
                context.getContentResolver().insert(SmartContentProvider.NEWS_CONTENT_URI, historyBeginValues);
            }
            cursor.close();
            historyBeginValues.clear();
        }
    }

    public void jsonToContentProvider(String action, JSONObject json) throws JSONException {
        JSONObject news;
        JSONObject devices;
        JSONObject history;
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
            } else {
                Log.d("error", " error add device");
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
                sendingForNewsBegin(news, action);
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
}
