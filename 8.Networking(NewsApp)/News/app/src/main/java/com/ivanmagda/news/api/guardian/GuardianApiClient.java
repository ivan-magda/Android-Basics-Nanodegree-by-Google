package com.ivanmagda.news.api.guardian;

import com.ivanmagda.news.api.client.HttpApiResource;
import com.ivanmagda.news.api.client.SimpleHttpApiClient;
import com.ivanmagda.news.model.object.NewsItem;
import com.ivanmagda.news.model.parser.NewsItemParser;
import com.ivanmagda.news.util.UrlUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class GuardianApiClient extends SimpleHttpApiClient {

    private static final String BASE_URL = "http://content.guardianapis.com/search";
    private static final String API_KEY = "05ccb328-703e-4bdf-9c24-68a4193ce522";

    private static final HttpApiResource RECENT_NEWS_RESOURCE = new HttpApiResource() {
        @Override
        public String url() {
            return UrlUtils.buildUrlWithParameters(BASE_URL, methodParameters());
        }

        @Override
        public String httpMethod() {
            return "GET";
        }

        @Override
        public HashMap<String, Object> methodParameters() {
            HashMap<String, Object> params = new HashMap<>(3);
            params.put("api-key", API_KEY);
            params.put("order-by", "newest");
            params.put("page-size", "200");

            return params;
        }
    };

    private static GuardianApiClient ourInstance = new GuardianApiClient();

    public static GuardianApiClient getInstance() {
        return ourInstance;
    }

    public ArrayList<NewsItem> fetchRecentItems() {
        String json = fetchResource(RECENT_NEWS_RESOURCE);
        return NewsItemParser.parseItems(json);
    }

}
