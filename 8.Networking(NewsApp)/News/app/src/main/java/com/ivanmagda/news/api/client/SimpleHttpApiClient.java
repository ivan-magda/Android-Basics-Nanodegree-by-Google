package com.ivanmagda.news.api.client;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class SimpleHttpApiClient {

    private static final String LOG_TAG = SimpleHttpApiClient.class.getSimpleName();

    private String mUrl;
    private String httpMethodName;

    public SimpleHttpApiClient() {
        this.mUrl = null;
        this.httpMethodName = null;
    }

    public SimpleHttpApiClient(HttpApiResource resource) {
        configureWithResource(resource);
    }

    private void configureWithResource(HttpApiResource resource) {
        this.mUrl = resource.url();
        this.httpMethodName = resource.httpMethod();
    }

    public String fetch() {
        URL url = createURL(mUrl);
        return makeHttpRequest(url);
    }

    public String fetchResource(HttpApiResource resource) {
        configureWithResource(resource);
        return fetch();
    }

    private URL createURL(String urlString) {
        URL url = null;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Failed to create URL", e);
        }

        return url;
    }

    private String makeHttpRequest(URL url) {
        String response = "";

        if (url == null) return response;

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(httpMethodName);
            connection.setReadTimeout(10000);
            connection.setReadTimeout(15000);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                inputStream = connection.getInputStream();
                response = readFromStream(inputStream);
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

        return response;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) builder.append(line);
        }

        return builder.toString();
    }

}
