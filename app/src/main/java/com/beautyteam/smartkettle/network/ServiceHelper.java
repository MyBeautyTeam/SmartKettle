package com.beautyteam.smartkettle.network;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.Context;

import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.LoginActivity;
import com.beautyteam.smartkettle.network.ApiService;

/**
 * Created by asus on 06.11.2014.
 */
public class ServiceHelper {
    public Context context;

    public ServiceHelper(Context context) {
        this.context = context;
    }

    public void serviceStarter(Intent intent) {
        Intent intentService = new Intent(context, ApiService.class);
        String action = intent.getAction();
        intentService.setAction(action);
        if (action.equals(JsonParser.ACTION_LOGIN)||action.equals(JsonParser.ACTION_REGISTER)) {
            intentService.putExtra(LoginActivity.LOGIN,intent.getStringExtra(LoginActivity.LOGIN));
            intentService.putExtra(LoginActivity.PASS, intent.getStringExtra(LoginActivity.PASS));

        } else if (action.equals(JsonParser.ACTION_ADDING_DEVICE)) {
            intentService.putExtra(MainActivity.OWNER, intent.getIntExtra(MainActivity.OWNER, 0));
            intentService.putExtra(MainActivity.ID_DEVICE,intent.getIntExtra(MainActivity.ID_DEVICE, 0));
            intentService.putExtra(MainActivity.NAME_DEVICE, intent.getStringExtra(MainActivity.NAME_DEVICE));

        } else if (action.equals(JsonParser.ACTION_REMOVE_DEVICE)) {
            intentService.putExtra(MainActivity.OWNER, intent.getIntExtra(MainActivity.OWNER, 0));
            intentService.putExtra(MainActivity.ID_DEVICE,intent.getIntExtra(MainActivity.ID_DEVICE, 0));

        } else if (action.equals(JsonParser.ACTION_ADDING_EVENTS)) {
            intentService.putExtra(MainActivity.OWNER, intent.getIntExtra(MainActivity.OWNER, 0));
            intentService.putExtra(MainActivity.ID_DEVICE,intent.getIntExtra(MainActivity.ID_DEVICE, 0));
            intentService.putExtra(MainActivity.EVENT_DATE_BEGIN,intent.getStringExtra(MainActivity.EVENT_DATE_BEGIN));
            intentService.putExtra(MainActivity.TEMPERATURE, intent.getIntExtra(MainActivity.TEMPERATURE, 0));

        } else if (action.equals(JsonParser.ACTION_ADDING_MORE_EVENTS_INFO)) {
            intentService.putExtra(MainActivity.OWNER, intent.getIntExtra(MainActivity.OWNER, 0));
            intentService.putExtra(MainActivity.ID_PAGE, intent.getIntExtra(MainActivity.ID_PAGE, 0));

        } else if (action.equals(JsonParser.ACTION_ENDED_EVENTS)) {
            intentService.putExtra(MainActivity.OWNER, intent.getIntExtra(MainActivity.OWNER, 0));
            intentService.putExtra(MainActivity.ID_DEVICE,intent.getIntExtra(MainActivity.ID_DEVICE, 0));
            intentService.putExtra(MainActivity.ID_EVENT, intent.getIntExtra(MainActivity.ID_EVENT, 0));

        } else if (action.equals(JsonParser.ACTION_ADDING_MORE_DEVICES_INFO)) {
            intentService.putExtra(MainActivity.OWNER, intent.getIntExtra(MainActivity.OWNER, 0));
            intentService.putExtra(MainActivity.ID_DEVICE,intent.getIntExtra(MainActivity.ID_DEVICE, 0));
            intentService.putExtra(MainActivity.ID_PAGE, intent.getIntExtra(MainActivity.ID_PAGE, 0));

        }
        context.startService(intentService);
    }


}
