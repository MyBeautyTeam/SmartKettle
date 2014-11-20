package com.beautyteam.smartkettle.ServiceWork;

import android.content.Intent;
import android.content.Context;
import android.os.ResultReceiver;

import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.LoginActivity;

/**
 * Created by asus on 06.11.2014.
 */
public class ServiceHelper {
    public Context context;

    public ServiceHelper(Context context) {
        this.context = context;
    }

    public void login(String login, String pass, ResultReceiver receiver ) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(JsonParser.ACTION_LOGIN);
        intentService.putExtra(LoginActivity.LOGIN, login);
        intentService.putExtra(LoginActivity.PASS, pass);
        intentService.putExtra(LoginActivity.RECEIVER, receiver);
        context.startService(intentService);
    }

    public void register(String login, String pass, ResultReceiver receiver ) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(JsonParser.ACTION_REGISTER);
        intentService.putExtra(LoginActivity.LOGIN, login);
        intentService.putExtra(LoginActivity.PASS, pass);
        intentService.putExtra(LoginActivity.RECEIVER, receiver);
        context.startService(intentService);
    }

    public void addingDevice(int owner, int idDevice, String deviceTitle) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(JsonParser.ACTION_ADDING_DEVICE);
        intentService.putExtra(MainActivity.OWNER, owner);
        intentService.putExtra(MainActivity.ID_DEVICE, idDevice);
        intentService.putExtra(MainActivity.DEVICE_TITLE, deviceTitle);
        context.startService(intentService);
    }

    public void removeDevice(int owner, int idDevice) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(JsonParser.ACTION_REMOVE_DEVICE);
        intentService.putExtra(MainActivity.OWNER, owner);
        intentService.putExtra(MainActivity.ID_DEVICE, idDevice);
        context.startService(intentService);
    }

    public void addingEvents(int owner, int idDevice, String eventDateBegin, String temperature) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(JsonParser.ACTION_ADDING_EVENTS);
        intentService.putExtra(MainActivity.OWNER, owner);
        intentService.putExtra(MainActivity.ID_DEVICE, idDevice);
        intentService.putExtra(MainActivity.EVENT_DATE_BEGIN, eventDateBegin);
        intentService.putExtra(MainActivity.TEMPERATURE, temperature);
        context.startService(intentService);
    }

    public void addingMoreEventsInfo(int owner, int idPage) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(JsonParser.ACTION_ADDING_MORE_EVENTS_INFO);
        intentService.putExtra(MainActivity.OWNER, owner);
        intentService.putExtra(MainActivity.ID_PAGE, idPage);
        context.startService(intentService);
    }

    public void endedEvents(int owner, int idDevice, int event) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(JsonParser.ACTION_ENDED_EVENTS);
        intentService.putExtra(MainActivity.OWNER, owner);
        intentService.putExtra(MainActivity.ID_DEVICE, idDevice);
        intentService.putExtra(MainActivity.ID_EVENT, event);
        context.startService(intentService);
    }

    public void addingMoreDevicesInfo(int owner, int idDevice, int page) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(JsonParser.ACTION_ADDING_MORE_DEVICES_INFO);
        intentService.putExtra(MainActivity.OWNER,owner);
        intentService.putExtra(MainActivity.ID_DEVICE, idDevice);
        intentService.putExtra(MainActivity.ID_PAGE, page);
        context.startService(intentService);
    }
}
