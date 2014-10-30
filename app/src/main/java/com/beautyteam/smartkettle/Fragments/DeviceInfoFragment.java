package com.beautyteam.smartkettle.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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

import com.beautyteam.smartkettle.Fragments.Adapter.NewsListAdapter;
import com.beautyteam.smartkettle.Instruments.SwipeDetector;
import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.Mechanics.Device;
import com.beautyteam.smartkettle.Mechanics.News;
import com.beautyteam.smartkettle.R;

import java.util.ArrayList;

/**
 * Created by Admin on 26.10.2014.
 */
public class DeviceInfoFragment extends Fragment {
    private final static String NAME = "name";
    private final static String DESCRIPTION = "description";
    private final static String IMAGE = "image";

    MainActivity mCallback;
    private SwipeRefreshLayout swipeRefreshLayout;

    private TextView name;
    private TextView description;
    private ImageView image;
    private View mainContentView;
    private Button removeBtn;

    public static DeviceInfoFragment getInstance(Device device) { // Пока не используется
        DeviceInfoFragment deviceInfoFragment = new DeviceInfoFragment();
        Bundle arguments = new Bundle();
        arguments.putString(NAME, device.getName());
        arguments.putString(DESCRIPTION, device.getLongDescription());
        arguments.putInt(IMAGE, device.getImageId());
        deviceInfoFragment.setArguments(arguments);
        return deviceInfoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        ListView deviceInfoList = (ListView) view.findViewById(R.id.deviceInfoList);

        name = (TextView)view.findViewById(R.id.deviceInfoName);
        description = (TextView)view.findViewById(R.id.deviceInfoDescript);
        image = (ImageView)view.findViewById(R.id.deviceInfoImage);
        mainContentView = view.findViewById(R.id.deviceInfoContent);
        removeBtn = (Button) view.findViewById(R.id.deviceInfoRemoveBtn);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.removeDevice();
            }
        });

        ((MainActivity) getActivity()).disableActionBarButton();// Отключаем клики по кнопкам

        final Animation animationSwipeLeft = AnimationUtils.loadAnimation(getActivity(),
                R.anim.swipe_device_info_left);
        final Animation animationSwipeRight = AnimationUtils.loadAnimation(getActivity(),
                R.anim.swipe_device_info_right);
        final Animation animationApear = AnimationUtils.loadAnimation(getActivity(),R.anim.alpha_from_0_to_1);
        final Animation animationDispear = AnimationUtils.loadAnimation(getActivity(),R.anim.alpha_from_1_to_0);

        animationSwipeLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setRemoveBtnParams(true, 1.1f);
                removeBtn.startAnimation(animationApear);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationSwipeRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                removeBtn.startAnimation(animationDispear);
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
        Hander нужен, чтобы через 2 секунды снова разрешить свайп.
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
                mCallback.refreshDeviceInfo();
            }
        });
        ArrayList<News> arrayList = new ArrayList<News>();
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:05:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:10:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:17:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 12:01:50", R.drawable.ic_drawer));

        Button newsBtn = (Button)LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news_footer, null);
        deviceInfoList.addFooterView(newsBtn);
        deviceInfoList.setAdapter(new NewsListAdapter(getActivity(), arrayList));
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

}
