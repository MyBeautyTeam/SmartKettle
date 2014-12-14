package com.beautyteam.smartkettle.ServiceWork;

import android.content.Intent;
import android.content.Context;
import android.os.ResultReceiver;

import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.LoginActivity;

import static com.beautyteam.smartkettle.LoginActivity.*;
import static com.beautyteam.smartkettle.MainActivity.*;
import static com.beautyteam.smartkettle.ServiceWork.JsonParser.*;

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
        intentService.setAction(ACTION_LOGIN);
        intentService.putExtra(LOGIN, login);
        intentService.putExtra(PASS, pass);
        intentService.putExtra(RECEIVER, receiver);
        context.startService(intentService);
    }

    public void register(String login, String pass, ResultReceiver receiver ) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(ACTION_REGISTER);
        intentService.putExtra(LOGIN, login);
        intentService.putExtra(PASS, pass);
        intentService.putExtra(RECEIVER, receiver);
        context.startService(intentService);
    }

    public void addingDevice(int owner, int idDevice, String deviceTitle,ResultReceiver receiver) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(ACTION_ADDING_DEVICE);
        intentService.putExtra(OWNER, owner);
        intentService.putExtra(ID_DEVICE, idDevice);
        intentService.putExtra(DEVICE_TITLE, deviceTitle);
        intentService.putExtra(RECEIVER, receiver);
        context.startService(intentService);
    }

    public void removeDevice(int owner, int idDevice, ResultReceiver receiver) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(ACTION_REMOVE_DEVICE);
        intentService.putExtra(OWNER, owner);
        intentService.putExtra(ID_DEVICE, idDevice);
        intentService.putExtra(RECEIVER, receiver);
        context.startService(intentService);
    }

    public void addingEvents(int owner, int idDevice, String eventDateBegin, int temperature, ResultReceiver receiver) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(ACTION_ADDING_EVENTS);
        intentService.putExtra(OWNER, owner);
        intentService.putExtra(ID_DEVICE, idDevice);
        intentService.putExtra(EVENT_DATE_BEGIN, eventDateBegin);
        intentService.putExtra(TEMPERATURE, temperature);
        intentService.putExtra(RECEIVER, receiver);
        context.startService(intentService);
    }

    public void addingMoreEventsInfo(int owner, int idPage) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(ACTION_ADDING_MORE_EVENTS_INFO);
        intentService.putExtra(OWNER, owner);
        intentService.putExtra(ID_PAGE, idPage);
        context.startService(intentService);
    }

    public void endedEvents(int owner, int idDevice, int event) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(ACTION_ENDED_EVENTS);
        intentService.putExtra(OWNER, owner);
        intentService.putExtra(ID_DEVICE, idDevice);
        intentService.putExtra(ID_EVENT, event);
        context.startService(intentService);
    }

    public void addingMoreDevicesInfo(int owner, int idDevice, int page) {
        Intent intentService = new Intent(context, ApiService.class);
        intentService.setAction(ACTION_ADDING_MORE_DEVICES_INFO);
        intentService.putExtra(OWNER,owner);
        intentService.putExtra(ID_DEVICE, idDevice);
        intentService.putExtra(ID_PAGE, page);
        context.startService(intentService);
    }
}
