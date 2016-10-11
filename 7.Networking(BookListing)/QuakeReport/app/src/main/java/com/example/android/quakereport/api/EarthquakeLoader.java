package com.example.android.quakereport.api;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.android.quakereport.model.Earthquake;
import com.example.android.quakereport.util.QueryUtils;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

    private static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.mUrl = url;

        Log.d(LOG_TAG, "EarthquakeLoader initialized");
    }

    @Override
    protected void onStartLoading() {
        Log.d(LOG_TAG, "EarthquakeLoader start loading");
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        Log.d(LOG_TAG, "EarthquakeLoader load in background");
        if (mUrl == null) return null;
        return QueryUtils.fetchRecentEarthquakes(mUrl);
    }

}
