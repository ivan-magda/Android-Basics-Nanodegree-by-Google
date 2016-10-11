package com.ivanmagda.booklisting.api;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.ivanmagda.booklisting.model.Book;
import com.ivanmagda.booklisting.util.QueryUtils;

import java.util.ArrayList;

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    private static final String LOG_TAG = BookLoader.class.getSimpleName();

    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        if (mUrl == null) return null;
        return QueryUtils.fetchBooks(mUrl);
    }

}
