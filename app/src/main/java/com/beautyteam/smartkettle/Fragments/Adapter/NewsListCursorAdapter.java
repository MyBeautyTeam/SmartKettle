package com.beautyteam.smartkettle.Fragments.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beautyteam.smartkettle.Database.NewsContract;
import com.beautyteam.smartkettle.Mechanics.News;
import com.beautyteam.smartkettle.R;

public class NewsListCursorAdapter extends CursorAdapter {
    private static final String LOG_TAG = "NewsListCursorAdapterLogs";
    private static final int NEWS_IMAGE = R.drawable.ic_news;
    private static ViewHolder viewHolder;
    private LayoutInflater inflater;

    public NewsListCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewHolder = new ViewHolder();
    }

    @Override
    public Object getItem(int position) {
        Cursor cursor = getCursor();
        News news = null;
        if (cursor.moveToPosition(position)) {
            news = new News(cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NAME_SHORT_NEWS)),
                            cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NAME_LONG_NEWS)),
                            cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NAME_EVENT_DATE)),
                            NEWS_IMAGE);
        }
        return news;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d(LOG_TAG, "NewsListCursorAdapter bindView");
        viewHolder.mainText = (TextView) view.findViewById(R.id.newsListText);
        viewHolder.date = (TextView) view.findViewById(R.id.newsListDate);
        viewHolder.image = (ImageView) view.findViewById(R.id.newsListIcon);
        view.setTag(viewHolder);

        News news = new News(cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NAME_SHORT_NEWS)),
                             cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NAME_LONG_NEWS)),
                             cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NAME_EVENT_DATE)),
                             NEWS_IMAGE);

        viewHolder.mainText.setText(news.getShortNews());
        viewHolder.date.setText(news.getDateInfo());
        viewHolder.image.setImageResource(news.getImage());
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d(LOG_TAG, "NewsListCursorAdapter newView");
        return inflater.from(context).inflate(R.layout.news_list_item, parent, false);
    }

    private class ViewHolder {
        public TextView mainText;
        public TextView date;
        public ImageView image;
    }
}
