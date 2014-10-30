package com.beautyteam.smartkettle;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
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
import com.beautyteam.smartkettle.Fragments.SettingsFragment;
import com.beautyteam.smartkettle.Instruments.TweetMaker;
import com.beautyteam.smartkettle.Mechanics.Device;

import java.util.HashMap;

public class MainActivity extends FragmentActivity
                        implements CompoundButton.OnCheckedChangeListener,
                        View.OnClickListener {

    static final String TAG = "myLogs";
    static final String TWEET_MESSAGE = "Офигенное приложение! Разработчикам - любовь!";


    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private SwipeRefreshLayout newsRefreshLayout;
    private SwipeRefreshLayout deviceNewsRefreshLayout;
    private ImageButton actionBarPlusBtn;
    private ImageView actionBarKettle;
    private TextView actionBarTitleView;

    private DrawerLayout drawerLayout; // Главный layout
    private ListView drawerList; // Список в меню слева

    public static String[] screenNames = {"Новости", "Устройства", "Добавить задачу", "Добавить устройство", "Настройки", "Выход"};

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
                    break;
                case 2: // Добавить устройство
                    addAddTaskFragment();
                    break;
                case 3: // Добавить задачу
                    addAddDeviceFragment();
                    break;
                case 4: // Настройки
                    FragmentTransaction fTran = getSupportFragmentManager().beginTransaction();
                    HashMap<String, Boolean> settingToValue = getCheckboxValueMap();

                    fTran.add(R.id.drawer_layout, SettingsFragment.getInstance(settingToValue));
                    fTran.addToBackStack(null);
                    fTran.commit();
                    break;
                case 5: // Выход
                    SharedPreferences sPref = getPreferences(MODE_PRIVATE);
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


    HashMap<String, Boolean> getCheckboxValueMap() {
        HashMap<String, Boolean> valueToName = new HashMap<String, Boolean>();
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        valueToName.put(SettingsFragment.settingsName[0], sPref.getBoolean(SettingsFragment.settingsName[0], true));
        valueToName.put(SettingsFragment.settingsName[1], sPref.getBoolean(SettingsFragment.settingsName[1], true));
        valueToName.put(SettingsFragment.settingsName[2], sPref.getBoolean(SettingsFragment.settingsName[2], true));
        return valueToName;
    }
    @Override // Обработчик на Checkbox в SettingsFragment
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int numberOfCheckbox = 0;
        switch (buttonView.getId()) {
            case R.id.setting1:
                numberOfCheckbox = 0;
                break;
            case R.id.setting2:
                numberOfCheckbox = 1;
                break;
            case R.id.setting3:
                numberOfCheckbox = 2;
                break;
        }
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(SettingsFragment.settingsName[numberOfCheckbox], isChecked);
        editor.commit();
    }

    
    public void refreshNewsList(){
        Toast.makeText(this, "Refreshing news list...", Toast.LENGTH_SHORT).show();
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

    public void refreshDeviceInfo() {
        Toast.makeText(this, "Refreshing details list...", Toast.LENGTH_SHORT).show();
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

    public void removeDevice() {
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