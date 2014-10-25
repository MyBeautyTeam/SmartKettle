package com.beautyteam.smartkettle;

import android.os.Bundle;


import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

import com.beautyteam.smartkettle.Fragments.Adapter.FragmentPagerAdapter;

public class MainActivity extends FragmentActivity {

    static final String TAG = "myLogs";
    static final int PAGE_COUNT = 10;

    ViewPager pager;
    PagerAdapter pagerAdapter;


    //===================
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    // navigation drawer title
    private CharSequence emptyTitle;
    // used to store app title
    private CharSequence appTitle;

    static private String[] viewsNames = {"artur", "dima", "max"};;
    //===================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appTitle =  getTitle();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, viewsNames));

        // Включает значок и позволяет вести себя как кнопки-переключателя
        getActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.drawable.ic_drawer, //иконка
                R.string.app_name, // описание открытия ???
                R.string.app_name // описание закрытия ???
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(appTitle); // Показать название приложения
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(emptyTitle);
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
            displayView(position);
            pager.setCurrentItem(position, true);
        }
    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        //Toast.makeText(this, "position =" + position, Toast.LENGTH_LONG);

        drawerLayout.closeDrawer(drawerList);
    }


/*  НАДО ЛИ?
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
*/
}