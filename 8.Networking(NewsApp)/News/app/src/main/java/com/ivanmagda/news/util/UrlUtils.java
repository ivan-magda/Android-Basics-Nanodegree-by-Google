package com.ivanmagda.news.util;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

public class UrlUtils {

    private UrlUtils() {
    }

    public static String buildUrlWithParameters(String baseUrl, HashMap<String, Object> params) {
        if (baseUrl == null) return null;

        Uri baseUri = Uri.parse(baseUrl);
        Uri.Builder builder = baseUri.buildUpon();

        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                builder.appendQueryParameter(key, value.toString());
            }
        }

        return builder.toString();
    }

}
