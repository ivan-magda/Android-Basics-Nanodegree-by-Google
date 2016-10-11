package com.ivanmagda.booklisting.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ivanmagda.booklisting.R;
import com.ivanmagda.booklisting.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    private static class ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView authorsTextView;
    }

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    public BookAdapter(Context context) {
        super(context, 0, new ArrayList<Book>());
    }

    public void updateData(List<Book> books) {
        clear();
        if (books != null && !books.isEmpty()) addAll(books);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View listView = convertView;

        if (convertView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView) listView.findViewById(R.id.book_title_text_view);
            viewHolder.descriptionTextView = (TextView) listView.findViewById(R.id.book_description_text_view);
            viewHolder.authorsTextView = (TextView) listView.findViewById(R.id.authors_text_view);

            listView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) listView.getTag();
        }

        Book book = getItem(position);
        assert book != null;

        configureViewHolder(viewHolder, book);

        return listView;
    }

    private void configureViewHolder(ViewHolder viewHolder, Book book) {
        TextView titleTextView = viewHolder.titleTextView;
        titleTextView.setText(book.getTitle());

        TextView descriptionTextView = viewHolder.descriptionTextView;
        String description = book.getDescription();
        if (description != null) {
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(book.getDescription());
        } else {
            descriptionTextView.setVisibility(View.GONE);
        }

        TextView authorsTextView = viewHolder.authorsTextView;
        String authors = ArrayUtils.mapToString(book.getAuthors());
        if (!TextUtils.isEmpty(authors)) {
            authorsTextView.setVisibility(View.VISIBLE);
            authorsTextView.setText(authors);
        } else {
            authorsTextView.setVisibility(View.GONE);
        }
    }

}
