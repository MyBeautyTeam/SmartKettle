package com.beautyteam.smartkettle.Fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beautyteam.smartkettle.Database.NewsContract;
import com.beautyteam.smartkettle.Database.SmartContentProvider;
import com.beautyteam.smartkettle.Fragments.Adapter.NewsListCursorAdapter;
import com.beautyteam.smartkettle.Instruments.SwipeDetector;
import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.R;

public class DeviceInfoFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private final static String NAME = "name";
    private final static String DESCRIPTION = "summary";
    private final static String IMAGE = "image";
    private final static String ID = "id";
    private final static String LANDSCAPE = "landscape";

    MainActivity mCallback;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int id;
    private TextView name;
    private TextView description;
    private ImageView image;
    private View mainContentView;
    private Button removeBtn;
    private String orientation;
    private ListView deviceInfoList;

    private static final String[] PROJECTION = new String[] {
            NewsContract.NewsEntry._ID,
            NewsContract.NewsEntry.COLUMN_NAME_NEWS_ID,
            NewsContract.NewsEntry.COLUMN_NAME_DEVICE,
            NewsContract.NewsEntry.COLUMN_NAME_SHORT_NEWS,
            NewsContract.NewsEntry.COLUMN_NAME_LONG_NEWS,
            NewsContract.NewsEntry.COLUMN_NAME_EVENT_DATE
    };
    private static final String SELECTION = NewsContract.NewsEntry.COLUMN_NAME_DEVICE + " = ?";
    private static final int LOADER_ID = 2;
    private NewsListCursorAdapter mAdapter;

    public static DeviceInfoFragment getInstance(Device device) { // Пока не используется
        DeviceInfoFragment deviceInfoFragment = new DeviceInfoFragment();
        Bundle arguments = new Bundle();
        arguments.putString(NAME, device.getTitle());
        arguments.putString(DESCRIPTION, device.getDescription());
        arguments.putInt(IMAGE, device.getTypeId());
        arguments.putInt(ID, device.getId());
        deviceInfoFragment.setArguments(arguments);
        return deviceInfoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID, null, this);
        return inflater.inflate(R.layout.fragment_device_info, null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (MainActivity)activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        deviceInfoList = (ListView) view.findViewById(R.id.deviceInfoList);

        if (view.findViewById(R.id.deviceInfoHorisontal)!=null) {
            orientation = "landscape";
        }

        name = (TextView)view.findViewById(R.id.deviceInfoName);
        description = (TextView)view.findViewById(R.id.deviceInfoDescript);
        image = (ImageView)view.findViewById(R.id.deviceInfoImage);
        mainContentView = view.findViewById(R.id.deviceInfoContent);
        removeBtn = (Button) view.findViewById(R.id.deviceInfoRemoveBtn);
        id = getArguments().getInt(ID);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.removeDevice(id);
            }
        });

        ((MainActivity) getActivity()).disableActionBarButton();// Отключаем клики по кнопкам

        final Animation animationSwipeLeft = AnimationUtils.loadAnimation(getActivity(),
                R.anim.swipe_device_info_left);
        final Animation animationSwipeRight = AnimationUtils.loadAnimation(getActivity(),
                R.anim.swipe_device_info_right);
        final Animation animationAppear = AnimationUtils.loadAnimation(getActivity(),R.anim.alpha_from_0_to_1);
        final Animation animationDisappear = AnimationUtils.loadAnimation(getActivity(),R.anim.alpha_from_1_to_0);

        if (LANDSCAPE.equals(orientation)) {
            animationSwipeRight.setDuration(0);
            animationSwipeLeft.setDuration(0);
        }

        animationSwipeLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setRemoveBtnParams(true, 1.1f);
                removeBtn.startAnimation(animationAppear);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationSwipeRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                removeBtn.startAnimation(animationDisappear);
                setRemoveBtnParams(false, 50f);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        /*
        Допольно сложный кусок кода.
        Handler нужен, чтобы через 2 секунды снова разрешить свайп.
        Возможно, стоит перенести обработчик в класс SwipeDetector.
        Теряется гибкость, но логика не нарушается. Хуй.
         */
        final Handler handler = new Handler();
        SwipeDetector swipeDetector = new SwipeDetector() {
            @Override
            public void actionLR() {
            }

            @Override
            public void actionRL() {
                if (getIsHandled()) {
                    setIsHandled(false);
                    getView().startAnimation(animationSwipeLeft);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           getView().startAnimation(animationSwipeRight);

                           setIsHandled(true);
                        }
                    }, 5000);
                }
            }

            @Override
            public void actionTB() {
            }

            @Override
            public void actionBT() {
            }
        };

        mainContentView.setOnTouchListener(swipeDetector);
        mainContentView.setOnClickListener(new View.OnClickListener() { // Почему-то нужен для работы TouchListener'a
            @Override
            public void onClick(View v) {
            }
        });

        name.setText(getArguments().getString(NAME));
        description.setText(getArguments().getString(DESCRIPTION));
        image.setImageResource(getArguments().getInt(IMAGE));

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.deviceInfoRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCallback.refreshDeviceInfo(id);
            }
        });
        Button newsBtn = (Button)LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news_footer, null);
        deviceInfoList.addFooterView(newsBtn);
    }

    private void setRemoveBtnParams(Boolean isVisiable, float weight) {
        if  (isVisiable) {
            removeBtn.setVisibility(View.VISIBLE);
        } else {
            removeBtn.setVisibility(View.INVISIBLE);
        }
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, weight);
        removeBtn.setLayoutParams(param);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).enableActionBarButton();// Отключаем запрет на клики по кнопкам
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] selectionArgs = { String.valueOf(getArguments().getInt(ID)) };
        return new CursorLoader(getActivity(), SmartContentProvider.NEWS_CONTENT_URI, PROJECTION, SELECTION, selectionArgs, null); // selection
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_ID:
                mAdapter = new NewsListCursorAdapter(getActivity(), cursor, 0);
                deviceInfoList.setAdapter(mAdapter);
                break;
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
