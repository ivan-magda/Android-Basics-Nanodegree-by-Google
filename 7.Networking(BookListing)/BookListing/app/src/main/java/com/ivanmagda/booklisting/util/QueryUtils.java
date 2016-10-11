package com.ivanmagda.booklisting.util;

import android.text.TextUtils;
import android.util.Log;

import com.ivanmagda.booklisting.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static ArrayList<Book> fetchBooks(String requestUrl) {
        URL url = createURL(requestUrl);
        String json = doRequest(url);

        return QueryUtils.parseBooks(json);
    }

    private static URL createURL(String urlString) {
        URL url = null;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Failed to create URL", e);
        }

        return url;
    }

    private static String doRequest(URL url) {
        String json = "";

        if (url == null) return json;

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setReadTimeout(15000);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                inputStream = connection.getInputStream();
                json = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Bad response code: " + responseCode);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to perform http request", e);
        } finally {
            if (connection != null) connection.disconnect();

            if (inputStream != null) try {
                inputStream.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error closing input stream", e);
            }
        }

        return json;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) builder.append(line);
        }

        return builder.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Book> parseBooks(String json) {
        if (TextUtils.isEmpty(json)) return null;

        ArrayList<Book> books = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(json);

            JSONArray items = rootObject.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                String title = volumeInfo.getString("title");
                String description = volumeInfo.optString("description");


                String[] authors = null;
                JSONArray authorsJSON = volumeInfo.optJSONArray("authors");
                if (authorsJSON != null) {
                    authors = new String[authorsJSON.length()];
                    for (int j = 0; j < authorsJSON.length(); j++) {
                        authors[j] = authorsJSON.getString(j);
                    }
                }

                books.add(new Book(title, description, authors));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the book JSON results", e);
        }

        return books;
    }

}
