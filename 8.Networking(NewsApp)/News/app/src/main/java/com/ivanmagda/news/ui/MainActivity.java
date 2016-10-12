package com.ivanmagda.news.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ivanmagda.news.R;
import com.ivanmagda.news.api.guardian.GuardianLoader;
import com.ivanmagda.news.model.NewsItem;
import com.ivanmagda.news.util.ConnectivityUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<NewsItem>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int GUARDIAN_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configure();
        fetchData();
    }

    private void configure() {
        setTitle(getString(R.string.main_activity_title));
    }

    private void fetchData() {
        if (ConnectivityUtils.isOnline(this)) {
            getLoaderManager().initLoader(GUARDIAN_LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<ArrayList<NewsItem>> onCreateLoader(int id, Bundle args) {
        return new GuardianLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsItem>> loader, ArrayList<NewsItem> data) {
        Log.d(LOG_TAG, "Fetched items: " + data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsItem>> loader) {

    }

}
