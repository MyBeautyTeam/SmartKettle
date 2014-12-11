package com.beautyteam.smartkettle.Fragments;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ListView;
import com.beautyteam.smartkettle.Database.NewsContract;
import com.beautyteam.smartkettle.Database.SmartContentProvider;
import com.beautyteam.smartkettle.Fragments.Adapter.NewsListCursorAdapter;
import com.beautyteam.smartkettle.MainActivity;
import com.beautyteam.smartkettle.R;

public class NewsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainActivity mCallback;
    private ListView newsList;
    private static final String[] PROJECTION = new String[] {
            NewsContract.NewsEntry._ID,
            NewsContract.NewsEntry.COLUMN_NAME_NEWS_ID,
            NewsContract.NewsEntry.COLUMN_NAME_DEVICE,
            NewsContract.NewsEntry.COLUMN_NAME_SHORT_NEWS,
            NewsContract.NewsEntry.COLUMN_NAME_LONG_NEWS,
            NewsContract.NewsEntry.COLUMN_NAME_EVENT_DATE
    };


//    private static final int NEWS_PER_PAGE = 1;
//    private static final String SORT_ORDER = "_id LIMIT " + String.valueOf(NEWS_PER_PAGE);
    private static final int LOADER_ID = 0;
    private NewsListCursorAdapter mAdapter;
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
        newsList = (ListView) view.findViewById(R.id.newsList);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.newsRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCallback.refreshNewsList();
            }
        });
        /*Button newsBtn = (Button)LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news_footer, null);
        newsList.addFooterView(newsBtn);*/
    }
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), SmartContentProvider.NEWS_CONTENT_URI, PROJECTION, null, null, /*SORT_ORDER*/null);
    }
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_ID:
                mAdapter = new NewsListCursorAdapter(getActivity(), cursor, 0);
                newsList.setAdapter(mAdapter);
                break;
        }
    }
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
