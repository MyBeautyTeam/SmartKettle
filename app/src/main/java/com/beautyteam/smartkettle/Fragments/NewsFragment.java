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
import android.widget.Adapter;
import android.widget.BaseAdapter;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
        final ListView newsList = (ListView) view.findViewById(R.id.newsList);



        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.newsRefreshLayout);

        Button newsBtn = (Button)LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news_footer, null);
        newsList.addFooterView(newsBtn);
        // =======================
        ArrayList<News> arrayList = new ArrayList<News>();
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:00:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "26 October 2014, 12:10:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "22 October 2014, 13:05:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:10:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:17:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 12:01:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "30 October 2014, 13:00:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "30 October 2014, 16:00:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:00:50", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "30 October 2014, 18:20:50", R.drawable.ic_drawer));
         // ======================
        Collections.sort(arrayList, new Comparator<News>() {
            @Override
            public int compare(News news1, News news2) {
                if(news1.getDateLong() > (news2.getDateLong())) {
                    return -1;
                } else {
                    if (news1.getDateLong() == (news2.getDateLong())) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            }
        });

        newsList.setAdapter(new NewsListAdapter(getActivity(), arrayList));
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCallback.refreshNewsList();
                //((BaseAdapter)newsList.getAdapter()).notifyDataSetChanged();
            }
        });
    }

}
