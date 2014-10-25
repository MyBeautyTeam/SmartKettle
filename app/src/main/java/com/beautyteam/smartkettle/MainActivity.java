package com.beautyteam.smartkettle;

import android.content.SharedPreferences;
import android.os.Bundle;


import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.view.View;

import com.beautyteam.smartkettle.Fragments.Adapter.FragmentPagerAdapter;
import com.beautyteam.smartkettle.Fragments.SettingsFragment;

import java.util.HashMap;
import java.util.Set;

public class MainActivity extends FragmentActivity
                        implements CompoundButton.OnCheckedChangeListener {

    static final String TAG = "myLogs";


    ViewPager pager;
    PagerAdapter pagerAdapter;


    private DrawerLayout drawerLayout; // Главный layout
    private ListView drawerList; // Список в меню слева
    private ActionBarDrawerToggle drawerToggle; // Переключатель

    private CharSequence appTitle; // Заголовок приложения

    public static String[] screenNames = {"Новости", "Устройства", "Заголовок", "Настройки"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appTitle =  getTitle();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, screenNames));

        // Включает значок и позволяет вести себя как кнопки-переключателя
        getActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.drawable.ic_launcher, //иконка
                R.string.app_name, // описание открытия ???
                R.string.app_name // описание закрытия ???
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(appTitle); // Показать название приложения
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(appTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
        }

        drawerList.setOnItemClickListener(new DrawerItemClickListener());


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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(
                AdapterView<?> parent, View view, int position, long id
        ) {
            // display view for selected nav drawer item
            drawerLayout.closeDrawer(drawerList);

            switch (position) {
                case 0:
                case 1:
                case 2:
                    pager.setCurrentItem(position, true);
                    break;
                case 3:
                    FragmentTransaction fTran = getSupportFragmentManager().beginTransaction();
                    HashMap<String, Boolean> settingToValue = getCheckboxValueMap();
                    fTran.add(R.id.drawer_layout, SettingsFragment.getInstance(settingToValue));
                    fTran.addToBackStack(null);
                    fTran.commit();
            }
        }
    }


/*  НАДО ЛИ? */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

}