package com.beautyteam.smartkettle;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beautyteam.smartkettle.Fragments.Adapter.FragmentPagerAdapter;
import com.beautyteam.smartkettle.Fragments.AddDeviceFragment;
import com.beautyteam.smartkettle.Fragments.AddTaskFragment;
import com.beautyteam.smartkettle.Fragments.DeviceInfoFragment;
import com.beautyteam.smartkettle.Fragments.EmptyFragment;
import com.beautyteam.smartkettle.Fragments.NewsFragment;
import com.beautyteam.smartkettle.Fragments.SettingsFragment;
import com.beautyteam.smartkettle.Instruments.TweetMaker;
import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.ServiceWork.ServiceHelper;

import com.google.android.gcm.GCMRegistrar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends FragmentActivity
                        implements
                        View.OnClickListener {

    static final String TAG = "myLogs";
    static final String TWEET_MESSAGE = "Офигенное приложение! Разработчикам - любовь!";
    public static final String OWNER = "OWNER";
    public static final String ID_DEVICE = "ID_DEVICE";
    public static final String EVENT_DATE_BEGIN = "EVENT_DATE_BEGIN";
    public static final String TEMPERATURE = "TEMPERATURE";
    public static final String DEVICE_TITLE = "DEVICE_TITLE";
    public static final String ID_PAGE = "ID_PAGE";
    public static final String ID_EVENT = "ID_EVENT";
    private ServiceHelper serviceHelper = new ServiceHelper(MainActivity.this);
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private SwipeRefreshLayout newsRefreshLayout;
    private SwipeRefreshLayout deviceNewsRefreshLayout;
    private ImageButton actionBarPlusBtn;
    private ImageView actionBarKettle;
    private TextView actionBarTitleView;

    private int idOwner;

    private DrawerLayout drawerLayout; // Главный layout
    private ListView drawerList; // Список в меню слева

    public static String[] screenNames = {"Новости", "Устройства", "Добавить задачу", "Добавить устройство", "Настройки", "Выход"};

    private CharSequence appTitle; // Заголовок приложения
    public BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ================== Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, screenNames));


        //===================ActionBar
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);


        View actionBarView = mInflater.inflate(R.layout.action_bar, null);
        actionBarTitleView = (TextView) actionBarView.findViewById(R.id.actionBarTitleText);
        actionBarTitleView.setText(R.string.app_name);

        actionBarPlusBtn = (ImageButton) actionBarView.findViewById(R.id.actionBarPlusBtn);
        actionBarKettle = (ImageView) actionBarView.findViewById(R.id.actionBarImage);

        actionBarPlusBtn.setOnClickListener(this);
        actionBarTitleView.setOnClickListener(this);
        actionBarKettle.setOnClickListener(this);

        actionBar.setCustomView(actionBarView);
        actionBar.setDisplayShowCustomEnabled(true);

        drawerList.setOnItemClickListener(new DrawerItemClickListener());


        // =============== PAGER
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, position = " + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.actionBarPlusBtn:
                FragmentTransaction fTran = getSupportFragmentManager().beginTransaction();
                fTran.replace(R.id.drawer_layout, new AddTaskFragment());
                fTran.addToBackStack(null);
                fTran.commit();
                break;
            case R.id.actionBarTitleText:
                TweetMaker tweetMaker = new TweetMaker(this, TWEET_MESSAGE);
                tweetMaker.submit();
                break;
            case R.id.actionBarImage:
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.drawer_layout, new AddDeviceFragment());
                fTrans.addToBackStack(null);
                fTrans.commit();
                break;
        }
    }

    public String registerDevice(HashMap params) {
        if (params.get("key").equals("") || params.get("title").equals("")) {
            return "Пустые значения!";
        }
        else {
            idOwner = getSharedPreferences(LoginActivity.LOGIN_PREF, MODE_PRIVATE).getInt(LoginActivity.ID_OWNER,0);
            int id = Integer.parseInt(params.get("key").toString());
            serviceHelper.addingDevice(idOwner, id, params.get("title").toString());
        }
        return "success";
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction fTran = getSupportFragmentManager().beginTransaction();
        fTran.remove(fragment);
        fTran.commit();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(
                AdapterView<?> parent, View view, int position, long id
        ) {
            // display view for selected nav drawer item
            drawerLayout.closeDrawer(drawerList);

            switch (position) {
                case 0: // История
                case 1: // Устройства
                    pager.setCurrentItem(position, true);
                    sound();
                    break;
                case 2: // Добавить устройство
                    addAddTaskFragment();
                    sound();
                    break;
                case 3: // Добавить задачу
                    sound();
                    addAddDeviceFragment();
                    break;
                case 4: // Настройки
                    sound();
                    Intent mainIntent = new Intent(MainActivity.this, SettingsActivity.class);
                    MainActivity.this.startActivity(mainIntent);

                    break;
                case 5: // Выход
                    sound();
                    SharedPreferences sPref = getSharedPreferences(LoginActivity.LOGIN_PREF, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sPref.edit();
                    editor.putString(LoginActivity.LOGIN, null);
                    editor.putString(LoginActivity.PASS, null);
                    editor.commit();
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(loginIntent);
                    MainActivity.this.finish();
                    break;
            }
        }
    }

    public void sound() {
        MediaPlayer mpSound;
        mpSound = MediaPlayer.create(MainActivity.this, R.raw.sound);
        mpSound.start();
    }

    public void refreshNewsList(){
        Toast.makeText(this, "Refreshing news list...", Toast.LENGTH_SHORT).show();
        //idOwner = getSharedPreferences(LoginActivity.LOGIN_PREF, MODE_PRIVATE).getInt(LoginActivity.ID_OWNER,0);
        //serviceHelper.addingMoreEventsInfo(idOwner,1);
        if (newsRefreshLayout == null) newsRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.newsRefreshLayout);
        newsRefreshLayout.setRefreshing(true);
        final Activity thisActivity = this;
        newsRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                newsRefreshLayout.setRefreshing(false);
                Toast.makeText(thisActivity, "Refreshed", Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    public void refreshDeviceInfo(int idDevice) {
        Toast.makeText(this, "Refreshing details list...", Toast.LENGTH_SHORT).show();
        //idOwner = getSharedPreferences(LoginActivity.LOGIN_PREF, MODE_PRIVATE).getInt(LoginActivity.ID_OWNER,0);
        //serviceHelper.addingMoreDevicesInfo(idOwner,1,idDevice);
        if (deviceNewsRefreshLayout == null) deviceNewsRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.deviceInfoRefreshLayout);
        deviceNewsRefreshLayout.setRefreshing(true);
        final Activity thisActivity = this;
        deviceNewsRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                deviceNewsRefreshLayout.setRefreshing(false);
                Toast.makeText(thisActivity, "Refreshed", Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }



    public void addDeviceDetailsFragment(Device device) {
        FragmentTransaction fTran = getSupportFragmentManager().beginTransaction();
        fTran.replace(R.id.drawer_layout, DeviceInfoFragment.getInstance(device));
        fTran.addToBackStack(null);
        fTran.commit();
    }

    public void removeDevice(int id) {
        idOwner = getSharedPreferences(LoginActivity.LOGIN_PREF, MODE_PRIVATE).getInt(LoginActivity.ID_OWNER,0);
        serviceHelper.removeDevice(idOwner,id);
        Toast.makeText(this, "Device will be removed", Toast.LENGTH_LONG).show();
    }

    public void disableActionBarButton() {
        actionBarPlusBtn.setEnabled(false);
        actionBarKettle.setEnabled(false);
    }

    public void enableActionBarButton() {
        actionBarPlusBtn.setEnabled(true);
        actionBarKettle.setEnabled(true);
    }

    public void addTask(Date date, String deviceName) {
        idOwner = getSharedPreferences(LoginActivity.LOGIN_PREF, MODE_PRIVATE).getInt(LoginActivity.ID_OWNER,0);
        String stringDate = new SimpleDateFormat("d MMMM yyyy HH:mm:ss", Locale.ENGLISH).format(date);
        int temperature = 100;
        int id =1;
        serviceHelper.addingEvents(idOwner,id, stringDate, temperature);
        Toast.makeText(this, "task wil be added", Toast.LENGTH_LONG).show();
    }

    private void addAddTaskFragment() {
        FragmentTransaction fTran = getSupportFragmentManager().beginTransaction();
        fTran.replace(R.id.drawer_layout, new AddTaskFragment());
        fTran.addToBackStack(null);
        fTran.commit();
    }

    private void addAddDeviceFragment() {
        FragmentTransaction fTran = getSupportFragmentManager().beginTransaction();
        fTran.replace(R.id.drawer_layout, new AddDeviceFragment());
        fTran.addToBackStack(null);
        fTran.commit();
    }

}