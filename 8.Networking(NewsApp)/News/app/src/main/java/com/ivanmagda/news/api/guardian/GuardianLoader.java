package com.ivanmagda.news.api.guardian;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.ivanmagda.news.model.NewsItem;

import java.util.ArrayList;

public class GuardianLoader extends AsyncTaskLoader<ArrayList<NewsItem>> {

    public GuardianLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<NewsItem> loadInBackground() {
        return GuardianApiClient.getInstance().fetchRecentItems();
    }

}
