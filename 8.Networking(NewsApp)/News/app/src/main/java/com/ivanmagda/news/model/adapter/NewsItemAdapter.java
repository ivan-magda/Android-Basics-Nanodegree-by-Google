package com.ivanmagda.news.model.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ivanmagda.news.R;
import com.ivanmagda.news.model.object.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class NewsItemAdapter extends ArrayAdapter<NewsItem> {

    private static class ViewHolder {
        TextView titleTextView;
        TextView sectionNameTextView;
    }

    public NewsItemAdapter(Context context, List<NewsItem> objects) {
        super(context, 0, objects);
    }

    public NewsItemAdapter(Context context) {
        super(context, 0, new ArrayList<NewsItem>());
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View listView = convertView;

        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView) listView.findViewById(R.id.title_text_view);
            viewHolder.sectionNameTextView = (TextView) listView.findViewById(R.id.section_text_view);

            listView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listView.getTag();
        }

        NewsItem newsItem = getItem(position);
        assert newsItem != null;

        viewHolder.titleTextView.setText(newsItem.getTitle());
        viewHolder.sectionNameTextView.setText(newsItem.getSectionName());

        return listView;
    }

    public void updateData(List<NewsItem> earthquakes) {
        clear();
        if (earthquakes != null && !earthquakes.isEmpty()) addAll(earthquakes);
        notifyDataSetChanged();
    }

}
