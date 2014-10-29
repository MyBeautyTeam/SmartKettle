package com.beautyteam.smartkettle.Fragments;

/**
 * Created by Admin on 25.10.2014.
 */
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ListView;

import com.beautyteam.smartkettle.Fragments.Adapter.NewsListAdapter;
import com.beautyteam.smartkettle.Instruments.SwipeDetector;
import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.Mechanics.News;
import com.beautyteam.smartkettle.R;

import java.util.ArrayList;
import java.util.Random;

public class NewsFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainActivity mCallback;

    public static NewsFragment getInstance() { // Пока не используется
        NewsFragment newsFragment = new NewsFragment();
        Bundle arguments = new Bundle(); // Пока пусто, возможно нет необходимости в getInstance
        newsFragment.setArguments(arguments);
        return newsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (MainActivity)activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView newsList = (ListView) view.findViewById(R.id.newsList);



        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.newsRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCallback.refreshNewsList();
            }
        });

        Button newsBtn = (Button)LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news_footer, null);
        newsList.addFooterView(newsBtn);
        // =======================
        ArrayList<News> arrayList = new ArrayList<News>();
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:00:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 12:10:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:05:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:10:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:17:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 12:01:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:00:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:00:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:00:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:00:50", R.drawable.ic_drawer));
         // ======================
        newsList.setAdapter(new NewsListAdapter(getActivity(), arrayList));
    }

}
