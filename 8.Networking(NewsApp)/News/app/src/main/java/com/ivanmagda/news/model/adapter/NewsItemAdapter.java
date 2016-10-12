package com.ivanmagda.news.model.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ivanmagda.news.R;
import com.ivanmagda.news.model.object.Author;
import com.ivanmagda.news.model.object.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class NewsItemAdapter extends ArrayAdapter<NewsItem> {

    private static final String AUTHORS_SEPARATOR = ", ";

    private static class ViewHolder {
        TextView titleTextView;
        TextView sectionNameTextView;
        TextView authorTextView;
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
            viewHolder.authorTextView = (TextView) listView.findViewById(R.id.author_text_view);

            listView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listView.getTag();
        }

        configureViewHolderWithItem(viewHolder, getItem(position));

        return listView;
    }

    public void updateData(List<NewsItem> earthquakes) {
        clear();
        if (earthquakes != null && !earthquakes.isEmpty()) addAll(earthquakes);
        notifyDataSetChanged();
    }

    private void configureViewHolderWithItem(ViewHolder viewHolder, NewsItem newsItem) {
        assert newsItem != null;

        viewHolder.titleTextView.setText(newsItem.getTitle());
        viewHolder.sectionNameTextView.setText(newsItem.getSectionName());

        Author[] authors = newsItem.getAuthors();
        if (authors == null || authors.length == 0) {
            viewHolder.authorTextView.setVisibility(View.GONE);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (Author author : authors) {
                stringBuilder.append(author.getFullName()).append(AUTHORS_SEPARATOR);
            }
            String authorsString = stringBuilder.toString()
                    .substring(0, stringBuilder.length() - AUTHORS_SEPARATOR.length());

            viewHolder.authorTextView.setText(authorsString);
            viewHolder.authorTextView.setVisibility(View.VISIBLE);
        }
    }

}
