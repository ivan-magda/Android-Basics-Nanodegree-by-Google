package com.ivanmagda.news.model.parser;

import android.util.Log;

import com.ivanmagda.news.model.object.Author;
import com.ivanmagda.news.model.object.NewsItem;
import com.ivanmagda.news.util.AuthorUtils;

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

                JSONArray tags = itemJSON.getJSONArray("tags");
                Author[] authors = null;

                if (tags.length() > 0) {
                    authors = new Author[tags.length()];
                    for (int j = 0; j < tags.length(); j++) {
                        JSONObject tagsObject = tags.getJSONObject(j);

                        String firstName = tagsObject.getString("firstName");
                        String lastName = tagsObject.getString("lastName");
                        authors[j] = new Author(AuthorUtils.verifiedPersonalInfo(firstName),
                                AuthorUtils.verifiedPersonalInfo(lastName));
                    }
                }

                items.add(new NewsItem(title, sectionName, url, authors));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Failed parse NewsItem", e);
        }

        return items;
    }

}
