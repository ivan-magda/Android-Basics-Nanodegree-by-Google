package com.ivanmagda.news.model.parser;

import android.util.Log;

import com.ivanmagda.news.model.object.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsItemParser {

    private static final String LOG_TAG = NewsItemParser.class.getSimpleName();

    private NewsItemParser() {
    }

    public static ArrayList<NewsItem> parseItems(String json) {
        ArrayList<NewsItem> items = new ArrayList<>();

        try {
            JSONObject rootJSON = new JSONObject(json);

            JSONObject response = rootJSON.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject itemJSON = results.getJSONObject(i);

                String title = itemJSON.getString("webTitle");
                String sectionName = itemJSON.getString("sectionName");
                String url = itemJSON.getString("webUrl");

                items.add(new NewsItem(title, sectionName, url));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Failed parse NewsItem", e);
        }

        return items;
    }

}
