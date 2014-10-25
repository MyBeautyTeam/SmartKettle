package com.beautyteam.smartkettle.Fragments;

/**
 * Created by Admin on 25.10.2014.
 */
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.beautyteam.smartkettle.Fragments.Adapter.NewsListAdapter;
import com.beautyteam.smartkettle.Mechanics.News;
import com.beautyteam.smartkettle.R;

import java.util.ArrayList;
import java.util.Random;

public class NewsFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    NewsListAdapter newsListAdapter;

    int pageNumber;
    int backColor;

    public static NewsFragment getInstance(int page) {
        NewsFragment newsFragment = new NewsFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        newsFragment.setArguments(arguments);
        return newsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*TextView tvPage = (TextView) view.findViewById(R.id.);
        tvPage.setText("Page " + pageNumber);*/


        return inflater.inflate(R.layout.fragment_news, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView newsList = (ListView) view.findViewById(R.id.newsList);
        newsList.setBackgroundColor(backColor);

        Button newsBtn = (Button)LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news_footer, null);
        newsList.addFooterView(newsBtn);
        // =======================
        ArrayList<News> arrayList = new ArrayList<News>();
        arrayList.add(new News("Ваш чайник скипел", "10 минут назад", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник скипел", "10 минут назад", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник скипел", "10 минут назад", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник скипел", "10 минут назад", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник скипел", "10 минут назад", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник скипел", "10 минут назад", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник скипел", "10 минут назад", R.drawable.ic_drawer));
        arrayList.add(new News("Ваш чайник скипел", "10 минут назад", R.drawable.ic_drawer));
         // ======================
        newsList.setAdapter(new NewsListAdapter(getActivity(), arrayList));
    }
}
