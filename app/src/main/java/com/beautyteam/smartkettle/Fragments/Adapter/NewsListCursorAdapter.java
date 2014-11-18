package com.beautyteam.smartkettle.Fragments.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beautyteam.smartkettle.Database.NewsContract;
import com.beautyteam.smartkettle.Mechanics.News;
import com.beautyteam.smartkettle.R;

public class NewsListCursorAdapter extends CursorAdapter {
    private Context context;
    private LayoutInflater inflater;

    public NewsListCursorAdapter(Context _context, Cursor _c, int _flags) {
        super(_context, _c, _flags);
        context = _context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.mainText = (TextView) view.findViewById(R.id.newsListText);
        viewHolder.date = (TextView) view.findViewById(R.id.newsListDate);
        viewHolder.image = (ImageView) view.findViewById(R.id.newsListIcon);
        view.setTag(viewHolder);

        News news = new News(cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NAME_SHORT_NEWS)),
                             cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NAME_LONG_NEWS)),
                             cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NAME_EVENT_DATE_END)),
                             R.drawable.ic_drawer);
        viewHolder.mainText.setText(news.getShortNewsText());
        viewHolder.date.setText(news.getDateInfo());
        viewHolder.image.setImageResource(news.getImageId());
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.from(context).inflate(R.layout.news_list_item, parent, false);
    }

    private class ViewHolder {
        public TextView mainText;
        public TextView date;
        public ImageView image;
    }
}
