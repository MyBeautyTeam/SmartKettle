package com.beautyteam.smartkettle.Fragments.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beautyteam.smartkettle.Mechanics.News;
import com.beautyteam.smartkettle.R;

import java.util.ArrayList;

/**
 * Created by Admin on 25.10.2014.
 */
public class NewsListAdapter extends BaseAdapter{

    private ArrayList<News> newsArrayList;
    private Context context;
    private LayoutInflater inflater;

    public NewsListAdapter(Context _context, ArrayList<News> _newsArrayList) {
        context = _context;
        newsArrayList = _newsArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return newsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsArrayList.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.from(context).inflate(R.layout.news_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mainText = (TextView) convertView.findViewById(R.id.newsListText);
            viewHolder.date = (TextView) convertView.findViewById(R.id.newsListDate);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.newsListIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        News news = (News)getItem(position);
        viewHolder.mainText.setText(news.getShortNewsText());
        viewHolder.date.setText(news.getDateInfo());
        viewHolder.image.setImageResource(news.getImageId());
        return convertView;
    }

    private class ViewHolder {
        public TextView mainText;
        public TextView date;
        public ImageView image;
    }
}
