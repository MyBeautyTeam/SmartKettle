package com.beautyteam.smartkettle.Fragments;

/**
 * Created by Admin on 25.10.2014.
 */
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ListView;

import com.beautyteam.smartkettle.Database.NewsContract;
import com.beautyteam.smartkettle.Database.SmartContentProvider;
import com.beautyteam.smartkettle.Fragments.Adapter.NewsListAdapter;
import com.beautyteam.smartkettle.Fragments.Adapter.NewsListCursorAdapter;
import com.beautyteam.smartkettle.Instruments.SwipeDetector;
import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.Mechanics.News;
import com.beautyteam.smartkettle.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class NewsFragment extends Fragment  implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainActivity mCallback;

    private static final String[] PROJECTION = new String[] {
            NewsContract.NewsEntry._ID,
            NewsContract.NewsEntry.COLUMN_NAME_NEWS_ID,
            NewsContract.NewsEntry.COLUMN_NAME_SHORT_NEWS,
            NewsContract.NewsEntry.COLUMN_NAME_LONG_NEWS,
            NewsContract.NewsEntry.COLUMN_NAME_EVENT_DATE_END
    };
    private static final int LOADER_ID = 0;
    private SimpleCursorAdapter mAdapter;

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
        String[] dataColumns = {
                NewsContract.NewsEntry.COLUMN_NAME_SHORT_NEWS,
                NewsContract.NewsEntry.COLUMN_NAME_EVENT_DATE_END,
        };
        int[] viewIDs = {
                R.id.newsListText,
                R.id.newsListDate
        };

//        mAdapter = new NewsListCursorAdapter(getActivity());
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.news_list_item, null, dataColumns, viewIDs, 0);
        getLoaderManager().initLoader(LOADER_ID, null, this);
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
//        ArrayList<News> arrayList = new ArrayList<News>();
//        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:00:50", R.drawable.ic_drawer));
//        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "26 October 2014, 12:10:50", R.drawable.ic_drawer));
//        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "22 October 2014, 13:05:50", R.drawable.ic_drawer));
//        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:10:50", R.drawable.ic_drawer));
//        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:17:50", R.drawable.ic_drawer));
//        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 12:01:50", R.drawable.ic_drawer));
//        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "30 October 2014, 13:00:50", R.drawable.ic_drawer));
//        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "30 October 2014, 16:00:50", R.drawable.ic_drawer));
//        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:00:50", R.drawable.ic_drawer));
//        arrayList.add(new News("Ваш чайник вскипел", "Ваш чайник вскипел и это было охренительно!", "28 October 2014, 13:00:50", R.drawable.ic_drawer));
//         // ======================
//        Collections.sort(arrayList, new Comparator<News>() {
//            @Override
//            public int compare(News news1, News news2) {
//                if(news1.getDateLong() > (news2.getDateLong())) {
//                    return -1;
//                } else {
//                    if (news1.getDateLong() == (news2.getDateLong())) {
//                        return 0;
//                    } else {
//                        return 1;
//                    }
//                }
//            }
//        });
        newsList.setAdapter(/*new NewsListAdapter(getActivity(), arrayList)*/mAdapter);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), SmartContentProvider.NEWS_CONTENT_URI, PROJECTION, null, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_ID:
                mAdapter.swapCursor(cursor);
                break;
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

}
